package org.example.webboot.filter;

import org.example.webboot.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtUtil.extractTokenFromRequest(request);

        if (token != null && jwtUtil.isValidToken(token, jwtUtil.getAuthenticationFromToken(token))) {
            String username = jwtUtil.getUsernameFromToken(token);
            // 根据 token 提取用户名，并在 Spring Security 中设置认证信息
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, null); // 设置权限信息，这里暂时设为 null，按需设置
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // 设置请求细节
            SecurityContextHolder.getContext().setAuthentication(authentication); // 将认证信息设置到 SecurityContext
        }

        filterChain.doFilter(request, response); // 继续过滤链
    }
}
