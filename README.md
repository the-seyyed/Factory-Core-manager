<div align="center">

# 🏭 Factory Core Manager

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)

> A comprehensive management system tailored for modern factory operations, human resources, and inventory control.

</div>

---

## 📖 Overview

**Factory Core Manager** is a robust web application and RESTful API designed to automate and streamline the daily operations of a manufacturing facility. It seamlessly bridges Human Resources (attendance, payroll, loans) with Production lines (inventory, product recipes, stock management), fully localized with Persian (Jalali) calendar support.

## ✨ Key Features

### 👥 Human Resources & Payroll
* **Worker Profiles:** Manage detailed employee information, addresses, and dependency records.
* **Smart Attendance:** Track check-ins, check-outs, and vacations. Automatically calculates over-time and under-time.
* **Dynamic Payroll:** Configurable salary rules based on background years, marital status, and children.
* **Financial Transactions:** Manage employee loans, advances, and auto-calculate annual financial summaries.

### 📦 Inventory & Production
* **Stock Control:** Real-time tracking of raw materials and inventory levels.
* **Product Recipes (BOM):** Define exact material requirements for each product.
* **Production Tracking:** Automatically adjust inventory stocks based on manufactured product recipes.

### 🤝 Customer Management
* **CRM Basics:** Register and manage factory clients and their contact information.

## 🛠️ Tech Stack

* **Backend Core:** Java 21, Spring Boot 3.2.5
* **Database:** PostgreSQL supported via Spring Data JPA
* **Mapping & Utils:** ModelMapper, Lombok, Criteria API
* **Localization:** `persian-date-time` library for all internal date calculations
* **Frontend:** HTML, CSS, JavaScript (Fetch API) integration

## 🚀 Getting Started

### Prerequisites
* **Java 21**
* **Maven**
* **PostgreSQL** (running on default port 5432)

### Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/mmd-anbari/Factory-Core-manager.git](https://github.com/mmd-anbari/Factory-Core-manager.git)
   cd Factory-Core-manager