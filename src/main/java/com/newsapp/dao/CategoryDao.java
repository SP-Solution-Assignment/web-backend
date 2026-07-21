package com.newsapp.dao;

import com.newsapp.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {

    List<Category> findAll();

    Optional<Category> findById(Long id);

    Category save(Category category);
}
