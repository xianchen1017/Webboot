package org.example.webboot.controller;

public class LoginResponseDTO {
    private String token;
    private String avatar;

    // 构造方法、getter、setter
    public LoginResponseDTO(String token, String avatar) {
        this.token = token;
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
