package org.example.webboot.dto;

public class UserLoginResponse {
    private String token;
    private String username;
    private String avatar;
    private String role;

    // Constructor
    public UserLoginResponse(String token, String username, String avatar, String role) {
        this.token = token;
        this.username = username;
        this.avatar = avatar;
        this.role = role;
    }

    // Getter and Setter methods
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
