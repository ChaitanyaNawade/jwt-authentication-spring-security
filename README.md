<div align="center">

# 🔐 JWT Authentication — Spring Boot + Spring Security

### Stateless authentication & authorization using JSON Web Tokens

[![Java](https://img.shields.io/badge/Java-24-orange?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.15-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.5-green?style=for-the-badge&logo=springsecurity)](https://spring.io/projects/spring-security)
[![JWT](https://img.shields.io/badge/JJWT-0.12.7-yellow?style=for-the-badge&logo=jsonwebtokens)](https://github.com/jwtk/jjwt)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](#-license)

</div>

---

## 📖 Overview

A production-style **stateless JWT authentication system** built with Spring Boot and Spring Security. Instead of relying on server-side sessions, every request is authenticated using a **signed JSON Web Token**, making the API scalable and ready for distributed/microservice environments.

This project was built as a hands-on deep-dive into how real-world backend systems handle login, token issuance, and request-level authorization — covering user registration with **BCrypt password hashing**, **JWT generation & validation**, and a **custom security filter chain**.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔑 **User Registration** | Register users with securely hashed passwords (BCrypt) |
| 🪪 **JWT Login** | Authenticate credentials and receive a signed JWT token |
| 🛡️ **Custom Auth Filter** | `OncePerRequestFilter` intercepts every request, validates the token, and sets the Spring Security context |
| ♾️ **Stateless Sessions** | No server-side session state — fully token-driven (`SessionCreationPolicy.STATELESS`) |
| 🔒 **Protected Endpoints** | Endpoints are locked down until a valid `Authorization: Bearer <token>` header is provided |
| 🗄️ **MySQL Persistence** | User data persisted via Spring Data JPA / Hibernate |

---

## 🏗️ Architecture & Request Flow

```
                  ┌─────────────────────┐
   1. POST /save  │   Register User      │  → password hashed (BCrypt) → saved in MySQL
                  └─────────────────────┘

                  ┌─────────────────────┐
   2. POST /login │  AuthenticationMgr   │  → validates credentials → JwtService issues token
                  └─────────────────────┘

                  ┌─────────────────────────────────────────────┐
   3. GET /xyz    │  JWTAuthFilter (runs before every request)   │
      + Bearer    │  → extracts token → validates signature/exp  │
      <token>     │  → loads UserDetails → sets SecurityContext  │
                  └─────────────────────────────────────────────┘
                                    │
                                    ▼
                        ✅ Request reaches Controller
```

---

## 🛠️ Tech Stack

- **Language:** Java 24
- **Framework:** Spring Boot 3.5.15
- **Security:** Spring Security 6.5 (`DaoAuthenticationProvider`, custom `OncePerRequestFilter`)
- **Token Library:** JJWT (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`) 0.12.7
- **Persistence:** Spring Data JPA + Hibernate + MySQL
- **Password Hashing:** BCryptPasswordEncoder
- **Build Tool:** Maven

---

## 📂 Project Structure

```
src/main/java/com/Chaitanya/JWT_Authentication
├── configuration
│   └── Security.java              # Security filter chain, AuthenticationManager, password encoder
├── controller
│   ├── usercontroller.java        # /save, /welcome endpoints
│   ├── AuthController.java        # /login endpoint
│   └── AuthRequest.java           # Login request DTO
├── entity
│   └── UserInfo.java              # User entity (name, password, role)
├── repository
│   └── userRepository.java        # Spring Data JPA repository
└── service
    ├── CustomUserDetailService.java  # Loads user from DB for Spring Security
    ├── JwtService.java               # Generates & validates JWT tokens
    └── JWTAuthFilter.java             # Intercepts requests & authenticates via token
```

---

## 🚀 Getting Started

### Prerequisites
- Java 24+
- Maven
- MySQL running locally

### 1. Clone the repository
```bash
git clone https://github.com/ChaitanyaNawade/jwt-authentication-spring-security.git
cd jwt-authentication-spring-security
```

### 2. Configure the database
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the application
```bash
mvn spring-boot:run
```
App starts on `http://localhost:8080`

---

## 📡 API Endpoints

### 1️⃣ Register a new user
```http
POST /save
Content-Type: application/json

{
    "name": "chaitanya",
    "password": "test123"
}
```
**Response:** `201 Created` → user saved with hashed password and default role `USER`

---

### 2️⃣ Login & get JWT token
```http
POST /login
Content-Type: application/json

{
    "username": "chaitanya",
    "password": "test123"
}
```
**Response:** `200 OK` → returns a signed JWT token string

---

### 3️⃣ Access a protected endpoint
```http
GET /welcome
Authorization: Bearer <your_jwt_token>
```
**Response:** `200 OK` → `"Welcome users"`

> ❌ Without a valid token → `403 Forbidden`

---

## 🧪 Testing with Postman

| Step | Method | Endpoint | Auth Required |
|---|---|---|---|
| Register | `POST` | `/save` | ❌ No |
| Login | `POST` | `/login` | ❌ No |
| Protected route | `GET` | `/welcome` | ✅ Yes (`Bearer <token>`) |

---

## 📚 What I Learned

- How JWT-based **stateless authentication** differs from traditional session-based auth
- Building a **custom security filter** and wiring it into Spring Security's filter chain
- Configuring `AuthenticationManager` and `DaoAuthenticationProvider` explicitly
- Signing & verifying tokens securely using `SecretKey` (HMAC-SHA)
- Debugging real-world Spring Security issues (missing beans, filter ordering, 403 vs 401 handling)

---

## 🔮 Future Improvements

- [ ] Add refresh token support
- [ ] Role-based endpoint authorization using `@PreAuthorize`
- [ ] Global exception handling for invalid/expired tokens
- [ ] Move secrets to environment variables
- [ ] Add unit & integration tests

---

## 👤 Author

**Chaitanya Nawade**
🎓 B.E. Information Technology, Savitribai Phule Pune University
💼 Aspiring Java Backend Developer

[![GitHub](https://img.shields.io/badge/GitHub-ChaitanyaNawade-181717?style=flat&logo=github)](https://github.com/ChaitanyaNawade)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-ChaitanyaNawade-0077B5?style=flat&logo=linkedin)](https://linkedin.com/in/ChaitanyaNawade)

---

## 📄 License

This project is licensed under the MIT License — free to use for learning and reference.

<div align="center">

⭐ If you found this project helpful, consider giving it a star!

</div>
