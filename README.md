# Online-shop

A full-stack e-commerce platform developed during my Bachelor's degree at Paul Lambin Institute, implementing enterprise-level Java architecture patterns.
The application follows a multi-tier architecture with clear separation of concerns.

## Architecture

The backend is structured using the following architectural patterns:

- **Data Transfer Objects (DTO)**: Lightweight objects for transferring data between layers.
- **Data Access Objects (DAO)**: Handles database operations and provides a clean abstraction for data persistence.
- **Use Case Controllers (UCC)**: Manages business logic and orchestrates operations between layers.
- **Factory Pattern**: Implements object creation and dependency management.
- **Interface Segregation**: Ensures loose coupling through well-defined interfaces.


## Technical Stack

**Backend:**

- Jersey REST framework
- PostgreSQL db
- Jenkins CI/CD pipeline integration
- JUnit and Mockito for testing
- Code quality tools: JaCoCo, Checkstyle

**Frontend:**

- Vanilla JavaScript
- Webpack and Babel
- Bootstrap for responsive design
- Axios for API integration

## Key Features

- Secure user authentication and authorization
- Product management system
- Shopping cart functionality
- Order processing
- Responsive user interface

## Development Practices

The project implements several enterprise-level practices:

- N-tier architecture
- Clean code principles
- Automated testing
- Continuous Integration/Deployment
- Code quality monitoring
