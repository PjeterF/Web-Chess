## Web-Chess
An online chess application built with Spring Boot and React, where players can play against each other or against an AI opponent.
## Features
- Singleplayer and Multiplayer modes
- Real time updates using Web Sockets
- Move validation at the backend
- Game history
- User authentication
## Setup
### Backend
1. Configure database connection in application.properties
```
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=WebChess;trustServerCertificate=true;
spring.datasource.username=sa
spring.datasource.password=sa
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
```
2. Build and run
```
cd web-chess/backend
mvn clean install
mvn spring-boot:run
```
### Frontend
Install dependencies and run
```
cd web-chess/frontend
npm install
npm start
```
