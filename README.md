# GradeVault API

Student Grade Management REST API built with Spring Boot 3.x, providing comprehensive CRUD operations for students, courses, and enrollments with grade tracking and performance analytics.

## Overview

GradeVault is a RESTful API that enables educational institutions to:
- Manage student records with email validation and uniqueness
- Maintain course catalogs with credit validation (1-6 credits)
- Track student enrollments with grade management (0.0-100.0 scale)
- Calculate student performance statistics (average grade, total courses, highest grade)
- Monitor API health and operational status

## Features

- **Full CRUD Operations**: Complete create, read, update, delete for students, courses, and enrollments
- **Data Validation**: Multi-layered validation (Bean Validation + database constraints)
- **Standardized Responses**: Consistent JSON envelope wrapper for all API responses
- **Error Handling**: Comprehensive error handling with field-level validation messages
- **API Documentation**: Interactive Swagger UI for API exploration and testing
- **Health Monitoring**: Spring Boot Actuator endpoints for operational monitoring
- **Database Versioning**: Flyway migrations for reproducible schema management
- **Sample Data**: Pre-seeded data for testing and demonstration

## Prerequisites

- **Java 17** or higher
- **Maven 3.8+**
- **MySQL 8.0+**

## Setup Instructions

### 1. Database Setup

Create the MySQL database and user:

```sql
CREATE DATABASE gradevault_db;
CREATE USER 'gradevault_user'@'localhost' IDENTIFIED BY 'gradevault_pass';
GRANT ALL PRIVILEGES ON gradevault_db.* TO 'gradevault_user'@'localhost';
FLUSH PRIVILEGES;
```

**Using Docker (Alternative)**:

```bash
docker run --name gradevault-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=gradevault_db -e MYSQL_USER=gradevault_user -e MYSQL_PASSWORD=gradevault_pass -p 3306:3306 -d mysql:8.0
```

### 2. Configure Application

Update `src/main/resources/application.yml` if needed (default configuration uses the database created above):

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/gradevault_db
    username: gradevault_user
    password: gradevault_pass
```

### 3. Build the Application

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR directly:

```bash
java -jar target/gradevault-api-1.0.0.jar
```

The application will start on **http://localhost:8080**

### 5. Verify Setup

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health
- **API Info**: http://localhost:8080/actuator/info

## API Endpoints

### Students

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/students` | Get all students |
| GET | `/api/students/{id}` | Get student by ID |
| POST | `/api/students` | Create new student |
| PUT | `/api/students/{id}` | Update student |
| DELETE | `/api/students/{id}` | Delete student (cascades to enrollments) |
| GET | `/api/students/{id}/stats` | Get student statistics |
| GET | `/api/students/{id}/courses` | Get student's enrolled courses |

### Courses

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/courses` | Get all courses |
| GET | `/api/courses/{id}` | Get course by ID |
| POST | `/api/courses` | Create new course |
| PUT | `/api/courses/{id}` | Update course |
| DELETE | `/api/courses/{id}` | Delete course |

### Enrollments

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/enrollments` | Get all enrollments |
| POST | `/api/enrollments` | Enroll student in course |
| PUT | `/api/enrollments/{id}` | Update enrollment grade |
| DELETE | `/api/enrollments/{id}` | Remove enrollment |

## API Examples

### Create Student

```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "dateOfBirth": "2000-05-15"
  }'
```

**Response (201 Created)**:
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "dateOfBirth": "2000-05-15",
    "createdAt": "2024-01-15T10:30:00"
  },
  "message": "Student created successfully",
  "timestamp": "2024-01-15T10:30:00"
}
```

### Get All Students

```bash
curl http://localhost:8080/api/students
```

### Create Course

```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Introduction to Computer Science",
    "courseCode": "CS101",
    "credits": 3
  }'
```

### Create Enrollment

```bash
curl -X POST http://localhost:8080/api/enrollments \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "courseId": 1,
    "grade": 85.5
  }'
