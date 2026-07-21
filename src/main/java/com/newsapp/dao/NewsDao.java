package com.newsapp.dao;

import com.newsapp.model.News;

import java.util.List;
import java.util.Optional;

public interface NewsDao {

    List<News> findAll();

    Optional<News> findById(Long id);

    List<News> findByCategoryId(Long categoryId);

    News save(News news);

    void deleteById(Long id);
}
