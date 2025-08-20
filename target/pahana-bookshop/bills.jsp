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
    <title>Bills - Pahana Edu Book Shop</title>
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
            <li><a href="customers?action=list">Customers</a></li>
            <li><a href="books?action=list">Books</a></li>
            <li><a href="bills?action=list" class="active">Bills</a></li>
            <li class="cashier-only" style="display: none;"><a href="bills?action=create">Create Bill</a></li>
        </ul>
    </nav>

    <main class="main-content">
        <div class="card fade-in">
            <div class="card-header">
                Bill Management
            </div>
            <div class="card-body">
                <!-- Alerts -->
                <div id="alertContainer"></div>

                <!-- Search and Create Section -->
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                    <div class="search-box">
                        <div style="display: flex;">
                            <input type="text" id="searchInput" placeholder="Search bills..." class="form-control">
                            <button type="button" onclick="searchBills()" class="btn btn-primary">Search</button>
                        </div>
                    </div>
                    <div style="display: flex; gap: 10px;">
                        <a href="bills?action=create" class="btn btn-success cashier-only" style="display: none;">Create New Bill</a>
                        <button onclick="printBillReport()" class="btn btn-info admin-only" style="display: none;">📊 Generate Report</button>
                    </div>
                </div>

                <!-- Loading indicator -->
                <div id="loadingIndicator" style="display: none; text-align: center; padding: 20px;">
                    <div>Loading bills...</div>
                </div>

                <!-- Bills Table -->
                <div id="billsTableContainer">
                    <!-- Table will be populated by JavaScript -->
                </div>
            </div>
        </div>
    </main>

    <!-- Help Modal -->
    <div id="helpModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3>Bill Management Help</h3>
                <span class="close" onclick="closeHelp()">&times;</span>
            </div>
            <div class="modal-body">
                <h4>Bill Management Features:</h4>
                <ul>
                    <li><strong>Create Bill:</strong> Click "Create New Bill" to generate a new invoice</li>
                    <li><strong>Search:</strong> Use the search box to find bills by bill number, customer name, or date</li>
                    <li><strong>View:</strong> Click "View" to see bill details and print</li>
                    <li><strong>Status:</strong> Bills show their current status (Paid, Pending, etc.)</li>
                </ul>
                
                <h4>Bill Information:</h4>
                <ul>
                    <li>Bill numbers are automatically generated</li>
                    <li>Each bill is linked to a customer and cashier</li>
                    <li>Total amount includes all items and taxes</li>
                    <li>Bills can be printed or viewed in detail</li>
                </ul>
            </div>
        </div>
    </div>

    <script src="js/bills.js"></script>
    <script src="js/report.js"></script>
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