package org.example.webboot.service;
// src/main/java/org/example/webboot/service/UserService.java
import org.example.webboot.dto.LoginDTO;
import org.example.webboot.dto.RegisterDTO;
import org.example.webboot.entity.User;
import org.example.webboot.mapper.UserMapper;
import org.example.webboot.repository.UserRepository;
import org.example.webboot.util.FileService;
import org.example.webboot.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.example.webboot.dto.UserDTO;
import org.example.webboot.entity.User;
import org.example.webboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService; // 用于处理文件上传

    @Autowired
    private PasswordEncoder passwordEncoder; // 注入 PasswordEncoder


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);  // 假设你使用 JPA 查询
    }

    public User registerUser(RegisterDTO registerDTO, MultipartFile avatar) {
        // 直接保存明文密码
        String password = registerDTO.getPassword();  // 明文密码
        String avatarPath = fileService.saveAvatar(avatar);  // 获取头像的相对路径
        // 创建并保存用户
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(password);  // 保存明文密码
        user.setAvatar(fileService.saveAvatar(avatar));

        // 保存到数据库
        return userRepository.save(user);
    }


    public User loginUser(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;  // 确保user对象包含头像路径字段
        }
        return null;
    }

    public String generateToken(User user) {
        return jwtUtil.generateToken(user);
    }

    /**
     * 获取所有用户
     */
    public List<UserDTO> getAllUsers() {
        List<User> users = userMapper.findAllUsers();
        return users.stream()
                .map(user -> new UserDTO(user))
                .collect(Collectors.toList());
    }

    /**
     * 获取单个用户
     * @param id 用户ID
     */
    public UserDTO getUserById(int id) {
        User user = userMapper.findById(id);
        return new UserDTO(user);
    }

    /**
     * 创建用户
     * @param userDTO 用户数据
     */
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User(userDTO); // 从 UserDTO 创建 User
        userMapper.insert(user);
        return new UserDTO(user); // 返回创建的 UserDTO
    }

    /**
     * 更新用户
     * @param id 用户ID
     * @param userDTO 用户数据
     */
    public UserDTO updateUser(int id, UserDTO userDTO) {
        User user = userMapper.findById(id);
        if (user != null) {
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            userMapper.update(user);
            return new UserDTO(user);
        }
        return null;
    }

    public boolean updatePassword(User user, String newPassword) {
        // 密码加密处理（如果有加密需求）
        user.setPassword(newPassword);

        // 保存更新后的用户信息
        userRepository.save(user);

        return true;  // 成功更新
    }


    /**
     * 删除用户
     * @param id 用户ID
     */
    public void deleteUser(int id) {
        userMapper.delete(id);
    }
}