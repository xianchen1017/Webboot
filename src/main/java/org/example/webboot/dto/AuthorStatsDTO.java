package org.example.webboot.dto;

public class AuthorStatsDTO {
    private String authorName;
    private Integer articleCount;

    // 构造器、getter和setter
    public AuthorStatsDTO() {}

    public AuthorStatsDTO(String authorName, Integer articleCount) {
        this.authorName = authorName;
        this.articleCount = articleCount;
    }

    // getters and setters
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }
}