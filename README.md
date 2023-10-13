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

## System architecture diagram

![Fulwe Architecture](Fulwe%20architecture.jpg)


## Business logic

## Folder Structure

- **/src**
  - Source code directory.

    - **/main**
      - Main application files.

        - **/java/com/fulwin/api**
          - API-related classes and controllers.

        - **/java/com/fulwin/config**
          - Configuration files for the application.

        - **/java/com/fulwin/controller**
          - Controllers handling HTTP requests.

        - **/java/com/fulwin/Enums**
          - Enumeration classes.

        - **/java/com/fulwin/handler**
          - Classes handling specific tasks.

        - **/java/com/fulwin/mapper**
          - Database mappers for mapping data between Java objects and the database.

        - **/java/com/fulwin/pojo**
          - Plain Old Java Objects (POJOs) representing data entities.

        - **/java/com/fulwin/service**
          - Business logic and service classes.

        - **/java/com/fulwin/util**
          - Utility classes.

        - **/java/com/fulwin/webhook**
          - Classes related to webhooks.

        - **/resources**
          - Configuration files, application properties, and static resources.

            - **/static**
              - JS, CSS, video plugins, and other static assets.

            - **/templates**
              - Thymeleaf templates for dynamic web pages.

- **/src/test**
  - Unit and integration tests.

- **/docker**
  - Docker-related files for containerization.


## Project Lifecycle

1. **Market Research:** Conducted comprehensive market research to identify key requirements and user needs.
2. **Design:** Defined business requirements and created an architecture design for the platform.
3. **Implementation:** Developed the platform from scratch, ensuring a scalable and efficient system.
4. **Testing:** Rigorous testing phases to guarantee functionality, security, and reliability.
5. **Deployment:** Successfully deployed the platform on AWS using Docker and Jenkins CI/CD.

## Future Plans

As we look ahead, our plans include continuous improvement, adding new features, and enhancing the platform based on user feedback.
