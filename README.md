# 🍔 QuickBite — Microservices Food Delivery Backend

A production-style food delivery backend built with a microservices architecture using Spring Boot, Kafka, Docker, and MySQL. Each service is independently deployable and communicates asynchronously via Kafka event streaming.

---

## 🏗️ Architecture Overview

```
                        ┌─────────────────┐
                        │   API Gateway   │
                        │  (Spring Cloud) │
                        └────────┬────────┘
                                 │
              ┌──────────────────┼──────────────────┐
              │                  │                  │
     ┌────────▼───────┐ ┌────────▼───────┐ ┌───────▼────────┐
     │  Auth Service  │ │ Order Service  │ │Restaurant Svc  │
     │  (JWT + MySQL) │ │    (MySQL)     │ │   (MySQL)      │
     └────────────────┘ └────────┬───────┘ └────────────────┘
                                 │
                        ┌────────▼────────┐
                        │  Kafka Broker   │
                        └────────┬────────┘
                                 │
                        ┌────────▼────────┐
                        │Notification Svc │
                        │  (Gmail SMTP)   │
                        └─────────────────┘

     ┌──────────────────────────────────────┐
     │     Eureka Discovery Server          │
     │  (all services register here)        │
     └──────────────────────────────────────┘
```

---

## 🧩 Services

| Service | Description | Repo |
|---|---|---|
| **API Gateway** | Single entry point; routes requests to downstream services | [api-gateway](https://github.com/Priyadharshanip/quickbite-api-gateway) |
| **Auth Service** | User registration, login, JWT token generation & validation | [auth-service](https://github.com/Priyadharshanip/quickbite-auth-service) |
| **Order Service** | Place, track, and manage orders; publishes events to Kafka | [order-service](https://github.com/Priyadharshanip/quickbite-order-service) |
| **Restaurant Service** | Manage restaurant listings, menus, and availability | [restaurant-service](https://github.com/Priyadharshanip/quickbite-restaurant-service) |
| **Notification Service** | Consumes Kafka events; sends email notifications via Gmail SMTP | [notification-service](https://github.com/Priyadharshanip/quickbite-notification-service) |
| **Eureka Discovery Server** | Service registry; enables dynamic service discovery | [eureka-server](https://github.com/Priyadharshanip/quickbite-eureka-server) |


---

## ⚙️ Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.x |
| Service Discovery | Netflix Eureka |
| API Gateway | Spring Cloud Gateway |
| Messaging | Apache Kafka |
| Database | MySQL (per service) |
| Authentication | JWT (JSON Web Tokens) |
| Email Notifications | Gmail App Passwords (SMTP) |
| Containerization | Docker + Docker Compose |

---

## 🚀 Running Locally

### Prerequisites
- Java 17+
- Docker & Docker Compose
- MySQL
- Apache Kafka (or use Docker Compose — included)

### Steps

```bash
# 1. Clone all service repos into one folder
mkdir quickbite && cd quickbite

# 2. Start infrastructure (Kafka, Zookeeper, MySQL)
docker-compose up -d

# 3. Start Eureka Discovery Server first
cd quickbite-eureka-server && mvn spring-boot:run

# 4. Start remaining services (in any order)
cd quickbite-auth-service && mvn spring-boot:run
cd quickbite-restaurant-service && mvn spring-boot:run
cd quickbite-order-service && mvn spring-boot:run
cd quickbite-notification-service && mvn spring-boot:run

# 5. Start API Gateway last
cd quickbite-api-gateway && mvn spring-boot:run
```

### Service Ports

| Service | Port |
|---|---|
| API Gateway | 8080 |
| Auth Service | 8081 |
| Order Service | 8082 |
| Restaurant Service | 8083 |
| Notification Service | 8084 |
| Eureka Dashboard | 8761 |

---

## 🔄 Event Flow

```
User places order
       │
       ▼
API Gateway → Order Service
                   │
                   │ publishes OrderPlacedEvent
                   ▼
             Kafka Broker
                   │
                   │ consumes event
                   ▼
          Notification Service
                   │
                   ▼
         Email sent to user via Gmail SMTP
```

---

## 📁 Repository Structure

```
QuickBite (this repo — overview)
├── quickbite-api-gateway
├── quickbite-auth-service
├── quickbite-order-service
├── quickbite-restaurant-service
├── quickbite-notification-service
└── quickbite-eureka-server
```

---

## 👩‍💻 Author

**Priyadharshani P**  
[![LinkedIn](https://img.shields.io/badge/LinkedIn-priyadharshani--p-blue?logo=linkedin)](https://linkedin.com/in/priyadharshani-p)
[![GitHub](https://img.shields.io/badge/GitHub-Priyadharshanip-black?logo=github)](https://github.com/Priyadharshanip)
