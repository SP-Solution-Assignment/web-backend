package com.newsapp.dao.impl;

import com.newsapp.dao.NewsDao;
import com.newsapp.model.News;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class NewsDaoImpl implements NewsDao {

    private final JdbcTemplate jdbcTemplate;

    public NewsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_ALL_NEWS =
            "SELECT id, title, summary, content, author, published_at, image_url " +
            "FROM news ORDER BY published_at DESC";

    private static final String SELECT_NEWS_BY_ID =
            "SELECT id, title, summary, content, author, published_at, image_url " +
            "FROM news WHERE id = ?";

    private static final String SELECT_NEWS_BY_CATEGORY =
            "SELECT DISTINCT n.id, n.title, n.summary, n.content, n.author, n.published_at, n.image_url " +
            "FROM news n JOIN news_category nc ON n.id = nc.news_id " +
            "WHERE nc.category_id = ? ORDER BY n.published_at DESC";

    private static final String INSERT_NEWS =
            "INSERT INTO news (title, summary, content, author, published_at, image_url) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String INSERT_NEWS_CATEGORY =
            "INSERT INTO news_category (news_id, category_id) VALUES (?, ?)";

    private static final String DELETE_NEWS_CATEGORY_BY_NEWS = "DELETE FROM news_category WHERE news_id = ?";

    private static final String DELETE_NEWS_BY_ID = "DELETE FROM news WHERE id = ?";

    @Override
    public List<News> findAll() {
        List<News> newsList = jdbcTemplate.query(SELECT_ALL_NEWS, this::mapRow);
        attachCategoryIds(newsList);
        return newsList;
    }

    @Override
    public Optional<News> findById(Long id) {
        List<News> results = jdbcTemplate.query(SELECT_NEWS_BY_ID, this::mapRow, id);
        Optional<News> news = results.stream().findFirst();
        news.ifPresent(n -> attachCategoryIds(List.of(n)));
        return news;
    }

    @Override
    public List<News> findByCategoryId(Long categoryId) {
        List<News> newsList = jdbcTemplate.query(SELECT_NEWS_BY_CATEGORY, this::mapRow, categoryId);
        attachCategoryIds(newsList);
        return newsList;
    }

    @Override
    @Transactional
    public News save(News news) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_NEWS, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, news.getTitle());
            ps.setString(2, news.getSummary());
            ps.setString(3, news.getContent());
            ps.setString(4, news.getAuthor());
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(news.getPublishedAt()));
            ps.setString(6, news.getImageUrl());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        Long newsId = key != null ? key.longValue() : null;
        news.setId(newsId);

        if (news.getCategoryIds() != null) {
            for (Long categoryId : news.getCategoryIds()) {
                jdbcTemplate.update(INSERT_NEWS_CATEGORY, newsId, categoryId);
            }
        }
        return news;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_NEWS_CATEGORY_BY_NEWS, id);
        jdbcTemplate.update(DELETE_NEWS_BY_ID, id);
    }

    /** Loads news_category mappings for the given news list and attaches categoryIds to each item. */
    private void attachCategoryIds(List<News> newsList) {
        if (newsList.isEmpty()) {
            return;
        }
        List<Long> newsIds = newsList.stream().map(News::getId).collect(Collectors.toList());
        String placeholders = newsIds.stream().map(id -> "?").collect(Collectors.joining(","));
        String sql = "SELECT news_id, category_id FROM news_category WHERE news_id IN (" + placeholders + ")";

        Map<Long, List<Long>> newsIdToCategoryIds = new HashMap<>();
        jdbcTemplate.query(sql, rs -> {
            long newsId = rs.getLong("news_id");
            long categoryId = rs.getLong("category_id");
            newsIdToCategoryIds.computeIfAbsent(newsId, k -> new ArrayList<>()).add(categoryId);
        }, newsIds.toArray());

        for (News news : newsList) {
            news.setCategoryIds(newsIdToCategoryIds.getOrDefault(news.getId(), new ArrayList<>()));
        }
    }

    private News mapRow(ResultSet rs, int rowNum) throws SQLException {
        News news = new News();
        news.setId(rs.getLong("id"));
        news.setTitle(rs.getString("title"));
        news.setSummary(rs.getString("summary"));
        news.setContent(rs.getString("content"));
        news.setAuthor(rs.getString("author"));
        news.setPublishedAt(rs.getTimestamp("published_at").toLocalDateTime());
        news.setImageUrl(rs.getString("image_url"));
        return news;
    }
}
