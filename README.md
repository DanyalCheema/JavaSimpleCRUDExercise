# Java Simple CRUD Exercise

This project is a simple Java-based CRUD application built using Spring Boot, JPA, and H2 database. It demonstrates fundamental CRUD operations (Create, Read, Update, Delete) through RESTful APIs.

## Features
- **Add User**: Add new users with name, email, and role.
- **Edit User**: Update existing user details.
- **Delete User**: Remove a user by ID.
- **Search Users**: Search users by role or name.
- **H2 In-Memory Database**: Preconfigured for quick testing.

## Technologies Used
- Java 17
- Spring Boot
- JPA
- H2 Database
- RestTemplate for external API calls

## Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/DanyalCheema/JavaSimpleCRUDExercise.git
   ```
2. Navigate to the project directory:
   ```bash
   cd JavaSimpleCRUDExercise
   ```
3. Build the project:
   ```bash
   mvn clean package
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints
- `POST /users`: Add a new user.
- `PUT /users/{id}`: Update user details.
- `DELETE /users/{id}`: Delete a user.
- `GET /users/search`: Search users by name or role.

## External API Integration
The project integrates with the [JSONPlaceholder](https://jsonplaceholder.typicode.com/) API to fetch TODOs.
