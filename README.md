# Twitter Clone

## Description

This project is an API that emulates a basic Twitter-like experience, where users can post messages up to 140 characters and register them within a unique thread (stream). Built using the Quarkus framework, this application currently functions as a monolith with plans to transition to a microservices architecture on AWS Lambda. The system includes the following primary entities:

- **User**: Represents a user who can create posts.
- **Stream**: Represents a thread that organizes posts in a structured feed.
- **Post**: Represents an individual message with content, an author, and a timestamp.

## Technologies Used

- **Quarkus**: Lightweight, fast Java framework optimized for microservices.
- **Java**: Primary programming language.
- **Jakarta EE**: For building the RESTful API.
- **JWT (JSON Web Tokens)**: Secures user authentication with AWS Cognito.
- **AWS Lambda**: Planned future migration target to break down the monolith into microservices.
- **S3**: For serving and hosting the JavaScript frontend.

## Project Architecture

The architecture follows a layered approach, ensuring a clear separation between different responsibilities within the application. It currently includes three main layers:

1. **Controllers**: RESTful endpoints to interact with the client-side application.
   - `PostController`: Manages endpoints for creating and retrieving posts.
   - `StreamController`: Handles the creation and retrieval of streams or threads.
   - `UserController`: Manages user registration and retrieval.

2. **Services**: Contains the core business logic of the application.
   - `PostService`: Processes logic related to posts, including saving and validating data.
   - `StreamService`: Manages the flow and structure of threads.
   - `UserService`: Manages operations related to user accounts and their data.

3. **DTOs** (Data Transfer Objects): Simplifies API interactions and abstracts away the data model.
   - `PostDTO`, `StreamDTO`, `UserDTO`: Represent simplified versions of each entity, optimized for communication between client and server.

4. **Persistence Layer**: Manages database operations, separating data access logic from other application logic.
   - **Repositories**: Contains classes to perform CRUD operations on the database.

### AWS Integration (Future State)

The monolithic design is slated for a transition to a microservices architecture using AWS Lambda. The project will eventually leverage serverless functions to handle each primary entity independently, improving scalability and maintainability.

## Requirements

To run this project locally, you will need the following:

- Java 11 or higher
- Maven 3.6 or higher
- AWS account for configuring services such as Cognito and Lambda (for future enhancements)

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/juaneortiz1/Twitter-Clone.git
   cd twitter-clone
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   ./mvnw quarkus:dev
   ```

   The application will be accessible at `http://localhost:8080`.

## Video Demonstration

*Video demonstration link to be added.*

## Authors

This project was created by:
- Juliana Briceño
- Erick Montero
- Juan Esteban Ortiz



