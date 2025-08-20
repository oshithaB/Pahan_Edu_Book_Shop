<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pahanaedu.bookshop.business.user.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    boolean isAdmin = "ADMIN".equalsIgnoreCase(user.getRole());
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Books - Pahana Edu Book Shop</title>
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
            <li><a href="books?action=list" class="active">Books</a></li>
            <li><a href="bills?action=list">Bills</a></li>
            <li class="cashier-only" style="display: none;"><a href="bills?action=create">Create Bill</a></li>
        </ul>
    </nav>

    <main class="main-content">
        <div class="card fade-in">
            <div class="card-header">
                Book Inventory Management
            </div>
            <div class="card-body">
                <!-- Alerts -->
                <div id="alertContainer"></div>

                <!-- Search and Add Section -->
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                    <div class="search-box">
                        <div style="display: flex;">
                            <input type="text" id="searchInput" placeholder="Search books..." class="form-control">
                            <button type="button" onclick="searchBooks()" class="btn btn-primary">Search</button>
                        </div>
                    </div>
                    <a href="books?action=add" class="btn btn-success admin-only" style="display: none;">Add New Book</a>
                </div>

                <!-- Loading indicator -->
                <div id="loadingIndicator" style="display: none; text-align: center; padding: 20px;">
                    <div>Loading books...</div>
                </div>

                <!-- Books Table -->
                <div id="booksTableContainer">
                    <!-- Table will be populated by JavaScript -->
                    <!-- Admins will see Edit/Delete buttons via JS -->
                </div>
            </div>
        </div>
    </main>

    <!-- Help Modal -->
    <div id="helpModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3>Book Management Help</h3>
                <span class="close" onclick="closeHelp()">&times;</span>
            </div>
            <div class="modal-body">
                <h4>Book Management Features:</h4>
                <ul>
                    <li><strong>Add Book:</strong> Click "Add New Book" to add a new book to inventory</li>
                    <li><strong>Search:</strong> Use the search box to find books by title, author, ISBN, or category</li>
                    <li><strong>Edit:</strong> Click "Edit" to modify book information</li>
                    <li><strong>Delete:</strong> Click "Delete" to remove a book (Admin only)</li>
                </ul>
                
                <h4>Book Information:</h4>
                <ul>
                    <li>ISBN should be unique for each book</li>
                    <li>Price should be in decimal format (e.g., 29.99)</li>
                    <li>Quantity indicates available stock</li>
                    <li>Status shows if book is available or out of stock</li>
                </ul>
            </div>
        </div>
    </div>

    <script src="js/books.js"></script>
    <script>
        // Pass admin status to JS
        var isAdmin = <%= isAdmin ? "true" : "false" %>;

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
    <!-- Add this note for books.js -->
    <!--
        In books.js, when rendering the books table, use the isAdmin variable:
        Example:
        if (isAdmin) {
            // Render Edit/Delete buttons
        }
    -->
</body>
</html>