# Aplazo Backend Application

This project is a Spring Boot application designed to run with Java 17, Gradle, and Docker. The application serves as a backend service, including functionalities like handling user requests and database interactions.

## Requirements

Before you begin, ensure you have met the following requirements:

- **Java 17**: Ensure you have Java Development Kit (JDK) 17 installed.
- **Gradle**: Install Gradle for building the project.
- **Docker**: Docker should be installed and running on your machine.

## Installation

Follow these steps to get your development environment set up:

### 1. Clone the Repository

Clone this repository to your local machine.

### 2. Build image

Build the Docker image 

```sh
docker build -t aplazo-backend .
```

Run the container (one or the other): 
```sh
docker run -p 8080:8080 aplazo-backend
```
or
```sh
docker-compose up --build
```

## Log Access

Once the app is running, you should be able to access it at: `http://localhost:8080`.

To view logs for the Docker container, use:

```sh
docker logs <container_id>
```

## Run tests using gradle

To run tests with coverage, runm the following command:

```sh
./gradlew testWithCoverage
```

You should be able to see something like:

![Test Coverage](https://github.com/Agm91/AplazoBackend/blob/main/testCoverage.png)

## Video

Functionality video [here](https://drive.google.com/drive/folders/1wHZ1NmDKr3DqPVN5ZFT-IOUlbbEOh27C?usp=sharing).

## Considerations

Docker files and application.properties should never have real urls, pass nor users.

**Clean architecture is essential. I prioritized speed this time and still, got 82% test covered.**

## Thank you

I wanted to express my gratitude for letting me participate for the role. I hope we can work together and build great things for millions of people!

**Best!**
