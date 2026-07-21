package com.newsapp.service;

import com.newsapp.model.News;

import java.util.List;

public interface NewsService {

    List<News> getAllNews(Long categoryId);

    News getNewsById(Long id);

    News createNews(News news);

    void deleteNews(Long id);
}
