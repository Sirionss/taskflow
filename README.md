# TaskFlow - Task Management REST API

A RESTful API for task management built with Java, Spring Boot, and PostgreSQL. Features full CRUD operations, advanced filtering, custom exception handling, JWT authentication, role-based access control, and comprehensive unit testing.

## Tech Stack

- **Java 17**
- **Spring Boot 4.0**
- **Spring Data JPA** - database access with auto-generated queries
- **Spring Security + JWT** - authentication and authorization
- **Role-based access control** - ADMIN and USER permissions
- **PostgreSQL** - relational database
- **JUnit 5 + Mockito** - unit testing (20 tests)
- **Maven** - build and dependency management

## API Endpoints

### Authentication (public)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT token |

### Tasks - Read (USER + ADMIN)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get task by ID |
| POST | `/api/tasks` | Create a new task |
| GET | `/api/tasks/status/{status}` | Filter by status (TODO, IN_PROGRESS, DONE) |
| GET | `/api/tasks/priority/{priority}` | Filter by priority (LOW, MEDIUM, HIGH) |
| GET | `/api/tasks/status/{status}/priority/{priority}` | Filter by status and priority |
| GET | `/api/tasks/before/{date}` | Tasks with deadline before date |
| GET | `/api/tasks/after/{date}` | Tasks with deadline after date |
| GET | `/api/tasks/title/{keyword}` | Search tasks by title |
| GET | `/api/tasks/description/{keyword}` | Search tasks by description |
| GET | `/api/tasks/status/{status}/count` | Count tasks by status |
| GET | `/api/tasks/status/{status}/sorted` | Tasks by status sorted by deadline |

### Tasks - Modify (ADMIN only)
| Method | Endpoint | Description |
|--------|----------|-------------|
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |
| DELETE | `/api/tasks/status/{status}` | Delete all tasks by status |

## Architecture

```
HTTP Request -> Controller -> Service -> Repository -> PostgreSQL
```

Controller handles HTTP, Service handles business logic, Repository handles database access. Each layer has a single responsibility and depends only on the layer below it.

## Project Structure

```
src/main/java/com/taskflow/taskflow/
├── TaskflowApplication.java
├── model/
│   ├── Task.java              # Task entity -> tasks table
│   ├── Status.java            # Enum: TODO, IN_PROGRESS, DONE
│   ├── Priority.java          # Enum: LOW, MEDIUM, HIGH
│   ├── Assignable.java        # Interface for task assignment
│   ├── User.java              # User entity -> users table
│   └── Role.java              # Enum: USER, ADMIN
├── repository/
│   ├── TaskRepository.java    # Task data access
│   └── UserRepository.java    # User data access
├── service/
│   └── TaskService.java       # Business logic
├── controller/
│   ├── TaskController.java    # Task REST endpoints
│   └── AuthController.java    # Auth REST endpoints
├── exception/
│   ├── TaskNotFoundException.java
│   └── GlobalExceptionHandler.java
└── security/
    ├── SecurityConfig.java              # Security rules and filters
    ├── JwtService.java                  # Token generation and validation
    ├── JwtAuthenticationFilter.java     # Intercepts requests, checks tokens
    └── CustomUserDetailsService.java    # Loads users from database
```

## Getting Started

### Prerequisites
- Java 17+
- PostgreSQL
- Maven

### Setup

1. Clone the repository:
```bash
git clone https://github.com/Sirionss/taskflow.git
cd taskflow
```

2. Create a PostgreSQL database:
```sql
CREATE DATABASE taskflow;
```

3. Copy the example config and fill in your credentials:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

### Usage Example

Register a user:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "damian", "password": "password123"}'
```

Register an admin:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123", "role": "ADMIN"}'
```

Login and get a token:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "damian", "password": "password123"}'
```

Create a task (use the token from login):
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{"title": "Fix bug", "description": "Fix login issue", "priority": "HIGH", "deadline": "2026-04-25T10:00:00"}'
```

## Running Tests

```bash
./mvnw test
```

20 unit tests: 16 covering TaskService methods with Mockito for repository mocking, 4 covering JwtService for token generation and validation.

## Planned Features

- Swagger/OpenAPI documentation
- Pagination and sorting for task lists
- Task assignment to users