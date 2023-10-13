# Fulwe (Second-Hand Goods Trading Platform)

Welcome to the Fulwe, Second-Hand Goods Trading Platform, a dynamic e-commerce solution designed to streamline transactions for college students seeking to buy and sell second-hand items.

## Project Overview

This platform was developed using Java Spring Boot, Bootstrap, and Thymeleaf, combining robust technology to provide a seamless user experience. Whether you're a student looking for affordable items or aiming to declutter, our platform offers a user-friendly interface and secure transactions.

## Key Features

- **Dynamic E-commerce:** Facilitate buying and selling of second-hand items among college students.
- **Full Lifecycle Management:** Solely responsible for the project's entire lifecycle, from inception to deployment on AWS using Docker with a Jenkins CI/CD pipeline.
- **Security Measures:** Implemented robust security with Shiro and Spring Security, ensuring a safe and secure environment.
- **Payment Integration:** Integrated Stripe for secure and reliable payment processing.
- **Efficient Project Management:** Effectively managed the project using Git, resulting in a 30% reduction in development time.

## Technical Stack

- **Backend:** Java Spring Boot
- **Frontend:** Bootstrap, Thymeleaf
- **Database:** MySQL with MyBatis for optimized data access times.
- **Deployment:** AWS, Docker, Jenkins CI/CD pipeline
- **Security:** Shiro, Spring Security
- **Payment Integration:** Stripe

## API Documentation

### Endpoints

- **GET /items**
  - Retrieve a list of available items.

- **POST /items**
  - Create a new item listing.

- **GET /items/{itemId}**
  - Retrieve details of a specific item.

- **PUT /items/{itemId}**
  - Update details of a specific item.

- **DELETE /items/{itemId}**
  - Delete a specific item listing.

- **POST /transactions**
  - Initiate a transaction for purchasing an item.

#### Request Format

- **GET /items**
  - No request body.

- **POST /items**
  - Request Body:
    ```json
    {
      "name": "Item Name",
      "description": "Item Description",
      "price": 20.99
    }
    ```

- **GET /items/{itemId}**
  - No request body.

- **PUT /items/{itemId}**
  - Request Body:
    ```json
    {
      "name": "Updated Item Name",
      "description": "Updated Item Description",
      "price": 25.99
    }
    ```

- **DELETE /items/{itemId}**
  - No request body.

- **POST /transactions**
  - Request Body:
    ```json
    {
      "itemId": "123",
      "buyerUserId": "456",
      "quantity": 1
    }
    ```

#### Response Format

- All endpoints respond with JSON objects containing relevant data.

### Authentication

- API endpoints are secured using token-based authentication. Include the authentication token in the request header.

## Folder Structure

- **/src**
  - Source code directory containing Java Spring Boot application files.

- **/src/main/resources**
  - Configuration files, application properties, and static resources.

- **/src/main/java/com/example/controller**
  - Controllers handling HTTP requests.

- **/src/main/java/com/example/model**
  - Data models and entities.

- **/src/main/java/com/example/service**
  - Business logic and service classes.

- **/src/main/resources/templates**
  - Thymeleaf templates for dynamic web pages.

- **/src/test**
  - Unit and integration tests.

- **/docker**
  - Docker-related files for containerization.

- **/docs**
  - Documentation files.

- **/scripts**
  - Miscellaneous scripts for database setup, maintenance, etc.

## Project Lifecycle

1. **Market Research:** Conducted comprehensive market research to identify key requirements and user needs.
2. **Design:** Defined business requirements and created an architecture design for the platform.
3. **Implementation:** Developed the platform from scratch, ensuring a scalable and efficient system.
4. **Testing:** Rigorous testing phases to guarantee functionality, security, and reliability.
5. **Deployment:** Successfully deployed the platform on AWS using Docker and Jenkins CI/CD.

## Future Plans

As we look ahead, our plans include continuous improvement, adding new features, and enhancing the platform based on user feedback.
