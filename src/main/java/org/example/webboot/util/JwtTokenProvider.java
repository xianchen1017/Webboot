package org.example.webboot.util;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

@Component // 添加 @Component 注解
public class JwtTokenProvider {

    private String secretKey = "your-secret-key";  // 你应该用更强的密钥

    // 根据用户名和角色生成 JWT token
    public String createToken(String username, String role) {
        // 使用 JWT 库生成 token（示例，实际根据你的需求进行修改）
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS256, "your_secret_key")
                .compact();
    }

    // 从 token 中解析出用户信息
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
