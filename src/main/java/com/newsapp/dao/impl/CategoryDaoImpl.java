package com.newsapp.dao.impl;

import com.newsapp.dao.CategoryDao;
import com.newsapp.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public CategoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String select_all = "SELECT id, name FROM category ORDER BY name";
    private static final String select_by_id = "SELECT id, name FROM category WHERE id = ?";
    private static final String insertdata = "INSERT INTO category (name) VALUES (?)";

    @Override
    public List<Category> findAll() {
        String sql = "SELECT id, name FROM category"; // example query
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Category category = new Category();
            category.setId(rs.getLong("id"));
            category.setName(rs.getString("name"));
            return category;
        });
    }


    @Override
    public Optional<Category> findById(Long id) {
        List<Category> results = jdbcTemplate.query(select_by_id, this::mapRow, id);
        return results.stream().findFirst();
    }

    @Override
    public Category save(Category category) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertdata, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getName());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        category.setId(key != null ? key.longValue() : null);
        return category;
    }

    private Category mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new Category(rs.getLong("id"), rs.getString("name"));
    }
}
