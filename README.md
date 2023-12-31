# ChaTop API

## Description

The ChaTop API serves as the server for the ChaTop application. Developed in Spring Boot 3 and Java 17, it incorporates
Spring-doc OpenAPI and Swagger UI for comprehensive documentation.

## Features

The ChaTop API encompasses the following key features:

- User Authentication with **JWT** (utilizing io.jsonwebtoken library)
- User registration
- Rental creation
- Rental display
- Rental update
- Sending Messages

## Installation

Before running the ChaTop Backend API, follow these installation steps:

1. Clone this repository.
2. Install JDK 17 (Java Development Kit) on your local machine.
3. Install Maven locally.
4. Install MySQL on your local machine and create a database for the application.
5. Configure the necessary environment variables in your system or within your IDE before running the application.

### Required Environment Variables

- `MYSQL_URL`: The url of your MySQL database. For example, `jdbc:mysql://localhost:3306/`.
- `MYSQL_DATABASE`: The name of your MySQL database. For example, `db_chatop`.
- `MYSQL_USER`: Your MySQL username.mysql_database
- `MYSQL_PASSWORD`: Your MySQL password.
- `IMAGE_STORAGE_PATH`: The local path where the application should store Rental images. For
  example, `C:/Users/username/Desktop/ChatopImages/`.
- `IMAGE_URL`: The URL path for accessing the stored Rental images. For
  example, `http://localhost:8080/api/v1/rentals/images/`.
- `JWT_SECRET_KEY`: A secret key for generating JSON Web Token (JWT) signature. Ensure it is at least 32 characters long
  for security reasons or generate one using a dedicated website,
  e.g., [the site](https://www.freeformatter.com/hmac-generator.html#before-output).

## Running The Application

### Run the application using a JAR file

- Generate a JAR in the root folder of the project by executing:
  ```bash
  mvn clean install spring-boot:run

## Testing with Postman

You can use [Postman](https://www.postman.com/) to test the ChaTop API. Download the Postman collection and environment script:

- [ChaTop API Postman Collection](src/main/resources/script/Rest Chaton.postman_collection.json)

## Testing with Swagger UI
- Run the application ChaTop API
- Access the Swagger UI documentation at http://localhost:8080/swagger-ui/index.html
- Register a user by sending a POST request to the `/api/auth/register` endpoint
- Login by sending a POST request to the `/api/auth/login` endpoint
- Copy the JWT token from the response
- Click on the `Authorize` button on the top right corner of the Swagger UI page
- Paste the JWT token in the `Value` field and click on the `Authorize` button
- `You can now test the API endpoints`

## The ChaTop backend application is developed using the following technologies:

- **Java:** Version 17 [![Java Version](https://img.shields.io/badge/Java-17-blue)](https://img.shields.io/badge/Java-17-blue)
- **Spring Boot:** Version 3.1.4 [![Spring Boot Version](https://img.shields.io/badge/Spring%20Boot-3.1.4-brightgreen)](https://img.shields.io/badge/Spring%20Boot-3.1.4-brightgreen)
- **Spring Security:** Version 6.1.5 [![Spring Security Version](https://img.shields.io/badge/Spring%20Security-6.1.5-orange)](https://img.shields.io/badge/Spring%20Security-6.1.5-orange)
- **Spring Web:** Starter for building web applications [![Spring Web](https://img.shields.io/badge/Spring%20Web-informational)](https://img.shields.io/badge/Spring%20Web-informational)
- **MySQL Connector/J:** Runtime dependency for MySQL database connectivity [![MySQL Connector/J](https://img.shields.io/badge/MySQL%20Connector%2FJ-informational)](https://img.shields.io/badge/MySQL%20Connector%2FJ-informational)
- **Project Lombok:** Used for reducing boilerplate code [![Project Lombok](https://img.shields.io/badge/Project%20Lombok-informational)](https://img.shields.io/badge/Project%20Lombok-informational)
- **JWT (JSON Web Token):** Java library for working with JSON Web Tokens
  - jjwt-api: Version 0.11.5 [![jjwt-api](https://img.shields.io/badge/jjwt--api-0.11.5-yellow)](https://img.shields.io/badge/jjwt--api-0.11.5-yellow)
  - jjwt-impl: Version 0.11.5 [![jjwt-impl](https://img.shields.io/badge/jjwt--impl-0.11.5-yellow)](https://img.shields.io/badge/jjwt--impl-0.11.5-yellow)
  - jjwt-jackson: Version 0.11.5 [![jjwt-jackson](https://img.shields.io/badge/jjwt--jackson-0.11.5-yellow)](https://img.shields.io/badge/jjwt--jackson-0.11.5-yellow)
- **Spring Data JPA:** Starter for using Spring Data JPA with Hibernate [![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-informational)](https://img.shields.io/badge/Spring%20Data%20JPA-informational)
- **Spring Validation:** Starter for validation in Spring Boot applications [![Spring Validation](https://img.shields.io/badge/Spring%20Validation-informational)](https://img.shields.io/badge/Spring%20Validation-informational)
- **javax.annotation:** API for the JavaTM programming language that defines a contract for the use of annotations [![javax.annotation](https://img.shields.io/badge/javax.annotation-informational)](https://img.shields.io/badge/javax.annotation-informational)
- **Passay:** A password policy enforcement library
  - Version 1.6.2 [![Passay 1.6.2](https://img.shields.io/badge/Passay-1.6.2-informational)](https://img.shields.io/badge/Passay-1.6.2-informational)
  - Version 1.0 [![Passay 1.0](https://img.shields.io/badge/Passay-1.0-informational)](https://img.shields.io/badge/Passay-1.0-informational)
- **Springdoc OpenAPI:** Starter for OpenAPI and Swagger UI documentation [![Springdoc OpenAPI Version](https://img.shields.io/badge/Springdoc%20OpenAPI-2.0.2-green)](https://img.shields.io/badge/Springdoc%20OpenAPI-2.0.2-green)
  - Version 2.0.2 [![Springdoc OpenAPI 2.0.2](https://img.shields.io/badge/Springdoc%20OpenAPI-2.0.2-green)](https://img.shields.io/badge/Springdoc%20OpenAPI-2.0.2-green)