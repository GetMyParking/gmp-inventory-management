# GMP Inventory Management Service

A microservice for managing inventory items, locations, pricing, and fulfillment within the GetMyParking ecosystem.

## Overview

The Inventory Management Service provides REST APIs for managing physical inventory items, their locations, pricing information, and fulfillment tracking. It integrates with Parking and Permit services to provide a comprehensive inventory management solution.

## Technology Stack

- **Java**: Spring Boot application
- **Database**: PostgreSQL with Flyway migrations
- **Build Tool**: Maven
- **API Framework**: Spring Web (REST)
- **HTTP Client**: Retrofit for external service calls
- **Caching**: Redis
- **Message Queue**: AWS SQS / ActiveMQ
- **Monitoring**: New Relic, Elastic APM

## Project Structure

```
gmp-inventory-management/
├── inventory-application/     # Spring Boot application module
│   ├── src/main/java/
│   │   └── com/gmp/inventory/
│   │       ├── InventoryApplication.java
│   │       └── controller/
│   │           ├── HealthCheckController.java
│   │           └── InventoryController.java
│   └── src/main/resources/
│       ├── config/
│       │   └── application.properties
│       └── db-migrations/     # Flyway migration scripts
│
└── inventory-core/            # Core business logic module
    └── src/main/java/
        └── com/gmp/inventory/
            ├── client/         # External service clients
            │   ├── ParkingClient.java
            │   └── PermitClient.java
            ├── configurations/ # Configuration classes
            │   ├── RetrofitClientConfig.java
            │   └── SwaggerConfig.java
            ├── persistence/
            │   └── model/      # JPA entities
            ├── repository/     # Data access layer
            └── service/        # Business logic layer
```

## API Endpoints

### Health Check
- **GET** `/v1/healthCheck` - Service health check endpoint

### Inventory Management
Base path: `/v1/inventory`

- **GET** `/items?companyId={companyId}` - Get all inventory items for a company
- **GET** `/items/{id}` - Get inventory item by ID
- **POST** `/items` - Create a new inventory item
- **PUT** `/items/{id}` - Update an inventory item
- **DELETE** `/items/{id}` - Delete an inventory item
- **GET** `/items/search?companyId={companyId}&branchId={branchId}&category={category}&status={status}` - Search inventory with filters
- **GET** `/items/branch/{branchId}` - Get inventory items by branch ID
- **GET** `/items/location/{locationId}` - Get inventory items by location ID
- **GET** `/items/serial?serialPrefix={prefix}&serialNo={no}` - Get inventory item by serial number

## Database Schema

The service uses the following main tables:

- **inventory** - Core inventory items
- **inventory_location** - Physical locations for inventory
- **inventory_location_parking_map** - Mapping between inventory locations and parking
- **inventory_parking_metadata** - Metadata for parking-related inventory
- **inventory_pricing** - Pricing information for inventory items
- **inventory_fulfilment** - Fulfillment tracking for inventory

All tables include common fields from `BaseEntity`:
- `tenant` - Multi-tenancy support
- `created_at` - Creation timestamp
- `updated_at` - Last update timestamp
- `deleted` - Soft delete flag

## Configuration

### Environment Variables

The service requires the following environment variables (configured in `application.properties`):

#### AWS Configuration
- `AWS_CLI_PROFILE` - AWS CLI profile name
- `AWS_CLI_REGION` - AWS region

#### Database Configuration
- `DB_URL_ENV` - PostgreSQL connection URL
- `DB_USER_ENV` - Database username
- `DB_PASSWORD_ENV` - Database password
- `LEAK_DETECTION_THRESHOLD` - HikariCP leak detection threshold

#### Service URLs
- `PARKING_URL` - Parking service base URL
- `PERMIT_URL` - Permit service base URL
- `GO_AUTHENTICATION_URL` - Authentication service URL

#### Redis Configuration
- `REDIS_HOST` - Redis server host
- `REDIS_PORT` - Redis port (default: 6379)

#### ActiveMQ Configuration
- `ACTIVE_MQ_BROKER_URL` - ActiveMQ broker URL
- `ACTIVE_MQ_USERNAME` - ActiveMQ username
- `ACTIVE_MQ_PASSWORD` - ActiveMQ password

#### Other Configuration
- `ALLOWED_ORIGINS` - CORS allowed origins
- `CONFIG_URL` - Spring Cloud Config server URL
- `RABBITMQ_HOST` - RabbitMQ host for Spring Cloud Bus
- `RABBITMQ_USERNAME` - RabbitMQ username
- `RABBITMQ_PASSWORD` - RabbitMQ password

## Running the Service

### Prerequisites
- Java 17+
- Maven 3.6+
- PostgreSQL database
- Redis (optional, for caching)

### Local Development

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd gmp-inventory-management
   ```

2. **Set up environment variables**
   - Copy and configure environment variables as listed above

3. **Run database migrations**
   - Flyway will automatically run migrations on startup

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   cd inventory-application
   mvn spring-boot:run
   ```

   Or use the provided run script:
   ```bash
   ./setup/run.sh
   ```

6. **Verify the service**
   ```bash
   curl http://localhost:8090/v1/healthCheck
   ```

### Docker

Build and run using Docker:

```bash
docker build -t gmp-inventory-management .
docker run -p 8090:8090 gmp-inventory-management
```

## External Service Integrations

### Parking Service
The service integrates with the Parking Service to:
- Fetch parking information by ID
- Get parking lists
- Retrieve parking IDs by company ID

### Permit Service
The service integrates with the Permit Service to:
- Fetch permit allocation information by ID

## Multi-Tenancy

The service supports multi-tenancy through the `tenant` field in all entities. The tenant value is extracted from:
1. Request header `X-GMP-TENANT` (if provided)
2. MDC context (fallback)

## Database Migrations

Database migrations are managed using Flyway. Migration scripts are located in:
```
inventory-application/src/main/resources/db-migrations/
```

Migration files follow the naming convention: `V{version}__{description}.sql`

## Monitoring and Observability

- **Elastic APM**: Distributed tracing
- **Spring Boot Actuator**: Health checks and metrics (exposed at `/actuator/*`)

## Development

### Branch Strategy
- `main` - Production branch
- `qa` - QA testing branch (created from main)
- `develop` - Development branch (created from qa)
- `feature/*` - Feature branches (created from develop)

### Code Style
- Follow Java coding conventions
- Use Lombok for reducing boilerplate code
- Use ModelMapper for entity-DTO conversions

## License

Copyright © GetMyParking. All rights reserved.
