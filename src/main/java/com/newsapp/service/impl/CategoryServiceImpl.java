package com.newsapp.service.impl;

import com.newsapp.dao.CategoryDao;
import com.newsapp.model.Category;
import com.newsapp.service.CategoryService;
import com.newsapp.service.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    public Category createCategory(Category category) {
        return categoryDao.save(category);
    }
}
