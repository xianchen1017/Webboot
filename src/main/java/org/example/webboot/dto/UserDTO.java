package org.example.webboot.dto;

import org.example.webboot.entity.User;
//src/main/java/org/example/webboot/dto/UserDTO.java
public class UserDTO {
    private String username;
    private String email;
    private String password;  // 添加 password 字段


    // Constructor from User entity
    public UserDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    // Getter and Setter methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;  // 返回 password
    }

    public void setPassword(String password) {
        this.password = password;  // 设置 password
    }
}
