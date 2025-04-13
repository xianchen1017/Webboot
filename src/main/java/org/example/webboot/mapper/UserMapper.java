package org.example.webboot.mapper;

import org.apache.ibatis.annotations.*;
import org.example.webboot.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    // 获取所有用户
    @Select("SELECT * FROM users")
    List<User> findAllUsers();

    // 根据ID获取用户
    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(int id);

    // 根据用户名获取用户
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);  // 添加了 findByUsername 方法


    // 插入新用户
    @Insert("INSERT INTO users (username, email, password) VALUES (#{username}, #{email}, #{password})")
    void insert(User user);

    // 更新用户
    @Update("UPDATE users SET username = #{username}, email = #{email}, password = #{password} WHERE id = #{id}")
    void update(User user);

    // 删除用户
    @Delete("DELETE FROM users WHERE id = #{id}")
    void delete(int id);
}
