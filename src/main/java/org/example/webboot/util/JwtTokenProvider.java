package org.example.webboot.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component // 添加 @Component 注解
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-in-ms}")
    private long jwtExpirationInMs;

    @Value("${app.jwt.refresh-expiration-in-ms}")
    private long refreshExpirationInMs;

    private String secretKey = System.getenv("SECRET_KEY");  // 你应该用更强的密钥
    private final String SECRET_KEY = System.getenv("SECRET_KEY"); // 替换为你自己的密钥
    private final long EXPIRATION_TIME = 86400000; // 过期时间（例如，24小时）

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // 返回去掉 "Bearer " 后的 token
        }
        return null; // 如果没有 Authorization 头或格式不对，返回 null
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = loadUserByUsername(getUsernameFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public UserDetails loadUserByUsername(String username) {
        // 从数据库或其他存储方式加载用户信息，返回一个 UserDetails 实例
        return new User(username, "", AuthorityUtils.createAuthorityList("USER"));
    }


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

    // 验证 token 是否有效
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date()); // 如果 token 没过期，返回 true
        } catch (Exception e) {
            return false;  // 如果验证失败，返回 false
        }
    }


    // 生成新的 token
    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    // 生成新的 refresh token
    public String generateRefreshToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshExpirationInMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    // 检查 token 是否过期
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
