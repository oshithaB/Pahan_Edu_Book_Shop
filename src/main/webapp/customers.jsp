<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pahanaedu.bookshop.business.user.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customers - Pahana Edu Book Shop</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header class="header">
        <div class="header-content">
            <div class="logo">
                <h1>Pahana Edu Book Shop</h1>
            </div>
            <div class="user-info">
                <span>Welcome, <%= user.getFullName() %> (<%= user.getRole().toUpperCase() %>)</span>
                <a href="javascript:void(0)" onclick="showHelp()" class="btn btn-secondary btn-sm">Help</a>
                <a href="logout" class="btn btn-danger btn-sm">Logout</a>
            </div>
        </div>
    </header>

    <nav class="nav-menu">
        <ul>
            <li><a href="dashboard">Dashboard</a></li>
            <li class="admin-only" style="display: none;"><a href="users?action=list">Users</a></li>
            <li><a href="customers?action=list" class="active">Customers</a></li>
            <li><a href="books?action=list">Books</a></li>
            <li><a href="bills?action=list">Bills</a></li>
            <li class="cashier-only" style="display: none;"><a href="bills?action=create">Create Bill</a></li>
        </ul>
    </nav>

    <main class="main-content">
        <div class="card fade-in">
            <div class="card-header">
                Customer Management
            </div>
            <div class="card-body">
                <!-- Alerts -->
                <div id="alertContainer"></div>

                <!-- Search and Add Section -->
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                    <div class="search-box">
                        <div style="display: flex;">
                            <input type="text" id="searchInput" placeholder="Search customers..." class="form-control">
                            <button type="button" onclick="searchCustomers()" class="btn btn-primary">Search</button>
                        </div>
                    </div>
                    <!-- Show Add New Customer for both admin and cashier -->
                    <a href="customers?action=add" class="btn btn-success admin-only cashier-only" style="display: none;">Add New Customer</a>
                </div>

                <!-- Loading indicator -->
                <div id="loadingIndicator" style="display: none; text-align: center; padding: 20px;">
                    <div>Loading customers...</div>
                </div>

                <!-- Customers Table -->
                <div id="customersTableContainer">
                    <!-- Table will be populated by JavaScript -->
                </div>
            </div>
        </div>
    </main>

    <!-- Help Modal -->
    <div id="helpModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3>Customer Management Help</h3>
                <span class="close" onclick="closeHelp()">&times;</span>
            </div>
            <div class="modal-body">
                <h4>Customer Management Features:</h4>
                <ul>
                    <li><strong>Add Customer:</strong> Click "Add New Customer" to create a new customer account</li>
                    <li><strong>Search:</strong> Use the search box to find customers by name, account number, email, or phone</li>
                    <li><strong>Edit:</strong> Click "Edit" to modify customer information</li>
                    <!-- Always show Delete option in help for admin -->
                    <li><strong>Delete:</strong> Click "Delete" to remove a customer (Admin only)</li>
                </ul>
                
                <h4>Customer Information:</h4>
                <ul>
                    <li>Account numbers are automatically generated</li>
                    <li>All fields are required for complete customer records</li>
                    <li>Email addresses are used for notifications</li>
                    <li>Phone numbers should include area codes</li>
                </ul>
            </div>
        </div>
    </div>

    <script src="js/customers.js"></script>
    <script>
        // Help modal functions
        function showHelp() {
            document.getElementById('helpModal').style.display = 'block';
        }
        
        function closeHelp() {
            document.getElementById('helpModal').style.display = 'none';
        }
        
        window.onclick = function(event) {
            const modal = document.getElementById('helpModal');
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        }
    </script>
</body>
</html>