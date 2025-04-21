package org.example.webboot.repository;

import org.example.webboot.entity.Article;
import org.example.webboot.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    // 根据作者对象查询文章（分页）
    Page<Article> findByAuthor(Author author, Pageable pageable);

    // 根据文章标题模糊查询
    List<Article> findByTitleContaining(String title);

    // 根据作者查询文章数量（通过作者对象）
    int countByAuthor(Author author);  // 修改为通过作者对象查询
}
