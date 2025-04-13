package org.example.webboot.controller;
//src/main/java/org/example/webboot/controller/UserController.java
import org.example.webboot.dto.UserDTO;
import org.example.webboot.entity.User;
import org.example.webboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取所有用户
     */
    @GetMapping("/list")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * 获取单个用户
     * @param id 用户ID
     */
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    /**
     * 创建新用户
     * @param userDTO 用户数据传输对象
     */
    @PostMapping("/create")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    /**
     * 更新用户信息
     * @param id 用户ID
     * @param userDTO 用户数据传输对象
     */
    @PutMapping("/{id}/update")
    public UserDTO updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    /**
     * 删除用户
     * @param id 用户ID
     */
    @DeleteMapping("/{id}/delete")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
