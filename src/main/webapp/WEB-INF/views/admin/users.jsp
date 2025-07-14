<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pahanaedu.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management - Pahana Edu</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f5f5;
            line-height: 1.6;
        }
        
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 1rem 2rem;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
            max-width: 1200px;
            margin: 0 auto;
        }
        
        .logo {
            font-size: 24px;
            font-weight: bold;
        }
        
        .user-info {
            display: flex;
            align-items: center;
            gap: 1rem;
        }
        
        .nav-menu {
            background: white;
            border-bottom: 1px solid #e1e5e9;
            padding: 0 2rem;
        }
        
        .nav-content {
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            gap: 2rem;
        }
        
        .nav-item {
            padding: 1rem 0;
            text-decoration: none;
            color: #333;
            border-bottom: 2px solid transparent;
            transition: all 0.3s;
        }
        
        .nav-item:hover, .nav-item.active {
            color: #667eea;
            border-bottom-color: #667eea;
        }
        
        .main-content {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 2rem;
        }
        
        .page-title {
            font-size: 28px;
            margin-bottom: 2rem;
            color: #333;
        }
        
        .content-section {
            background: white;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 2rem;
        }
        
        .form-group {
            margin-bottom: 1.5rem;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
            color: #333;
        }
        
        .form-group input, .form-group select {
            width: 100%;
            padding: 10px;
            border: 2px solid #e1e5e9;
            border-radius: 6px;
            font-size: 16px;
        }
        
        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 6px;
            transition: transform 0.2s;
            border: none;
            cursor: pointer;
            font-size: 16px;
        }
        
        .btn:hover {
            transform: translateY(-2px);
        }
        
        .btn-secondary {
            background: #6c757d;
        }
        
        .btn-success {
            background: #28a745;
        }
        
        .btn-danger {
            background: #dc3545;
        }
        
        .btn-small {
            padding: 5px 10px;
            font-size: 14px;
        }
        
        .error-message {
            background: #fee;
            color: #c33;
            padding: 10px;
            border-radius: 6px;
            margin-bottom: 20px;
            border: 1px solid #fcc;
        }
        
        .success-message {
            background: #efe;
            color: #3c3;
            padding: 10px;
            border-radius: 6px;
            margin-bottom: 20px;
            border: 1px solid #cfc;
        }
        
        .users-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
        }
        
        .users-table th, .users-table td {
            padding: 12px;
            border: 1px solid #e1e5e9;
            text-align: left;
        }
        
        .users-table th {
            background: #f8f9fa;
            font-weight: 500;
        }
        
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
        }
        
        .checkbox-group {
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }
        
        .checkbox-group input[type="checkbox"] {
            width: auto;
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="header-content">
            <div class="logo">Pahana Edu</div>
            <div class="user-info">
                <span>Welcome, ${sessionScope.fullName}</span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary">Logout</a>
            </div>
        </div>
    </div>
    
    <div class="nav-menu">
        <div class="nav-content">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-item">Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/users" class="nav-item active">Users</a>
            <a href="${pageContext.request.contextPath}/admin/items" class="nav-item">Items</a>
            <a href="${pageContext.request.contextPath}/admin/reports" class="nav-item">Reports</a>
            <a href="${pageContext.request.contextPath}/help" class="nav-item">Help</a>
        </div>
    </div>
    
    <div class="main-content">
        <h1 class="page-title">User Management</h1>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <% if (request.getAttribute("success") != null) { %>
            <div class="success-message">
                <%= request.getAttribute("success") %>
            </div>
        <% } %>
        
        <!-- Add/Edit User Form -->
        <div class="content-section">
            <h3><%= request.getAttribute("editUser") != null ? "Edit User" : "Add New User" %></h3>
            <form action="${pageContext.request.contextPath}/admin/users" method="post">
                <% 
                    User editUser = (User) request.getAttribute("editUser");
                    if (editUser != null) {
                %>
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="userId" value="<%= editUser.getUserId() %>">
                <% } else { %>
                    <input type="hidden" name="action" value="add">
                <% } %>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="username">Username *</label>
                        <input type="text" id="username" name="username" 
                               value="<%= editUser != null ? editUser.getUsername() : "" %>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="password">Password *</label>
                        <input type="password" id="password" name="password" 
                               value="<%= editUser != null ? editUser.getPassword() : "" %>" required>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="role">Role *</label>
                        <select id="role" name="role" required>
                            <option value="">Select Role</option>
                            <option value="ADMIN" <%= editUser != null && "ADMIN".equals(editUser.getRole()) ? "selected" : "" %>>Admin</option>
                            <option value="CASHIER" <%= editUser != null && "CASHIER".equals(editUser.getRole()) ? "selected" : "" %>>Cashier</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="fullName">Full Name *</label>
                        <input type="text" id="fullName" name="fullName" 
                               value="<%= editUser != null ? editUser.getFullName() : "" %>" required>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" 
                           value="<%= editUser != null && editUser.getEmail() != null ? editUser.getEmail() : "" %>">
                </div>
                
                <% if (editUser != null) { %>
                <div class="form-group">
                    <div class="checkbox-group">
                        <input type="checkbox" id="active" name="active" <%= editUser.isActive() ? "checked" : "" %>>
                        <label for="active">Active</label>
                    </div>
                </div>
                <% } %>
                
                <div>
                    <button type="submit" class="btn btn-success">
                        <%= editUser != null ? "Update User" : "Add User" %>
                    </button>
                    <% if (editUser != null) { %>
                        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">Cancel</a>
                    <% } %>
                </div>
            </form>
        </div>
        
        <!-- Users List -->
        <div class="content-section">
            <h3>All Users</h3>
            <table class="users-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Full Name</th>
                        <th>Role</th>
                        <th>Email</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<User> users = (List<User>) request.getAttribute("users");
                        if (users != null) {
                            for (User user : users) {
                    %>
                    <tr>
                        <td><%= user.getUserId() %></td>
                        <td><%= user.getUsername() %></td>
                        <td><%= user.getFullName() %></td>
                        <td><%= user.getRole() %></td>
                        <td><%= user.getEmail() != null ? user.getEmail() : "-" %></td>
                        <td><%= user.isActive() ? "Active" : "Inactive" %></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/users?action=edit&id=<%= user.getUserId() %>" 
                               class="btn btn-small">Edit</a>
                            <form style="display: inline;" method="post" 
                                  onsubmit="return confirm('Are you sure you want to delete this user?')">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="userId" value="<%= user.getUserId() %>">
                                <button type="submit" class="btn btn-danger btn-small">Delete</button>
                            </form>
                        </td>
                    </tr>
                    <% 
                            }
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>