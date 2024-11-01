# Online Book Store

## Project Overview

You are watching **Online Book Store** project. This application is designed to simplify the process of buying books online.
It addresses the challenges of finding and purchasing books by offering a user-friendly interface for both customers and administrators.
Users can browse books, search them by category, add them to the shopping cart, and place orders, while administrators manage the catalog of available books.

## Technologies Used

- **Spring Boot** - Backend framework for building the core application
- **Spring Security** - For managing authentication and authorization
- **Spring Data JPA** - For interacting with the database
- **MySQL** - Database to store books, categories and users information
- **Docker** - Containerization for easy deployment
- **Swagger** - API documentation and testing
- **MapStruct** - Object mapping
- **Liquibase** - Database change management
- **JUnit & Mockito** - For testing

## Controllers functionalities

### For users

- **Book browsing**: Browse books and search them by category 
- **Cart management**: Add books to shopping cart, remove them, or adjust the desired quantity.
- **Order creation**: Place orders for the books in shopping cart and track their status. 

### For admins:
- **Book management**: Add books, change information about them, delete them.
- **Category management**: Add categories, change information about them, delete them.
- **Order creation**: Update orders status 

## Requirements

- **Java** version 17 and higher
- **Maven** for dependency management
- **Docker** to prepare the environment

## How to set up

1. Clone repository
    ```bash
       git clone https://github.com/f3ops3/book-store.git
       cd book-store
   ```

2. Create .evn file for environment by filling the .env.template
    ```
    MYSQLDB_DATABASE=
    MYSQLDB_USER=
    MYSQLDB_PASSWORD=
    MYSQLDB_ROOT_PASSWORD=

    MYSQLDB_PORT=
    DEBUG_PORT=

    MYSQLDB_LOCAL_PORT=
    MYSQLDB_DOCKER_PORT=
    SPRING_LOCAL_PORT=
    SPRING_DOCKER_PORT=
   ```
3. Build and then start the containers by using Docker Compose
    ```
   docker-compose build
   docker-compose up
   ```
4. The application will be accessible at `http://localhost:<YOUR_PORT>/api`.
 
## Testing

1. You can run tests to check if everything is working as it should be by executing following:
    ```bash
      mvn clean test
    ```
2. If there are any trouble of understanding 
 the purpose of specific controller or 
endpoint you can check out swagger by visiting:
   http://localhost:8080/swagger-ui/index.html

## Challenges faced

1. For proper integration testing, several SQL scripts were created to emulate a realistic database state. If adding new tests, use these scripts to ensure tests remain independent and unaffected by execution order.
2. I used Liquibase in this project to simplify database changes and migrations. All changesets are reliable and easy to manage.

## Postman collection

I've provided Postman collection with all the API requests that can be used. It's located in `postman` folder.

### How to use it

1. **Locate the collection**: The Postman collection is located in the `postman` folder of the project.
    - File path: `./postman/Book store.postman_collection.json`
2. **Import the collection** 
    - In the upper left corner of Postman use `File -> Import` or just press `ctrl + o`to import the collection

---
The book-store project provided me with a wealth of experience and fundamentally changed my understanding of building applications. Through this project, I became familiar with a wide range of libraries and frameworks, gained a clear understanding of their purposes and applied them effectively. 

If you're interested in the project and want to see it in action, I've included a showcase video demonstrating the application usage: https://youtu.be/MJkf99GtqjE  
