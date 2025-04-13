package org.example.webboot.controller;
//src/main/java/org/example/webboot/controller/ArticleController.java
import org.example.webboot.entity.Article;
import org.example.webboot.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/create")
    public String createArticle(@RequestBody Article article) {
        articleService.createArticle(article);
        return "Article created successfully";
    }

    @GetMapping("/user/{userId}")
    public List<Article> getArticlesByUser(@PathVariable int userId) {
        return articleService.getArticlesByUser(userId);
    }
}
