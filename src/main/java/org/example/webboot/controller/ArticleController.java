package org.example.webboot.controller;
import org.example.webboot.entity.Article;
import org.example.webboot.entity.Author;
import org.example.webboot.service.ArticleService;
import org.example.webboot.service.AuthorService;
import org.example.webboot.dto.AuthorStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public ResponseEntity<Map<String, Object>> getAuthors(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {

        // 转换为Spring Data的分页参数(从0开始)
        PageRequest pageable = PageRequest.of(page - 1, size);

        // 使用Spring Data的Page
        org.springframework.data.domain.Page<Author> pageResult = authorService.getAuthors(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("authors", pageResult.getContent());
        response.put("currentPage", pageResult.getNumber() + 1);
        response.put("totalItems", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // 获取指定作者的文章
    @GetMapping("/author/{authorId}/articles")
    public ResponseEntity<Map<String, Object>> getArticlesByAuthor(
            @PathVariable Long authorId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Author author = authorService.getAuthorById(authorId);
        org.springframework.data.domain.Page<Article> pageResult = articleService.getArticlesByAuthor(author, page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("articles", pageResult.getContent());
        response.put("currentPage", pageResult.getNumber() + 1);
        response.put("totalItems", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());

        return ResponseEntity.ok(response);
    }


    // 新增文章
    @PostMapping("/article")
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        Article savedArticle = articleService.addArticle(article);
        return ResponseEntity.ok(savedArticle);
    }

    // 编辑文章
    @PutMapping("/article/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article updatedArticle) {
        Article article = articleService.updateArticle(id, updatedArticle);
        return ResponseEntity.ok(article);
    }

    // 删除文章
    @DeleteMapping("/article/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

    // 根据标题搜索文章
    @GetMapping("/articles/search")
    public ResponseEntity<List<Article>> searchArticles(@RequestParam String title) {
        List<Article> articles = articleService.searchArticlesByTitle(title);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/authors/stats")
    public ResponseEntity<List<AuthorStatsDTO>> getAuthorsWithArticleCount() {
        List<AuthorStatsDTO> stats = authorService.getAuthorsWithArticleCount();
        return ResponseEntity.ok(stats);
    }
}
