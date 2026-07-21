package com.newsapp.controller;

import com.newsapp.model.News;
import com.newsapp.service.NewsService;
//import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    // GET /api/news
    // GET /api/news?categoryId=2
    @GetMapping
    public List<News> getAllNews(@RequestParam(required = false) Long categoryId) {
        return newsService.getAllNews(categoryId);
    }

    // GET /api/news/101
    @GetMapping("/{id}")
    public News getNewsById(@PathVariable Long id) {
        return newsService.getNewsById(id);
    }

    // POST /api/news
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public News createNews(@RequestBody News news) {
        return newsService.createNews(news);
    }

    // DELETE /api/news/101
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }
}
