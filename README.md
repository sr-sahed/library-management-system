
# 📚 OOP Library Management System (Spring Boot)

A full-stack, professional Library Management System built using **Java 17+, Spring Boot, Thymeleaf, and H2/MySQL database**. This project demonstrates core Object-Oriented Programming (OOP) principles and enterprise software architecture.

## 🌐 Live Demo
You can access the live application here:  
👉 [Library Management System Live Demo](https://library-management-system-production-554a.up.railway.app)

## 🚀 Features
- **OOP Principles Applied:** 
  - **Abstraction & Inheritance:** `User` abstract class subclassed by `Admin` and `Student`.
  - **Polymorphism:** Role-based access and behavior execution.
  - **Encapsulation:** Secured model states.
- **Role-Based Authentication:**
  - **Admin / Librarian:** Add books with stock management, view inventory, track who borrowed which book, and force-return books.
  - **Student / User:** Search books, borrow available books, view personal borrowing history, and return books.
- **Stock Management:** Tracks total quantity and available quantity of books dynamically.
- **Search Functionality:** Real-time search by book title or author.

## 🛠️ Tech Stack
- **Backend:** Java, Spring Boot, Spring Data JPA, Hibernate, Lombok
- **Frontend:** Thymeleaf, Bootstrap 5, HTML5, CSS3
- **Database:** H2 In-Memory Database
- **Build Tool:** Maven

## 💻 How to Run Locally
1. Clone the repository:
   ```bash
   git clone https://github.com/sr-sahed/library-management-system.git
   ```



2. Navigate to the project directory:
```bash
cd library-management-system

```


3. Run the application using Maven wrapper:
```bash
./mvnw spring-boot:run

```


4. Open your browser and go to: `http://localhost:8080`

### Default Login Credentials:

* **Admin:** `admin` / `1234`
* **Student:** `student` / `1234`
