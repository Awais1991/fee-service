# Fee Service

Payment Service is a Java application developed using Spring Boot (version 3.2.2) and Java 21. It serves as a backend service for handling fee management, configure fees, get fees The application utilizes an H2 database for data storage.

## Technologies

- Java 21
- Spring Boot 3.2.2
- H2 Database
- JUnit
- Swagger for API documentation
- Spring Boot Actuator for monitoring and managing application

## Features

1. **Configure Fee:** The service allows adding and configuring fee against grades

2. **Update fee:** Allows updating fee in the system.

3. **Fee Metadata:**
    - Fee types such as TUITION or TRANSPORTATION
    - Fee frequencies such as ONE TIME or MONTHLY

4. **Swagger Documentation:**
    - API documentation is available through Swagger for easy exploration and integration.
    - Accessible at [http://localhost:8082/fee/swagger-ui/index.html#/](http://localhost:8082/fee/swagger-ui/index.html#/).
### Spring Boot Actuator

- Monitor and manage the application in production with Spring Boot Actuator.
- Endpoints include health, metrics, info, and more.
- Accessible at [http://localhost:8082/fee/actuator](http://localhost:8082/fee/actuator).

## Unit Test Cases

- JUnit test cases are implemented to ensure the reliability and correctness of the application.

## Setup

1. Clone the repository.
2. Configure your IDE or build tool for a Spring Boot application.
3. Run the application and access the Swagger documentation to explore the APIs.


## Endpoints

- **Fee Management:**
    - POST `/api/fees` - Configure new Fee in the system based in given values.
    - PUT `/api/fees/{uuid}` - Update exiting fee based on fee unique identifier and given data.
    - GET `/api/fees/{uuid}` - Fee details by give fee unique identifier.
    - GET `/api/fees` - Get All Fees configured in the system.
    - GET `/api/fees/grades/{grade}` - Get fees against given grade name.
- **MetaData APIs:**
    - GET `/api/fees/fee-types` - Get list of all fee types supported by system.
    - GET `/api/fees/fee-frequencies` - Get the list of supported fee frequencies supported by system.
# Getting Started

To get started with the project, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/Awais1991/fee-service.git

2. Build Application:

   ```bash
   mvn clean install

3. Run Application:

   ```bash
   java -jar fee-0.0.1-SNAPSHOT.jar

4. Access Swagger:

   ```bash
   http://localhost:8082/fee/swagger-ui/index.html#/