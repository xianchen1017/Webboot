package org.example.webboot.controller;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;

    // Getter 和 Setter 方法
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
