package org.example.webboot.repository;

import org.example.webboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 你可以添加自定义的查询方法
    User findByUsername(String username);
}
