#  Fintech API – Backend Assignment

##  Overview

This project is a **Spring Boot-based backend system** that simulates a fintech-style user onboarding and transaction flow.

It includes:

* User registration with OTP verification
* JWT-based authentication
* Account creation and balance management
* Money transfer between users
* Transaction history tracking

---

##  Tech Stack

* Java 17+
* Spring Boot
* Spring Security
* JPA / Hibernate
* PostgreSQL
* JWT (JSON Web Token)

---

##  Project Architecture

The project follows a **layered architecture**:

```
Controller → Service → Repository
```

### Modules:

* `user` → registration & verification
* `auth` → login & JWT
* `otp` → OTP management
* `account` → user account
* `transaction` → transfer & history
* `core` → exception handling & utilities
* `config` → security & password encoding

---

##  Features Implemented

### 1. User Registration with OTP

* Register user with name, email, mobile, password
* OTP generated and stored with expiry
* User status = `PENDING`

### 2. OTP Verification

* Validate OTP
* Activate user (`ACTIVE`)
* Automatically create account with ₹1000 balance

### 3. Login (JWT Authentication)

* Login using email & password
* Returns JWT token

### 4. Money Transfer

* Transfer money between users
* Validations:

    * Sender/receiver exists
    * Sufficient balance
    * Valid amount
    * Prevent self-transfer

### 5. Transaction History

* Fetch all transactions for a user

---

##  API Endpoints

###  Auth APIs

#### Register

```
POST /api/auth/register
```

#### Verify OTP

```
POST /api/auth/verify-otp
```

#### Login

```
POST /api/auth/login
```

---

###  Transaction APIs

#### Transfer Money

```
POST /api/transactions/transfer
```

#### Get Transaction History

```
GET /api/transactions/{email}
```

---

##  Sample Requests

### Register

```json
// {
//   "name": "Sender",
//   "email": "sender@gmail.com",
//   "mobile": "9778234872",
//   "password": "sender123"
// }

{
  "name": "receiver",
  "email": "receiver@gmail.com",
  "mobile": "9778234875",
  "password": "receiver123"
}
```

---

### Verify OTP

```json
// {
//   "email": "sender@gmail.com",
//   "otp": "159018" // otp varies
// }

{
  "email": "receiver@gmail.com",
  "otp": "134963" // otp varies
}
```

---

### Login

```json
{
  "email": "sender@gmail.com",
  "password": "sender123"
}

// {
//   "email": "receiver@gmail.com",
//   "password": "receiver123"
// }
```

---

### Transfer

```json
{
  "senderEmail": "sender@gmail.com",
  "receiverEmail": "receiver@gmail.com",
  "amount": 200
}
```
### Get Transactions

```json

{{base_url}}/api/transactions/receiver@gmail.com

```

---

* Email is used instead of userId for simplicity and readability.

## ️ Setup Instructions

### 1. Clone Repository

```bash
git clone https://github.com/nibin-joseph05/fintech-api.git
cd fintech-api
```

### 2. Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fintech
spring.datasource.username=postgres
spring.datasource.password=your_password
```

---

### 3. Run Application

```bash
mvn spring-boot:run
```

---

### 4. Test APIs

Use Postman collection provided.

---

## ️ Notes

* OTP is printed in console (for testing purposes)
* Security is partially open for testing APIs
* In production:

    * JWT filters should secure endpoints
    * OTP should be sent via SMS/Email
    * Secrets should be stored securely

---

##  Key Concepts Demonstrated

* Layered architecture
* REST API design
* Exception handling (Global handler)
* Input validation
* Transaction management (`@Transactional`)
* JWT authentication
* Modular project structure

---

##  Submission Includes

* GitHub Repository
* Postman Collection
* Demo Video 
* Link : https://drive.google.com/file/d/1D2OWdew3DZHigUkSSCY5fR5EyYxy38b-/view?usp=drive_link

---

##  Author

**Nibin Joseph**
Java Backend Developer

---
