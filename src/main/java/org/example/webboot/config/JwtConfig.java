package org.example.webboot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtConfig {

    private String secret;
    private long expirationInMs;
    private long refreshExpirationInMs;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 注册 JavaTimeModule
        return objectMapper;
    }

    // Getters and Setters
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpirationInMs() {
        return expirationInMs;
    }

    public void setExpirationInMs(long expirationInMs) {
        this.expirationInMs = expirationInMs;
    }

    public long getRefreshExpirationInMs() {
        return refreshExpirationInMs;
    }

    public void setRefreshExpirationInMs(long refreshExpirationInMs) {
        this.refreshExpirationInMs = refreshExpirationInMs;
    }
}
