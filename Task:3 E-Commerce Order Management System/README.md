# E-Commerce Order Management System

## Project Description

This project is a console based E-Commerce Order Management System developed using Java. The main aim of this project is to understand Object Oriented Programming concepts and how they are used in a real application.

The system allows users to manage products, register customers, place orders, process payments and update order status. It also provides reports to view customer and order details.

All data is stored in memory using HashMap, so there is no need for any database connection.

---

## Objective

The objective of this project is to create a simple E-Commerce application using Java and OOP concepts such as classes, objects, interfaces, encapsulation and abstraction.

---

## Technologies Used

* Java 21
* IntelliJ IDEA
* Java Collections Framework
* UUID
* ArrayList
* HashMap
* LocalDateTime
* Git and GitHub

---

## Features

### Product Management

* Add new products
* View all products
* Restock products
* Maintain product stock

### Customer Management

* Register new customer
* View customer details
* Update customer information

### Order Management

* Create new order
* Add items to order
* View order details
* Cancel orders

### Payment Processing

* Simulated Stripe payment processing
* Authorize payments
* Prevent invalid payments

### Order Status Tracking

Order status can be updated through different stages:

NEW → PAID → SHIPPED → DELIVERED

Orders can also be cancelled when required.

### Reports

* View all orders
* View all customers
* Filter orders by status
* Order summary with grand total

---

## Project Structure

```text
src
│
├── Product.java
├── Customer.java
├── LineItem.java
├── Order.java
├── OrderStatus.java
│
├── PaymentGateway.java
├── StripePaymentProvider.java
│
├── OrderRepository.java
├── InMemoryOrderRepository.java
│
├── OrderManager.java
│
└── Main.java
```

---

## Class Description

### Product.java

This class is used to store product details like name, price, description and stock quantity.

### Customer.java

This class stores customer information such as name, email and address.

### LineItem.java

Represents a single item inside an order.

### Order.java

Handles order creation, payment processing and status updates.

### PaymentGateway.java

Interface used for payment processing.

### StripePaymentProvider.java

Implements PaymentGateway and simulates Stripe payment authorization.

### OrderRepository.java

Interface for storing and retrieving orders.

### InMemoryOrderRepository.java

Stores all order data in memory using HashMap.

### OrderManager.java

Acts as the service layer and manages all order operations.

### Main.java

Contains the menu driven program and user interaction.

---

## OOP Concepts Used

### Encapsulation

All fields are private and accessed through methods.

### Abstraction

Interfaces like PaymentGateway and OrderRepository hide implementation details.

### Composition

Order contains multiple LineItems.

### Association

Customer is associated with Orders and LineItems are associated with Products.

### Dependency Injection

OrderManager receives repository object through constructor.

### Exception Handling

Used to handle invalid operations and prevent errors.

---

## How to Run

Compile the project:

```bash
javac *.java
```

Run the project:

```bash
java Main
```

---

## Sample Menu

```text
[1] Product Management
[2] Customer Management
[3] Order Management
[4] Payment Processing
[5] Update Order Status
[6] View Reports
[0] Exit
```

---

## What I Learned

While developing this project, I learned how OOP concepts are used in real applications. I also got better understanding of interfaces, collections, exception handling and project structure in Java.

This project helped me understand how an order management system works in an online shopping platform.

---

## Future Improvements

Some features can be added in future:

* Database integration using MySQL
* GUI application
* User login system
* Product search functionality
* Real payment gateway integration
* Email notifications

---

## Conclusion

The E-Commerce Order Management System was successfully developed and tested. All major functionalities like product management, customer registration, order creation, payment processing and order tracking are working properly.

This project gave me practical experience in Java programming and Object Oriented Programming concepts.
## Project Documentation

For more details about this project, please refer the documentation attached in this repository.

Documentation: [E-Commerce_Project_Documentation.pdf](./E-Commerce_Project_Documentation.pdf)

The document contains UML diagrams, class descriptions, screenshots, code snippets and complete project explanation.
