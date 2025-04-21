package org.example.webboot.controller;
import org.example.webboot.dto.ArticleDTO;
import org.example.webboot.entity.Article;
import org.example.webboot.entity.Author;
import org.example.webboot.exception.ResourceNotFoundException;
import org.example.webboot.service.ArticleService;
import org.example.webboot.service.AuthorService;
import org.example.webboot.dto.AuthorStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
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
        if (author == null) {
            throw new ResourceNotFoundException("Author not found with id: " + authorId);
        }

        Page<Article> pageResult = articleService.getArticlesByAuthor(author, page, size);

        // 添加日志输出
        System.out.println("查询到的文章数量: " + pageResult.getContent().size());
        pageResult.getContent().forEach(article ->
                System.out.println("文章ID: " + article.getId() + ", 标题: " + article.getTitle()));

        Map<String, Object> response = new HashMap<>();
        response.put("articles", pageResult.getContent());
        response.put("currentPage", pageResult.getNumber() + 1);
        response.put("totalItems", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // 新增文章
    @PostMapping("/article")
    public ResponseEntity<?> addArticle(@RequestBody Map<String, Object> requestData) {
        try {
            Article article = new Article();
            article.setTitle((String) requestData.get("title"));
            article.setContent((String) requestData.get("content"));

            Long authorId = Long.parseLong(requestData.get("authorId").toString());
            Author author = authorService.getAuthorById(authorId);
            if (author == null) {
                return ResponseEntity.badRequest().body("Author not found");
            }
            article.setAuthor(author);

            Article savedArticle = articleService.addArticle(article);
            return ResponseEntity.ok(savedArticle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating article: " + e.getMessage());
        }
    }

    // 编辑文章
    @PutMapping("/article/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDTO) {
        try {
            Article existingArticle = articleService.getArticleById(id);
            if (existingArticle == null) {
                return ResponseEntity.notFound().build();
            }

            // 更新字段
            existingArticle.setTitle(articleDTO.getTitle());
            existingArticle.setContent(articleDTO.getContent());

            Article result = articleService.updateArticle(existingArticle);

            // 转换为DTO返回
            ArticleDTO responseDTO = new ArticleDTO();
            responseDTO.setId(result.getId());
            responseDTO.setTitle(result.getTitle());
            responseDTO.setContent(result.getContent());
            responseDTO.setAuthorId(result.getAuthor() != null ? result.getAuthor().getId() : null);

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "更新文章失败",
                            "message", e.getMessage(),
                            "timestamp", LocalDateTime.now()
                    ));
        }
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
