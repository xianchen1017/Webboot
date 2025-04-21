package org.example.webboot.service;

import org.example.webboot.entity.Article;
import org.example.webboot.entity.Author;
import org.example.webboot.repository.ArticleRepository;
import org.example.webboot.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // 使用Spring的Page
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ArticleRepository articleRepository;

    // 获取所有作者的文章统计信息
    public org.springframework.data.domain.Page<Object[]> getAuthorsWithArticleCount(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return (Page<Object[]>) authorRepository.findAllWithArticleCount((Pageable) pageable);
    }

    // 获取指定作者的所有文章
    public org.springframework.data.domain.Page<Article> getArticlesByAuthor(Author author, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // Spring分页从0开始
        return articleRepository.findByAuthor(author, pageable);
    }

    // 添加文章后更新作者的文章数量
    public Article addArticle(Article article) {
        Article savedArticle = articleRepository.save(article);
        updateAuthorArticleCount(article.getAuthor()); // 传入作者对象
        return savedArticle;
    }

    // 编辑文章
    public Article updateArticle(Long id, Article updatedArticle) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        article.setTitle(updatedArticle.getTitle());
        article.setCategory(updatedArticle.getCategory());
        article.setStatus(updatedArticle.getStatus());
        return articleRepository.save(article);
    }

    // 删除文章后更新作者的文章数量
    public void deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("Article not found"));
        articleRepository.delete(article);
        updateAuthorArticleCount(article.getAuthor()); // 传入作者对象
    }

    // 更新作者的文章数量
    private void updateAuthorArticleCount(Author author) {
        int articleCount = articleRepository.countByAuthor(author); // 使用author对象来查询
        author.setArticleCount(articleCount); // 更新文章数量
        authorRepository.save(author); // 保存更新后的作者
    }

    // 搜索文章
    public List<Article> searchArticlesByTitle(String title) {
        return articleRepository.findByTitleContaining(title);
    }
}
