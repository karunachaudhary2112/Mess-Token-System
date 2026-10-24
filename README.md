# 🍽️ Mess Token System

A full-stack Java web application designed to make hostel mess management smarter and reduce food wastage. The system provides students with a token-based wallet, meal preordering, special food ordering, diet food options, and transaction tracking. Mess staff can monitor meal attendance and skipped meals to plan food preparation more efficiently.

## 🛠️ Tech Stack

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge\&logo=openjdk\&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge\&logo=springboot\&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge\&logo=thymeleaf\&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2%20Database-0E76A8?style=for-the-badge\&logo=databricks\&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge\&logo=apachemaven\&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge\&logo=html5\&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge\&logo=css3\&logoColor=white)

---

## 📌 Problem Statement

Traditional mess systems often prepare fixed quantities of food without knowing how many students will actually eat. Students who skip meals may also lose the value of their prepaid meals, while excess food leads to unnecessary wastage.

## 💡 Our Solution

The Mess Token System introduces a digital token wallet and a meal management platform. Students can manage their balances, preorder or skip meals, explore food options, and view their transaction history. Mess staff can use meal summaries to better understand student attendance and reduce food wastage.

---

## ✨ Features

### 👨‍🎓 Student Portal

* **Student Signup & Login:** Individual student accounts.
* **Personal Dashboard:** View token balance, monthly spending limit, and monthly usage.
* **Profile Photo:** Set a profile photo for the student dashboard.
* **Token Wallet:** Manage mess tokens and track spending.
* **Pay Yearly Mess Fees:** Enter yearly mess fees through the dashboard.
* **Scan & Pay:** Meal payment interface with transaction validation.
* **Transaction History:** View recorded wallet transactions.
* **Weekly Menu:** Explore the available mess menu.
* **Meal Preordering:** Mark breakfast, lunch, or dinner as skipped.
* **Special Food Orders:** Order items such as Maggi, noodles, pizza, momos, and more.
* **Diet Food Orders:** Explore options such as fruit bowls, oats, salads, and boiled eggs.
* **Balance & Spending Validation:** Check available balance and monthly spending limits before orders or payments.

### 🧑‍🍳 Mess Staff Portal

* Separate mess staff login.
* View the number of students expected to eat.
* View skipped meal counts.
* Access meal attendance summaries for improved food planning.

### 🔐 Data & Validation

* Student-specific account information.
* Mess ID validation for meal payments.
* Stock and balance checks for special and diet orders.
* Database-backed student information using Spring Data JPA.

---

## 🛠️ Project Architecture

The application follows a Controller–Service–Repository architecture:

* **Controller:** Handles HTTP requests and application navigation.
* **Service:** Contains business logic, payment validation, and meal operations.
* **Repository:** Communicates with the database using Spring Data JPA.
* **Entity:** Represents application data stored in the database.
* **Thymeleaf Templates:** Render dynamic web pages.

---

## 🚀 Getting Started

### Prerequisites

* Java JDK 17 or later
* Maven, or the included Maven Wrapper
* IntelliJ IDEA or another Java IDE

### Installation

**1. Clone the repository**

```
git clone https://github.com/karunachaudhary2112/Mess-Token-System.git
```

**2. Navigate to the application directory**

```
cd Mess-Token-System/MessTokenWeb
```

**3. Run the application**

On Windows:

```
.\mvnw.cmd spring-boot:run
```

On Linux or macOS:

```
./mvnw spring-boot:run
```

**4. Open the application**

Visit:

```
http://localhost:8080
```

---

## 📂 Project Structure

```
Mess-Token-System/
└── MessTokenWeb/
    └── src/
        └── main/
            ├── java/MessTokenWeb/
            │   ├── Student.java
            │   ├── StudentRepository.java
            │   ├── StudentService.java
            │   ├── StudentController.java
            │   ├── MenuController.java
            │   ├── MenuService.java
            │   ├── MenuItem.java
            │   ├── MessController.java
            │   └── MessTokenWebApplication.java
            └── resources/
                ├── templates/
                ├── static/
                └── application.properties
```

*Note: The structure above highlights key files. Other classes and resources may also be present.*

---

## 🔮 Future Enhancements

* Secure password hashing and improved authentication.
* Store profile photos persistently per student.
* Add administrative tools for managing students, messes, and menu items.
* Improve stock management for special and diet food orders.
* Automate monthly spending limit resets.
* Integrate real QR-code scanning and payment systems.
* Deploy the application to a cloud platform.

---

## 👩‍💻 Author

**Karuna Chaudhary**

Built as a college project to explore full-stack Java development, Spring Boot, database integration, and practical solutions for hostel mess management.

---

⭐ If you find this project interesting, consider giving the repository a star!
