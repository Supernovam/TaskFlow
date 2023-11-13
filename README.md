# Task Management Application API (aka TaskFlow)

## Overview
This project is a RESTful API for a Task Management application, built with Java using Spring Boot. The API allows for managing tasks with basic CRUD (Create, Read, Update, Delete) operations.

## Technologies
- Java
- Spring Boot
- Spring Data JPA
- Lombok
- H2 Database (for demo purposes)

## Features
- Create new tasks
- Retrieve a list of all tasks
- Retrieve a specific task by ID
- Update existing tasks
- Delete tasks

## Installation and Running the Application
Ensure you have Java and Gradle installed on your system.
1. Clone the repository: git clone https://github.com/Supernovam/TaskFlow.git
2. Navigate to the project directory: cd taskmanager-api
3.  Build the project:gradle build
4.  Run the application: gradle bootRun
5.  The application will start running on `http://localhost:8080`.

## API Endpoints
| Method | Endpoint        | Description               |
| ------ | --------------- | ------------------------- |
| POST   | /api/tasks      | Create a new task         |
| GET    | /api/tasks      | Retrieve all tasks        |
| GET    | /api/tasks/{id} | Retrieve task by ID       |
| PUT    | /api/tasks/{id} | Update a task             |
| DELETE | /api/tasks/{id} | Delete a task             |

## Testing
Tests can be run by executing: gradle test

## Contributing
Contributions are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[MIT](https://choosealicense.com/licenses/mit/)
      
   
