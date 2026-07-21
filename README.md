# News App — Backend (Java + Spring Boot + JDBC + MySQL)

Layered architecture: **Controller → Service → DAO → DB**, plain JDBC (`JdbcTemplate`)
in the DAO layer — no JPA/Hibernate magic, so the SQL is explicit and easy to adapt.

## Folder structure
```
news-backend/
├── pom.xml
└── src/main/
    ├── java/com/newsapp/
    │   ├── NewsAppApplication.java     # main() entry point
    │   ├── model/
    │   │   ├── Category.java
    │   │   └── News.java                # includes List<Long> categoryIds (many-to-many)
    │   ├── dao/
    │   │   ├── CategoryDao.java / impl/CategoryDaoImpl.java
    │   │   └── NewsDao.java / impl/NewsDaoImpl.java   # JdbcTemplate + manual row mapping
    │   ├── service/
    │   │   ├── CategoryService.java / impl/CategoryServiceImpl.java
    │   │   ├── NewsService.java / impl/NewsServiceImpl.java
    │   │   └── ResourceNotFoundException.java
    │   ├── controller/
    │   │   ├── CategoryController.java
    │   │   └── NewsController.java
    │   └── config/
    │       ├── CorsConfig.java          # allows the Vite dev server to call the API
    │       └── GlobalExceptionHandler.java
    └── resources/
        ├── application.properties       # MySQL datasource config
        ├── schema.sql                   # CREATE TABLE: category, news, news_category (join)
        └── data.sql                     # seed data matching the frontend's dummy data
```

## Database

Three tables — `news_category` is the join table implementing the many-to-many
relationship between news and categories:

```
category (id, name)
news (id, title, summary, content, author, published_at, image_url)
news_category (news_id, category_id)   -- composite PK, FKs to both tables
```

`schema.sql` and `data.sql` run automatically on startup
(`spring.sql.init.mode=always` in `application.properties`) and will create the
`newsdb` database/tables and seed them if MySQL is reachable. Turn this off
(`spring.sql.init.mode=never`) once you're managing the schema yourself.

Update the datasource credentials in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/newsdb?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
```

## Run it
```bash
mvn spring-boot:run
```
Server starts on `http://localhost:8080`.

## API endpoints

| Method | Endpoint                       | Description                                   |
|--------|---------------------------------|------------------------------------------------|
| GET    | `/api/categories`               | List all categories                             |
| GET    | `/api/categories/{id}`          | Get one category                                |
| POST   | `/api/categories`                | Create a category                               |
| GET    | `/api/news`                      | List all news                                   |
| GET    | `/api/news?categoryId={id}`      | List news filtered by one category (server-side)|
| GET    | `/api/news/{id}`                 | Full article — used by the news detail page     |
| POST   | `/api/news`                       | Create a news item (`categoryIds: [1,2]` in body)|
| DELETE | `/api/news/{id}`                  | Delete a news item                              |

Example response for `GET /api/news`:
```json
[
  {
    "id": 101,
    "title": "Government Unveils New Digital Economy Policy",
    "summary": "...",
    "content": "...",
    "author": "Nimal Perera",
    "publishedAt": "2026-07-18T09:30:00",
    "imageUrl": "https://placehold.co/800x450?text=Digital+Economy+Policy",
    "categoryIds": [1, 2, 4]
  }
]
```
This matches the shape the React frontend already expects.

## Wiring it to the frontend

In the React project, replace the dummy-data calls with real `fetch`s:
```js
// Home.jsx
fetch('http://localhost:8080/api/categories').then(r => r.json()).then(setCategories)
fetch('http://localhost:8080/api/news').then(r => r.json()).then(setNewsItems)
// or filter server-side:
fetch(`http://localhost:8080/api/news?categoryId=${selectedCategoryId}`)

// NewsDetail.jsx
fetch(`http://localhost:8080/api/news/${id}`).then(r => r.json()).then(setNews)
```
`CorsConfig.java` already allows requests from `http://localhost:5173`
(the Vite dev server's default port).
