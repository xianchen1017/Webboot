package org.example.webboot.controller;
//src/main/java/org/example/webboot/controller/AuthController.java
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.webboot.dto.LoginDTO;
import org.example.webboot.dto.RegisterDTO;
import org.example.webboot.entity.User;
import org.example.webboot.service.UserService;
import org.example.webboot.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseResult register(@RequestParam("registerDTO") String registerDTOJson,
                                   @RequestParam("avatar") MultipartFile avatar) throws JsonProcessingException {
        // 解析 registerDTO
        RegisterDTO registerDTO = new ObjectMapper().readValue(registerDTOJson, RegisterDTO.class);

        // 调用 UserService 注册用户
        User user = userService.registerUser(registerDTO, avatar);

        return new ResponseResult("User registered successfully", user);
    }


    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDTO loginDTO) {
        User user = userService.loginUser(loginDTO.getUsername(), loginDTO.getPassword());
        if (user != null) {
            String token = userService.generateToken(user);
            return new ResponseResult("Login successful", token);
        } else {
            return new ResponseResult("Invalid credentials", null);
        }
    }
}
