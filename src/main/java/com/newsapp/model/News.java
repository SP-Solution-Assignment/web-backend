package com.newsapp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class News {

    private Long id;
    private String title;
    private String summary;
    private String content;
    private String author;
    private LocalDateTime publishedAt;
    private String imageUrl;

    private List<Long> categoryIds = new ArrayList<>();

    public News() {
    }

    public News(Long id, String title, String summary, String content, String author,
                LocalDateTime publishedAt, String imageUrl, List<Long> categoryIds) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.author = author;
        this.publishedAt = publishedAt;
        this.imageUrl = imageUrl;
        this.categoryIds = categoryIds != null ? categoryIds : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
