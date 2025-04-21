package org.example.webboot.service;

import org.example.webboot.entity.Article;
import org.example.webboot.entity.Author;
import org.example.webboot.exception.ResourceNotFoundException;
import org.example.webboot.repository.ArticleRepository;
import org.example.webboot.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // 使用Spring的Page
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public Page<Article> getArticlesByAuthor(Author author, int page, int size) {
        // 确保author是从数据库获取的托管实体
        Author managedAuthor = authorRepository.findById(author.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Article> articles = articleRepository.findByAuthor(managedAuthor, pageable);

        // 检查是否获取到数据
        System.out.println("Service层获取的文章数量: " + articles.getNumberOfElements());

        return articles;
    }

    // 添加文章后更新作者的文章数量
    @Transactional
    public Article addArticle(Article article) {
        // 验证作者是否存在
        if (article.getAuthor() == null || article.getAuthor().getId() == null) {
            throw new IllegalArgumentException("Author must be specified");
        }

        Author author = authorRepository.findById(article.getAuthor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        article.setAuthor(author);
        Article savedArticle = articleRepository.save(article);

        // 更新作者文章计数
        updateAuthorArticleCount(author);

        return savedArticle;
    }

    // 更新文章
    @Transactional
    public Article updateArticle(Article article) {
        Article existingArticle = articleRepository.findById(article.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        existingArticle.setTitle(article.getTitle());
        existingArticle.setContent(article.getContent());

        return articleRepository.save(existingArticle);
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

    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));
    }

}
