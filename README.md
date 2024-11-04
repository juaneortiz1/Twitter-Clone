# Twitter Clone

## Description

This project is an API designed to allow users to create posts of up to 140 characters and register them in a unique thread (stream), similar to Twitter. It is developed using Quarkus and has been implemented as an initial monolith, with plans to decompose it into microservices on AWS Lambda. The project includes the following entities:

- **User**: Represents the users who can create posts.
- **Stream**: Represents a thread of posts.
- **Post**: Represents a post that contains content, an author, and a timestamp.

## Technologies Used

- **Quarkus**: Java framework for creating microservices.
- **Java**: Primary programming language.
- **Jakarta EE**: For building the REST API.
- **JWT (JSON Web Tokens)**: For user authentication via AWS Cognito.
- **AWS Lambda**: For future separation of the monolith into microservices.
- **S3**: For deploying the JavaScript application.

## Project Structure

The project is divided into the following parts:

1. **Controllers**:
    - `PostController`: Handles the creation and retrieval of posts.
    - `StreamController`: Handles the creation and retrieval of threads.
    - `UserController`: Handles the creation and retrieval of users.

2. **Services**:
    - `PostService`: Business logic for managing posts.
    - `StreamService`: Business logic for managing streams.
    - `UserService`: Business logic for managing users.

3. **DTOs**:
    - `PostDTO`, `StreamDTO`, `UserDTO`: Data Transfer Objects for API operations.

4. **Persistence**:
    - Repositories to handle interaction with the database.

## Requirements

To run this project locally, ensure you have the following:

- Java 11 or higher.
- Maven 3.6 or higher.
- Access to AWS for configuring Cognito and Lambda (future).

## Installation

1. **Clone the repository**:
   ```bash
   git clone <REPOSITORY_URL>
   cd <REPOSITORY_NAME>