```

### Get Student Statistics

```bash
curl http://localhost:8080/api/students/1/stats
```

**Response (200 OK)**:
```json
{
  "success": true,
  "data": {
    "studentId": 1,
    "studentName": "John Doe",
    "averageGrade": 87.5,
    "totalCourses": 4,
    "highestGrade": 95.0
  },
  "message": "Student statistics retrieved successfully",
  "timestamp": "2024-01-15T10:40:00"
}
```

### Update Enrollment Grade

```bash
curl -X PUT http://localhost:8080/api/enrollments/1 \
  -H "Content-Type: application/json" \
  -d '{
    "grade": 92.0
  }'
```

### Delete Student

```bash
curl -X DELETE http://localhost:8080/api/students/1
```

## Response Format

All API responses follow a standardized envelope format:

### Success Response

```json
{
  "success": true,
  "data": { ... },
  "message": "Operation completed successfully",
  "timestamp": "2024-01-15T10:30:00"
}
```

### Error Response

```json
{
  "success": false,
  "error": "Error Type",
  "details": "Detailed error information or field-level errors",
  "timestamp": "2024-01-15T10:30:00"
}
```

### Validation Error Example

```json
{
  "success": false,
  "error": "Validation Failed",
  "details": {
    "email": "Email must be valid",
    "name": "Name is required"
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

## HTTP Status Codes

- **200 OK**: Successful GET, PUT, DELETE operations
- **201 Created**: Successful POST operations
- **400 Bad Request**: Validation errors
- **404 Not Found**: Resource not found
- **409 Conflict**: Duplicate resource (email, course code, enrollment)
- **500 Internal Server Error**: Unexpected errors

## Swagger UI

Access interactive API documentation at:

**http://localhost:8080/swagger-ui.html**

The Swagger UI provides:
- Complete API endpoint documentation
- Request/response schemas
- Interactive testing interface
- Example payloads

## Actuator Endpoints

### Health Check

```bash
curl http://localhost:8080/actuator/health
```

**Response**:
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "MySQL",
        "validationQuery": "isValid()"
      }
    }
  }
}
```

### Application Info

```bash
curl http://localhost:8080/actuator/info
```

## Project Structure

```
gradevault-api/
├── src/main/java/com/gradevault/
│   ├── GradeVaultApplication.java          # Main application class
│   ├── config/
│   │   └── OpenApiConfig.java              # Swagger/OpenAPI configuration
│   ├── controller/
│   │   ├── StudentController.java          # Student REST endpoints
│   │   ├── CourseController.java           # Course REST endpoints
│   │   └── EnrollmentController.java       # Enrollment REST endpoints
│   ├── service/
│   │   ├── StudentService.java             # Student business logic interface
│   │   ├── StudentServiceImpl.java         # Student business logic implementation
│   │   ├── CourseService.java              # Course business logic interface
│   │   ├── CourseServiceImpl.java          # Course business logic implementation
│   │   ├── EnrollmentService.java          # Enrollment business logic interface
│   │   └── EnrollmentServiceImpl.java      # Enrollment business logic implementation
│   ├── repository/
│   │   ├── StudentRepository.java          # Student data access
│   │   ├── CourseRepository.java           # Course data access
│   │   └── EnrollmentRepository.java       # Enrollment data access
│   ├── entity/
│   │   ├── Student.java                    # Student JPA entity
│   │   ├── Course.java                     # Course JPA entity
│   │   └── Enrollment.java                 # Enrollment JPA entity
│   ├── dto/
│   │   ├── StudentRequestDTO.java          # Student request payload
│   │   ├── StudentResponseDTO.java         # Student response payload
│   │   ├── CourseRequestDTO.java           # Course request payload
│   │   ├── CourseResponseDTO.java          # Course response payload
│   │   ├── EnrollmentRequestDTO.java       # Enrollment request payload
│   │   ├── EnrollmentResponseDTO.java      # Enrollment response payload
│   │   ├── EnrollmentUpdateDTO.java        # Enrollment update payload
│   │   ├── StudentStatsDTO.java            # Student statistics payload
│   │   └── ApiResponse.java                # Standardized response envelope
│   ├── mapper/
│   │   ├── StudentMapper.java              # Student entity↔DTO mapper
│   │   ├── CourseMapper.java               # Course entity↔DTO mapper
│   │   └── EnrollmentMapper.java           # Enrollment entity↔DTO mapper
│   └── exception/
│       ├── ResourceNotFoundException.java   # 404 exception
│       ├── DuplicateResourceException.java  # 409 exception
│       ├── ValidationException.java         # Validation exception
│       └── GlobalExceptionHandler.java      # Global error handler
├── src/main/resources/
│   ├── application.yml                      # Application configuration
│   └── db/migration/
│       ├── V1__Create_students_table.sql    # Students table migration
│       ├── V2__Create_courses_table.sql     # Courses table migration
│       ├── V3__Create_enrollments_table.sql # Enrollments table migration
│       └── V4__Insert_sample_data.sql       # Sample data seeding
├── pom.xml                                  # Maven configuration
└── README.md                                # This file
```

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Persistence**: Spring Data JPA + Hibernate
- **Database**: MySQL 8
- **Migration**: Flyway
- **Validation**: Spring Validation (Bean Validation)
- **Monitoring**: Spring Boot Actuator
- **Documentation**: Springdoc OpenAPI 3
- **Mapping**: MapStruct 1.5.5
- **Build Tool**: Maven
- **Utilities**: Lombok 1.18.30

## Data Model

### Student
- `id`: Unique identifier (auto-generated)
- `name`: Student name (required)
- `email`: Email address (required, unique, validated)
- `dateOfBirth`: Date of birth (optional)
- `createdAt`: Creation timestamp (auto-generated)

### Course
- `id`: Unique identifier (auto-generated)
- `name`: Course name (required)
- `courseCode`: Unique course code (required, unique)
- `credits`: Credit hours (required, 1-6)
- `createdAt`: Creation timestamp (auto-generated)

### Enrollment
- `id`: Unique identifier (auto-generated)
- `studentId`: Reference to student (required)
- `courseId`: Reference to course (required)
- `grade`: Grade score (required, 0.0-100.0)
- `enrolledAt`: Enrollment timestamp (auto-generated)
- **Constraint**: Unique combination of (studentId, courseId)

## Validation Rules

- **Student email**: Must be valid format and unique across all students
- **Course code**: Must be unique across all courses
- **Course credits**: Must be between 1 and 6 (inclusive)
- **Enrollment grade**: Must be between 0.0 and 100.0 (inclusive)
- **Duplicate enrollment**: Student cannot be enrolled in the same course twice
- **Cascade delete**: Deleting a student automatically deletes all their enrollments

## Sample Data

The application includes pre-seeded sample data:
- **5 students**: Alice Johnson, Bob Smith, Charlie Brown, Diana Prince, Ethan Hunt
- **4 courses**: CS101, CS201, CS301, CS202
- **10 enrollments**: Various student-course combinations with grades

## Environment Variables

You can override configuration using environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/gradevault_db
export SPRING_DATASOURCE_USERNAME=gradevault_user
export SPRING_DATASOURCE_PASSWORD=gradevault_pass
export SERVER_PORT=8080
```

## Troubleshooting

### Database Connection Issues

If you encounter database connection errors:

1. Verify MySQL is running: `mysql -u root -p`
2. Check database exists: `SHOW DATABASES;`
3. Verify user permissions: `SHOW GRANTS FOR 'gradevault_user'@'localhost';`
4. Check application.yml configuration

### Flyway Migration Errors

If Flyway migrations fail:

1. Check migration files in `src/main/resources/db/migration/`
2. Verify database schema: `USE gradevault_db; SHOW TABLES;`
3. Clean and rebuild: `mvn clean install`

### Port Already in Use

If port 8080 is already in use:

```bash
# Change port in application.yml
server:
  port: 8081
```

Or use environment variable:
```bash
export SERVER_PORT=8081
mvn spring-boot:run
```

## License

This project is licensed under the Apache License 2.0.

## Support

For issues, questions, or contributions, please contact the GradeVault team at support@gradevault.com.
