![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Subscription Platform

## Overview

Subscription Platform is a backend REST API built with Spring Boot and JPA for managing operators, subscription plans, and customer subscriptions.

The application allows administrators to manage operators and plans, while customers can browse available plans, subscribe to services, change subscriptions, and cancel subscriptions according to defined business rules.

---

## Features

### Authentication & Security

* JWT Authentication
* Spring Security authorization
* Role-based access control
* Admin and Customer roles
* Redis token blacklist support

### Operator Management

* Manage operators
* Retrieve operator information

### Plan Management

* Create plans
* Update plans
* Delete plans
* View all plans
* View active plans only
* Filter plans by service type
* View plans by operator

### Subscription Management

* Subscribe to a plan
* View customer subscriptions
* Change subscription plans
* Cancel subscriptions
* Track subscription status and cancellation date

---

## Business Rules

The system enforces the following rules:

* A customer can have only one active Internet subscription.
* A customer can have only one active Mobile subscription.
* Only active plans can be subscribed to.
* New subscriptions are created with ACTIVE status.
* Cancelled subscriptions store a cancellation date.
* Plan changes are only allowed within the same operator.
* Plan changes are only allowed within the same service type.
* Invalid operations generate custom business exceptions.

---

## Domain Model

Main entities:

* Customer
* CustomerDetail
* Operator
* Plan
* Subscription

Relationships are implemented using JPA and Hibernate.

---

## Technical Stack

* Java 25
* Spring Boot 4
* Spring Data JPA
* Spring Security
* JWT Authentication
* MySQL 8
* Redis
* MapStruct
* Lombok
* Swagger / OpenAPI
* Maven
* Docker & Docker Compose

---

## Validation & Error Handling

* DTO-based API design
* Bean Validation annotations
* Global Exception Handling
* Custom Exceptions:

  * ResourceNotFoundException
  * BusinessRuleException

---

## Seed Data

### Users

#### Admin User

Email:

[admin@example.com](mailto:admin@example.com)

Password:

password

#### Regular User

Email:

[user@example.com](mailto:user@example.com)

Password:

password

### Operators

* Telia
* Telenor

### Plans

Multiple active and inactive plans are automatically created during application startup.

---

## Running the Project

### Start Infrastructure

```bash
docker-compose up -d
```

### Run Application

Navigate to:

```bash
subscription-api
```

Run:

```bash
./mvnw spring-boot:run
```

or start:

```text
SubscriptionApiApplication
```

from IntelliJ IDEA.

---

## API Documentation & Testing

Swagger UI is available at:

http://localhost:8080/swagger-ui.html

All API endpoints were manually tested using both Swagger UI and Postman.

The following areas were verified successfully:

* Authentication and authorization
* Plan management operations
* Subscription management operations
* Business rule enforcement
* Request validation
* Exception handling
* Role-based access control

---

## Project Structure

* Controllers
* Services
* Repositories
* DTOs
* Mappers
* Security
* Exception Handling
* Configuration

The project follows a layered architecture and separates domain entities from API contracts using DTOs.

---

## Author

Muthana Fouad

Lexicon Java Backend Development Program
