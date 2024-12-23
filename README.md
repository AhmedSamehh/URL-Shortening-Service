# [URL-Shortening-Service](https://roadmap.sh/projects/url-shortening-service)

## Overview
This project is a simple RESTful API for a URL Shortening Service built using **Spring Boot** and **PostgreSQL**. The service allows you to:

- Create a short URL for a given original URL.
- Retrieve the original URL from a short URL.
- Update or delete a short URL.
- Track the number of times a short URL is accessed.

## Features

- Automatically generate short URLs.
- Store and retrieve URLs using PostgreSQL.
- Track access statistics.
- Full CRUD functionality.

## Technologies

- Spring Boot
- PostgreSQL
- Hibernate (JPA)
- Maven

## API Endpoints

### 1. Create Short URL
**POST** `/shorten`

Request:
```json
{
  "originalUrl": "https://example.com"
}
```
Response:
```json
{
  "shortUrl": "YZIh5tVi"
}
```

### 2. Retrieve Original URL
**GET** `/{shortUrl}`

Response:
```json
"https://example.com"
```

### 3. Update Short URL
**PUT** `/update`

Request:
```json
{
  "shortUrl": "YZIh5tVi",
  "originalUrl": "https://updated-example.com"
}
```

### 4. Delete Short URL
**DELETE** `/{shortUrl}`

Response:
```json
{
  "message": "Short URL deleted."
}
```

### 5. Get Statistics
**GET** `/stats/{shortUrl}`

Response:
```json
{
  "originalUrl": "https://example.com",
  "accessCount": 10
}
```

## How to Run

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd url-shortening-service
   ```

2. Configure PostgreSQL:
    - Create a database named `url`.
    - Update `application.properties` with your credentials:
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/url
      spring.datasource.username=your_username
      spring.datasource.password=your_password
      ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Test the API using tools like Postman or cURL.

