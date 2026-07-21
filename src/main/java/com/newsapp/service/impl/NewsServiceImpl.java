package com.newsapp.service.impl;

import com.newsapp.dao.NewsDao;
import com.newsapp.model.News;
import com.newsapp.service.NewsService;
import com.newsapp.service.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsDao newsDao;

    public NewsServiceImpl(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public List<News> getAllNews(Long categoryId) {
        if (categoryId != null) {
            return newsDao.findByCategoryId(categoryId);
        }
        return newsDao.findAll();
    }

    @Override
    public News getNewsById(Long id) {
        return newsDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with id: " + id));
    }

    @Override
    public News createNews(News news) {
        if (news.getPublishedAt() == null) {
            news.setPublishedAt(LocalDateTime.now());
        }
        return newsDao.save(news);
    }

    @Override
    public void deleteNews(Long id) {
        getNewsById(id);
        newsDao.deleteById(id);
    }
}
