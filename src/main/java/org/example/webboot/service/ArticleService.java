package org.example.webboot.service;

import org.example.webboot.entity.Article;
import org.example.webboot.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    public void createArticle(Article article) {
        articleMapper.insert(article);
    }

    public List<Article> getArticlesByUser(int userId) {
        return articleMapper.findByUserId(userId);
    }
}
