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

![Fulwe Architecture](Fulwe%20architecture2.jpeg)

## Business logic

![Buissness logic](Buissness%20logic.jpeg)

In our business logic, we follow a layered architecture to organize and manage various components. The following are key components and their roles:

#### View Object (VO):
- **Description:** View Objects are primarily used in the presentation layer. These objects are transmitted from the web layer to the template rendering engine.
  
#### Data Transfer Object (DTO):
- **Description:** Data Transfer Objects serve as objects for data transfer between the frontend and backend. They are analogous to parameters and are used in communications from the frontend to the backend.

#### Business Object (BO):
- **Description:** Business Objects are internal business objects that are used for communication within the system. They are not exposed externally.

#### Model:
- **Description:** Models correspond one-to-one with database table structures. They are used to transfer data from the Mapper layer to the presentation layer.

#### Controller:
- **Description:** Controllers handle external access control, route requests, perform basic parameter validation, and handle non-reusable, straightforward business logic. Some transaction-independent code is also written in this layer for simplicity.

#### FeignClient:
- **Description:** In a microservices architecture, FeignClients serve as internal interfaces for communication between microservices.

#### Service Layer:
- **Description:** The Service Layer contains relatively specific business logic services.

#### Manager Layer:
- **Description:** The Manager Layer is a common business processing layer with the following characteristics:
  1. It encapsulates third-party platforms, preprocessing return results, and transforming exception information to adapt to upper-layer interfaces.
  2. It sinks common capabilities of the Service Layer, such as caching strategies and middleware processing.
  3. It interacts with the DAO Layer, combining multiple DAOs for reusability.

#### Mapper (Persistence) Layer:
- **Description:** The Mapper Layer is responsible for data access, interacting with the underlying MySQL database for data transactions.

This layered structure helps us maintain a clean separation of concerns, simplifies code organization, and facilitates modularity and scalability in our business logic.


## Database Design

The database design for our application follows a relational model, consisting of several tables that store essential data for our system.

### Entity-Relationship Diagram

![Database](Database.jpeg)

### Additional Details

- **Logical Deletion (Soft Delete):**
  - A `deleted` flag is used in relevant tables to indicate soft deletions. When a user is deleted, the `deleted` flag is set to 1, making the record invisible to users but accessible for administrative purposes.

- **Optimistic Locking (Version Control):**
  - Optimistic locking is implemented in tables such as Products and Orders to manage concurrent updates. Only one user can successfully update a record at the same time.

- **Timestamps (utc_create and utc_update):**
  - `utc_create` and `utc_update` columns capture the creation and last update timestamps.
  - Timestamp values are generated in the backend logic code to ensure consistency and accuracy.

Please refer to the entity-relationship diagram above for a visual representation of our database structure.

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
