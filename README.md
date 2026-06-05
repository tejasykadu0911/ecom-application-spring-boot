# E-Commerce Application

A RESTful e-commerce backend built with Spring Boot 3, providing APIs for user management, product catalog, shopping cart, and order processing.

## Tech Stack

- **Java 17**
- **Spring Boot 3.5** — Web, Data JPA, Actuator
- **Hibernate / H2** — ORM with in-memory database (development)
- **Lombok** — Boilerplate reduction
- **Maven** — Build tool

## Project Structure

```
src/main/java/com/app/ecom/
├── controller/     # REST controllers (User, Product, Cart, Order)
├── service/        # Business logic
├── repository/     # Spring Data JPA interfaces
├── model/          # JPA entities and enums
├── dto/            # Request/Response DTOs
└── exception/      # Custom exceptions
```

## API Endpoints

### Users — `/api/users`
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/users` | List all users |
| GET | `/api/users/{id}` | Get user by ID |
| POST | `/api/users` | Create user |
| PUT | `/api/users/{id}` | Update user |

### Products — `/api/products`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/products` | Create product |
| GET | `/api/products` | List active products |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Soft-delete product |
| DELETE | `/api/products/search?keyword=` | Search active in-stock products |

### Cart — `/api/cart`
Requires `X-User-Id` header on all requests.

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/cart/add` | Add item to cart |
| DELETE | `/api/cart/items/{productId}` | Remove item from cart |
| GET | `/api/cart/usercart` | Get user's cart |

### Orders — `/api/orders`
Requires `X-User-Id` header.

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/orders` | Create order from cart |

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+ (or use the included `./mvnw` wrapper)

### Run locally

```bash
./mvnw spring-boot:run
```

### Build

```bash
./mvnw clean package
java -jar target/ecom-application-0.0.1-SNAPSHOT.jar
```

## Configuration

Default config is in `src/main/resources/application.yml`.

| Setting | Value |
|---------|-------|
| Port | `8080` |
| Database | H2 in-memory (`jdbc:h2:mem:test`) |
| DDL strategy | `create` (tables recreated on each start) |
| H2 Console | `http://localhost:8080/h2-console` |

## Monitoring

Spring Boot Actuator is enabled with all endpoints exposed.

| URL | Description |
|-----|-------------|
| `http://localhost:8080/actuator/health` | Health check |
| `http://localhost:8080/actuator/info` | App info |
| `http://localhost:8080/actuator` | All actuator endpoints |

## Key Design Decisions

- **Soft delete** — Products are deactivated via an `active` flag rather than deleted from the database.
- **Stock validation** — Cart additions are validated against current stock quantity.
- **Header-based identity** — Cart and order operations identify the caller via the `X-User-Id` HTTP header.
- **DTO separation** — API contracts use dedicated request/response DTOs, keeping entities internal.