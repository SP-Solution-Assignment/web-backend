CREATE TABLE IF NOT EXISTS category (
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS news (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    summary      VARCHAR(500),
    content      TEXT,
    author       VARCHAR(150),
    published_at DATETIME NOT NULL,
    image_url    VARCHAR(500)
);

-- Join table implementing the many-to-many relationship between news and category
CREATE TABLE IF NOT EXISTS news_category (
    news_id     BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (news_id, category_id),
    FOREIGN KEY (news_id) REFERENCES news(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);
