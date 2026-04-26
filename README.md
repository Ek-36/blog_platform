# Blog Platform REST API

A RESTful backend for a blog platform built with Spring Boot and Spring Security. Supports full CRUD operations for Posts, Categories, Tags, and Users with JWT-based stateless authentication.

## Features

- JWT Authentication & Authorization via Spring Security
- Full CRUD — Posts, Categories, Tags, Users
- Relational entity mappings (User–Post, Category–Post, Tag–Post)
- DTO mapping with MapStruct
- Structured error handling across all endpoints

## Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java |
| Framework | Spring Boot |
| Security | Spring Security, JWT |
| ORM | JPA / Hibernate |
| Database | PostgreSQL |
| Mapping | MapStruct |

## Project Structure

src/main/java/com/blogProject/blog
├── config/        # Security & JWT configuration
├── controller/    # REST controllers
├── service/       # Business logic
├── repository/    # JPA repositories
├── entity/        # JPA entities
├── dto/           # Data Transfer Objects
└── mapper/        # MapStruct mappers

## API Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | /auth/authenticate | Login & get JWT | No |
| GET | /categories | List all categories | No |
| POST | /categories | Create category | Yes |
| DELETE | /categories/{id} | Delete category | Yes |
| GET | /tags | List all tags | No |
| POST | /tags | Create tag | Yes |
| DELETE | /tags/{id} | Delete tag | Yes |
| GET | /posts | List published posts | No |
| GET | /posts/drafts | List draft posts | Yes |
| POST | /posts | Create post | Yes |
| PUT | /posts/{id} | Update post | Yes |
| GET | /posts/{id} | Get post by ID | No |
| DELETE | /posts/{id} | Delete post | Yes |

## Getting Started

### Prerequisites

- Java 21
- PostgreSQL
- Maven

### Setup

```bash
git clone https://github.com/Ek-36/blog-platform.git
cd blog-platform
```

Configure `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/blogdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

```bash
./mvnw spring-boot:run
```
