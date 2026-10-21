# 🍽 Mess Token System

A full-stack Java web application that solves a common hostel/college mess problem: students pay a fixed mess fee but lose money whenever they skip a meal — and the mess cooks a fixed quantity regardless of actual turnout, causing food wastage. This system converts mess fees into a **token-based wallet with a monthly spending cap**, so unused tokens are never wasted, and adds a **meal preorder feature** so the mess always knows exactly how much food to prepare.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2%20Database-0E76A8?style=for-the-badge&logo=databricks&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

---

## 📌 The Problem

- Students who stay up late studying miss breakfast; students with afternoon classes miss lunch — multiple times every month — and that already-paid-for meal money is simply lost.
- When food isn't tasty, students skip meals in groups without warning the mess, which keeps cooking fixed quantities. The surplus food is wasted.

## 💡 The Solution

A token-based wallet (₹1 = 1 token) with a **monthly spending cap** (yearly fee ÷ 12), so tokens can never all be spent in one month — and tokens from skipped meals automatically stay safe for later use. A **preorder system** lets students mark meals they'll skip, giving the mess real-time visibility into actual demand.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🏠 **Home Page** | Landing page with separate Student and Mess Staff portals |
| 👤 **Student Signup/Login** | Secure registration and authentication per student |
| 💰 **Token Wallet** | Yearly fee converts to tokens with an auto-calculated monthly spending limit |
| 📷 **Scan & Pay** | QR-code based payment simulation, password-protected before every transaction |
| 📜 **Transaction History** | Full, timestamped log of all credits and debits |
| 🍽️ **Preorder / Meal Cancellation** | Students mark meals they'll skip to reduce food wastage |
| 🧑‍🍳 **Mess Staff Portal** | Separate login showing a live summary of confirmed vs. skipped meals |
| 🔒 **Mess Restriction** | Tokens are only usable at a student's assigned mess |

---

## 🛠 Tech Stack

- **Backend:** Java, Spring Boot (Controller–Service–Repository architecture)
- **Frontend:** Thymeleaf, HTML5, CSS3
- **Database:** H2 (file-based, via Spring Data JPA)
- **Build Tool:** Maven
- **QR Code:** QRCode.js

---

## 🚀 Getting Started

### Prerequisites
- Java JDK 17+
- Maven (or use the included `mvnw` wrapper)
- IntelliJ IDEA (recommended) or any Java IDE

### Run Locally

```bash
# Clone the repository
git clone https://github.com/karunachaudhary2112/Mess-Token-System.git

# Navigate into the project
cd Mess-Token-System/MessTokenWeb

# Run the application
./mvnw spring-boot:run
```

Then open your browser at:
```
http://localhost:8080
```

---

## 📂 Project Structure

```
MessTokenWeb/
└── src/main/
    ├── java/MessTokenWeb/
    │   ├── Student.java              # JPA entity
    │   ├── StudentRepository.java    # Database layer
    │   ├── StudentService.java       # Core wallet/business logic
    │   ├── StudentController.java    # Student-facing routes
    │   ├── MessController.java       # Mess staff routes
    │   └── MessTokenWebApplication.java
    └── resources/
        ├── templates/                # Thymeleaf HTML pages
        ├── static/                   # CSS, logo, images
        └── application.properties    # Database configuration
```

---

## 🔮 Future Scope

- Migrate from H2 to MySQL/PostgreSQL for production deployment
- Real QR/RFID hardware integration at mess counters
- Admin panel for managing multiple messes
- Automated monthly reset of spending limits and preorder status
- Deploy as a live, publicly accessible website

---

## 👩‍💻 Author

**Karuna Chaudhary**
Built as a college project — a complete full-stack Java application from problem statement to deployment.
