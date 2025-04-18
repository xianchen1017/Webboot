package org.example.webboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.webboot.dto.LoginDTO;
import org.example.webboot.dto.PasswordDTO;
import org.example.webboot.dto.RegisterDTO;
import org.example.webboot.dto.UserLoginResponse;
import org.example.webboot.entity.User;
import org.example.webboot.service.UserService;
import org.example.webboot.util.JwtTokenProvider;
import org.example.webboot.util.ResponseResult;
import org.example.webboot.service.AuthService; // 导入 AuthService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService; // 注入 AuthService

    @Autowired
    private JwtTokenProvider jwtTokenProvider;  // 注入 JwtTokenProvider

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseResult register(@RequestParam("registerDTO") String registerDTOJson,
                                   @RequestParam("avatar") MultipartFile avatar) throws JsonProcessingException {
        // 解析 registerDTO
        RegisterDTO registerDTO = new ObjectMapper().readValue(registerDTOJson, RegisterDTO.class);

        // 打印注册数据进行调试
        System.out.println("接收到的注册数据：");
        System.out.println("用户名: " + registerDTO.getUsername());
        System.out.println("密码: " + registerDTO.getPassword());
        System.out.println("邮箱: " + registerDTO.getEmail());
        System.out.println("生日: " + registerDTO.getBirthday());

        // 调用 UserService 注册用户
        User user = userService.registerUser(registerDTO, avatar);

        return new ResponseResult("注册成功", user);
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDTO loginDTO) {
        // 确保调用authenticate()方法返回的是正确的结果
        boolean isAuthenticated = authService.authenticate(loginDTO);

        // 登录认证
        User user = userService.getUserByUsername(loginDTO.getUsername());
        if (user != null && user.getPassword().equals(loginDTO.getPassword())) {
            String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole());
            return ResponseResult.success("登录成功", user);
        }else {
            return ResponseResult.error("用户名或密码错误");
        }
    }

    @PostMapping("/change-password")
    public ResponseResult changePassword(@RequestBody PasswordDTO passwordDTO) {
        // 检查用户名和原密码
        User user = userService.getUserByUsername(passwordDTO.getUsername());
        if (user == null || !user.getPassword().equals(passwordDTO.getOldPassword())) {
            return ResponseResult.error("用户名或原密码错误");
        }

        // 检查新密码和确认密码是否一致
        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            return ResponseResult.error("新密码与确认密码不一致");
        }

        // 更新密码
        boolean isUpdated = userService.updatePassword(user, passwordDTO.getNewPassword());
        if (isUpdated) {
            return ResponseResult.success("密码修改成功", null);
        } else {
            return ResponseResult.error("密码修改失败");
        }
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            String refreshToken = request.getRefreshToken();
            String username = jwtTokenProvider.getUsernameFromToken(refreshToken);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenProvider.validateToken(refreshToken)) {
                String newToken = jwtTokenProvider.generateToken(userDetails);
                String newRefreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

                return ResponseEntity.ok(new TokenResponse(newToken, newRefreshToken));
            }

            throw new RuntimeException("Invalid refresh token");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }


}