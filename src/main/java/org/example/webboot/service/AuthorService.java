package org.example.webboot.service;

import org.example.webboot.dto.AuthorStatsDTO;
import org.example.webboot.entity.Author;
import org.example.webboot.entity.User;
import org.example.webboot.dto.LoginDTO;
import org.example.webboot.repository.AuthorRepository;
import org.example.webboot.repository.UserRepository;
import org.example.webboot.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // 确保导入的是Spring的Page
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider; // 用于生成 JWT token

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  // 用于密码加密

    // 根据用户名获取用户信息
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);  // 假设你有一个根据用户名查询用户的方法
    }

    // 生成 JWT token
    public String generateToken(User user) {
        // 使用 JWT 库生成 token
        return jwtTokenProvider.createToken(user.getUsername(), user.getRole());
    }

    public boolean authenticate(LoginDTO loginDTO) {
        // 根据用户名查询用户
        User user = userRepository.findByUsername(loginDTO.getUsername());

        if (user == null) {
            // 用户不存在，抛出异常
            System.out.println("用户不存在：" + loginDTO.getUsername());
            throw new IllegalArgumentException("用户名或密码错误");
        }

        System.out.println("输入的密码: " + loginDTO.getPassword());
        System.out.println("数据库中的密码: " + user.getPassword());

        // 直接比对明文密码
        boolean isPasswordCorrect = loginDTO.getPassword().equals(user.getPassword());
        if (!isPasswordCorrect) {
            // 密码错误，抛出异常
            System.out.println("密码错误：" + loginDTO.getPassword());
            throw new IllegalArgumentException("用户名或密码错误");
        }

        return true;  // 密码正确
    }

    // 根据ID获取作者
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public Page<Author> getAuthors(PageRequest pageable) {
        return authorRepository.findAll((Pageable) pageable);
    }


    public List<AuthorStatsDTO> getAuthorsWithArticleCount() {
        // 使用JPQL查询
        return authorRepository.findAuthorsWithArticleCount();
    }
}



