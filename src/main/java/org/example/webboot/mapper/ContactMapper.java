package org.example.webboot.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.webboot.entity.Contact;

import java.util.List;

public interface ContactMapper {
    int insert(Contact contact);
    int update(Contact contact);
    int deleteById(int id);
    Contact selectById(int id);
    List<Contact> selectByUserIdAndName(@Param("userId") int userId, @Param("name") String name);
}