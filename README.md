# carrental

Car Rental Full Stack Application

## Prerequisites

- Java 21 and Maven for the backend
- Node and Npm for the frontend
- Postgres 16

### To start the frontend

cd frontend
npm install
npm run dev

### To start the backend

1. Configure `application.properties` for your setup:

   - `spring.datasource.url=jdbc:postgresql://localhost:5432/yourDatabaseName`
   - `spring.datasource.username=yourUsername`
   - `spring.datasource.password=yourPassword`

2. Start the backend server:

cd backend
mvn clean package
./mvnw spring-boot:run
