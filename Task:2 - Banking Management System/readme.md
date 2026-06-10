# Banking Management System

## Overview

The Banking Management System is a console-based Java application developed to simplify and automate common banking operations. The project uses Java for application development, JDBC for database connectivity, and MySQL for persistent data storage.

The system enables users to manage customer accounts, perform transactions, and maintain transaction records through an easy-to-use menu-driven interface. By integrating Java with a relational database, the project demonstrates how real-world banking operations can be managed efficiently through software.

---

## Problem Statement

Managing customer accounts and banking transactions manually can be time-consuming and prone to errors. Banks require a reliable system to maintain customer information, account details, balances, and transaction history.

This project aims to provide a simple banking solution that automates these operations while ensuring data consistency and accuracy.

---

## Project Objective

The objective of this project is to design and develop a Banking Management System that allows users to:

* Create and manage customer accounts
* Store customer information securely
* Perform deposit and withdrawal operations
* Transfer funds between accounts
* Track transaction history
* Maintain account balances automatically

---

## Technologies Used

### Java

Java is used as the primary programming language for implementing business logic and application functionality.

### JDBC (Java Database Connectivity)

JDBC is used to establish communication between the Java application and the MySQL database.

### MySQL

MySQL is used as the backend database for storing customer information, account details, and transaction records.

### Object-Oriented Programming

The project follows Object-Oriented Programming principles such as encapsulation, abstraction, and modular design.

---

## System Features

### Customer Management

The system allows users to register new customers by providing basic details such as name and address. A unique Client ID is generated automatically for each customer.

### Account Management

Users can create bank accounts associated with registered customers. Each account is assigned a unique account number and maintains its own balance information.

### Deposit Operation

Customers can deposit money into their accounts. The system updates the account balance automatically and records the transaction in the database.

### Withdrawal Operation

Customers can withdraw funds from their accounts. The system validates the available balance before processing the withdrawal.

### Fund Transfer

The application supports transferring money between two accounts. Account balances are updated immediately, and transaction details are recorded for future reference.

### Transaction History

All banking activities are stored in the transaction table. Users can view transaction records to monitor account activity.

---

## Database Design

The project uses a MySQL database named **banking_system**.

### Clients Table

Stores customer information including Client ID, Name, and Address.

### Accounts Table

Stores account details such as Account Number, Balance, Client ID, and Account Creation Date.

### Savings Table

Maintains savings account information including interest rates associated with accounts.

### Transactions Table

Records all deposits, withdrawals, and fund transfer activities performed within the system.

The tables are connected using primary key and foreign key relationships to ensure data integrity and consistency.

---

## Working of the System

1. The application starts by displaying a menu of available banking operations.
2. Users can create customers and accounts.
3. Customer and account information is stored in the MySQL database.
4. Banking operations such as deposits, withdrawals, and transfers are performed through menu selections.
5. JDBC is used to communicate with the database and update records.
6. Transaction details are stored automatically for future reference.
7. Users can retrieve customer information, account details, and transaction history whenever required.

---

## Key Concepts Implemented

* Classes and Objects
* Encapsulation
* JDBC Connectivity
* SQL Queries
* Database Relationships
* Exception Handling
* Menu-Driven Programming
* Random ID Generation

---

## Results

The Banking Management System was successfully implemented and tested. All major banking operations including account creation, customer management, deposits, withdrawals, and fund transfers were executed correctly. The application maintained accurate account balances and stored transaction records efficiently in the MySQL database.

The integration between Java and MySQL through JDBC was completed successfully, demonstrating the practical use of database-driven application development.

---

## Learning Outcomes

Through this project, I gained practical experience in:

* Java application development
* Database design and management
* JDBC connectivity
* Object-Oriented Programming concepts
* SQL query execution
* Real-world transaction processing systems

---

## Conclusion

The Banking Management System successfully demonstrates how software can be used to automate banking operations. By combining Java, JDBC, and MySQL, the project provides an efficient and reliable solution for managing customer accounts and transactions.

This project helped strengthen my understanding of Java programming, database connectivity, and software design principles while providing hands-on experience in developing a real-world application.

---

## Author

**Abisheik R**

GitHub Repository:
https://github.com/Devabisheik/BANKING_SYSTEM
