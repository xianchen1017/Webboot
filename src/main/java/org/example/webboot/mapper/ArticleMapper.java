package org.example.webboot.mapper;

import org.example.webboot.entity.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("INSERT INTO article (title, content, category, status, id) VALUES (#{title}, #{content}, #{category}, #{status}, #{userId})")
    void insert(Article article);

    @Select("SELECT * FROM article WHERE id = #{userId}")
    List<Article> findByUserId(int userId);
}
