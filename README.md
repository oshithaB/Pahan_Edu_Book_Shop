# Pahana Edu Bookshop Management System

A complete Java web application for managing bookshop operations using JSP, Servlets, and MySQL.

## Features

### Admin Features
- User management (add, edit, delete users)
- Item management (books inventory)
- Sales reports and analytics
- View all bill records

### Cashier Features
- Customer registration and management
- Bill creation with auto-suggestions
- Print bills functionality
- Customer search and management

## Technology Stack
- **Backend**: Java Servlets, JSP
- **Database**: MySQL
- **Build Tool**: Maven
- **Server**: Apache Tomcat

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/pahanaedu/
│   │       ├── dao/           # Data Access Objects
│   │       ├── model/         # Entity classes
│   │       ├── servlet/       # Servlet controllers
│   │       └── util/          # Utility classes
│   └── webapp/
│       └── WEB-INF/
│           ├── views/         # JSP pages
│           └── web.xml        # Web configuration
```

## Setup Instructions

### Prerequisites
- Java 11 or higher
- MySQL 8.0 or higher
- Apache Tomcat 9.0 or higher
- Maven 3.6 or higher

### Database Setup
1. Create MySQL database named `pahana_edu`
2. Run the SQL script from `database/schema.sql`
3. Update database credentials in `DBConnection.java`

### Build and Deploy
1. Clone the repository
2. Navigate to project directory
3. Build the project:
   ```bash
   mvn clean compile
   ```
4. Create WAR file:
   ```bash
   mvn clean package
   ```
5. Deploy the generated WAR file to Tomcat

### Running the Application
1. Start MySQL server
2. Start Tomcat server
3. Deploy the WAR file to Tomcat webapps directory
4. Access the application at: `http://localhost:8080/pahana-edu-bookshop`

## Default Login Credentials
- **Admin**: username: `admin`, password: `admin123`
- **Cashier**: username: `cashier`, password: `cash123`

## Database Schema
- `users` - System users (admin, cashier)
- `customers` - Bookshop customers
- `items` - Book inventory
- `bills` - Sales transactions
- `bill_items` - Individual items in bills

## Maven Commands
- `mvn clean compile` - Compile the project
- `mvn clean package` - Create WAR file
- `mvn tomcat7:run` - Run with embedded Tomcat (development)
- `mvn clean install` - Install to local repository

## Features Implemented
- ✅ User authentication and authorization
- ✅ Role-based access control (Admin/Cashier)
- ✅ Customer management
- ✅ Inventory management
- ✅ Bill generation with print functionality
- ✅ Sales reporting
- ✅ Auto-suggestions for customers and items
- ✅ Responsive web design
- ✅ Error handling and validation

## Architecture
- **MVC Pattern**: Servlets as Controllers, JSP as Views, DAO as Model
- **SOLID Principles**: Applied throughout the codebase
- **Clean Code**: Meaningful names, proper commenting, modular design
- **Security**: Prepared statements, input validation, session management