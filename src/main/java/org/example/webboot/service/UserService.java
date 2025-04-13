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

    public User registerUser(RegisterDTO registerDTO, MultipartFile avatar) {
        // 保存头像文件
        String avatarPath = fileService.saveAvatar(avatar); // 保存文件并获取路径

        // 创建并保存用户
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword()); // 这里可以加入密码加密逻辑
        user.setAvatar(avatarPath);

        // 保存到数据库
        userRepository.save(user);

        return user;
    }

    public User loginUser(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
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

    /**
     * 删除用户
     * @param id 用户ID
     */
    public void deleteUser(int id) {
        userMapper.delete(id);
    }
}
