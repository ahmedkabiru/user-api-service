[![Codacy Badge](https://app.codacy.com/project/badge/Grade/ac3659f8d95a4c9487917e2f5655afc0)](https://www.codacy.com/gh/ahmedkabiru/user-api-service/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ahmedkabiru/user-api-service&amp;utm_campaign=Badge_Grade)  [![Codacy Badge](https://app.codacy.com/project/badge/Coverage/ac3659f8d95a4c9487917e2f5655afc0)](https://www.codacy.com/gh/ahmedkabiru/user-api-service/dashboard?utm_source=github.com&utm_medium=referral&utm_content=ahmedkabiru/user-api-service&utm_campaign=Badge_Coverage)
## User Service
The user service implements the following actions;
1. Create a user
2. Update a User
3. Deactivate a user
4. Send email account verification
5. Fetch all registered users

## Technology
Following libraries were used during the development of this service:
- **Java 16** - Language
- **Spring Boot 2.4.4** - Server side framework
- **Maven** - Build tool
- **Docker** - Containerizing framework
- **MySQL** - Relational database
- **Flyway** - Database migration
- **Swagger** - API documentation
- **Thymeleaf** - Templating engine
- **Mail Server** - AWS Simple Mail Service
- **JUnit** - Unit testing
- **JaCoCo** - Code coverage

## Application Structure
The application is structured into the following packages;
- **configuration** - contains configuration classes
- **dto** - Data Transfer Object
- **models** - contains database entities
- **service** - handles business logic
- **controller** - rest endpoints and route mapping
- **repository** - handles query and data persistence
  <p align="left">  
    <img src="https://github.com/ahmedkabiru/1wa-user-service/blob/main/docs/images/project-structure-2.png" alt="project structure">  
  </p>  
## Data Transfer Object With Record Class
The DTO was created using Java 16 Record class 
```  
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public record  UserDto(
        @NotBlank(message = "title is required")
        @JsonProperty("title")
        String title,

        @NotBlank(message = "firstname is required")
        @JsonProperty("firstName")
        String firstName,

        @NotBlank(message = "lastname is required")
        @JsonProperty("lastName")
        String lastName,

        @Email(message = "please provide a valid email address")
        @JsonProperty("email")
        String email,

        @NotBlank(message = "mobile number is required")
        @JsonProperty("mobile")
        String mobile,

        @NotBlank(message = "password is required")
        @JsonProperty("password")
        String password,

        @NotNull(message = "specify at least one role")
        @JsonProperty("role")
        String role
) { }

```
## Running the server locally
Before running the application install MySQL with "brew install mysql" for MAC user.  
Update the application.yml with your MySQL credentials
```  
spring:  
 datasource: url: jdbc:mysql://localhost:3306/userdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull&createDatabaseIfNotExist=true&useSSL=false username:    password:   
  jpa:  
 properties: hibernate: dialect: org.hibernate.dialect.MySQL5Dialect ddl-auto: validate generate-ddl: true show-sql: true  
```  
Use the below command to build and run the application:
```  
mvn clean package -DskipTests  
```  
then run
```  
mvn spring-boot:run  
```  

The application would start on port 9001.

## Running the server in Docker Container
#### Before you start

- Install Docker and Docker Compose.
- Make sure to build the project: `mvn package [-DskipTests]`
- update the MySQL environment variable inside docker-compose.yml
##### Docker Compose #####  
To build the application using docker-compose simply execute the following command :
```  
docker-compose build  
```  
```  
docker-compose up  
```  
## API Documentation
The documentation is generated using Swagger 3.  
It is accessible via :   
http://localhost:9001/swagger-ui/index.html

<p align="center">  
    <br>  
    <img width="600" src="https://github.com/ahmedkabiru/1wa-user-service/blob/main/docs/images/swagger.png">  
</p>

## Monitoring and Metrics
Actuator endpoint expose the following operation:

- **/metrics** Shows ???metrics??? information for the current application.

- **/health** Shows application health information.

- **/info** Displays arbitrary application info.

All the actuator endpoints is available via <http://localhost:9001/actuator>.