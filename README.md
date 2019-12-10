# Clock

Simulator for electronic point control where users can add and check their electronic point registers.


## Built With

* 	[Gradle](https://gradle.org/) - Dependency Management
* 	[JDK](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit 
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring 
Applications
* 	[PostgreSQL](https://www.postgresql.org/) - Open-Source Relational Database Management System
* 	[git](https://git-scm.com/) - Free and Open-Source distributed version control system 
* 	[Swagger](https://swagger.io/) - Open-Source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTful Web services.
*   [JUnit 5](https://junit.org/junit5/)- Unit tests.


## Running the application 

- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to project root folder.
- Run the Gradle command to start the application:
```shell
.\gradlew bootRun
```

## Running the tests
- Open Command Prompt and Change directory (cd) to project root folder.
- Run the Gradle command to start the application:
```shell
.\gradlew test
```

## External Tools Used

* [Postman](https://www.getpostman.com/) - API Development Environment (Testing Docmentation)

## Documentation

* [Postman Collection](https://documenter.getpostman.com/view/2449187/RWTiwzb2) - online, with code auto-generated snippets in cURL, 
jQuery, Ruby,Python Requests, Node, PHP and Go programming languages
* [Postman Collection](https://github.com/AnanthaRajuC/Spring-Boot-Application-Template/blob/master/Spring%20Boot%20Template.postman_collection.json) - offline
* [Swagger](http://localhost:8088/swagger-ui.html) - Documentation & Testing

## Data Model

![dataModel](https://user-images.githubusercontent.com/3866759/70484670-55e55e80-1acb-11ea-8713-5930ef9255c4.png)

- `daily_register_punches` — represents the daily punches created by users;
- `daily_register` — represents the daily registers created by users;
- `time_sheet` — represents the time sheet with the registers created by users by month;
- `work_hours` — represents the worked hours by day;
- `users` — represents the user;


## Packages

- `model` — to hold the entities;
- `repository` — to communicate with the database;
- `service` — to hold the business logic;
- `controller` — to listen to the client;
- `config` -  to hold the application config;
- `util` -  to hold utils functions.

- `resources/` - contains all the static resources and property files.

- `test/` - contains unit and integration tests

- `build.gradle` - contains all the project dependencies
