<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pahanaedu.model.Item" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Item Management - Pahana Edu</title>
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
        
        .form-group input, .form-group select, .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 2px solid #e1e5e9;
            border-radius: 6px;
            font-size: 16px;
        }
        
        .form-group input:focus, .form-group select:focus, .form-group textarea:focus {
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
        
        .items-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
        }
        
        .items-table th, .items-table td {
            padding: 12px;
            border: 1px solid #e1e5e9;
            text-align: left;
        }
        
        .items-table th {
            background: #f8f9fa;
            font-weight: 500;
        }
        
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
        }
        
        .form-row-3 {
            display: grid;
            grid-template-columns: 1fr 1fr 1fr;
            gap: 1rem;
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
            <a href="${pageContext.request.contextPath}/admin/users" class="nav-item">Users</a>
            <a href="${pageContext.request.contextPath}/admin/items" class="nav-item active">Items</a>
            <a href="${pageContext.request.contextPath}/admin/reports" class="nav-item">Reports</a>
            <a href="${pageContext.request.contextPath}/help" class="nav-item">Help</a>
        </div>
    </div>
    
    <div class="main-content">
        <h1 class="page-title">Item Management</h1>
        
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
        
        <!-- Add/Edit Item Form -->
        <div class="content-section">
            <h3><%= request.getAttribute("editItem") != null ? "Edit Item" : "Add New Item" %></h3>
            <form action="${pageContext.request.contextPath}/admin/items" method="post">
                <% 
                    Item editItem = (Item) request.getAttribute("editItem");
                    if (editItem != null) {
                %>
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="itemId" value="<%= editItem.getItemId() %>">
                <% } else { %>
                    <input type="hidden" name="action" value="add">
                <% } %>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="itemCode">Item Code *</label>
                        <input type="text" id="itemCode" name="itemCode" 
                               value="<%= editItem != null ? editItem.getItemCode() : "" %>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="title">Title *</label>
                        <input type="text" id="title" name="title" 
                               value="<%= editItem != null ? editItem.getTitle() : "" %>" required>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="author">Author *</label>
                        <input type="text" id="author" name="author" 
                               value="<%= editItem != null ? editItem.getAuthor() : "" %>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="category">Category</label>
                        <input type="text" id="category" name="category" 
                               value="<%= editItem != null && editItem.getCategory() != null ? editItem.getCategory() : "" %>">
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="price">Price (Rs.) *</label>
                        <input type="number" step="0.01" id="price" name="price" 
                               value="<%= editItem != null ? editItem.getPrice() : "" %>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="quantity">Quantity *</label>
                        <input type="number" id="quantity" name="quantity" 
                               value="<%= editItem != null ? editItem.getQuantity() : "" %>" required>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" rows="3"><%= editItem != null && editItem.getDescription() != null ? editItem.getDescription() : "" %></textarea>
                </div>
                
                <div>
                    <button type="submit" class="btn btn-success">
                        <%= editItem != null ? "Update Item" : "Add Item" %>
                    </button>
                    <% if (editItem != null) { %>
                        <a href="${pageContext.request.contextPath}/admin/items" class="btn btn-secondary">Cancel</a>
                    <% } %>
                </div>
            </form>
        </div>
        
        <!-- Items List -->
        <div class="content-section">
            <h3>All Items</h3>
            <table class="items-table">
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<Item> items = (List<Item>) request.getAttribute("items");
                        if (items != null) {
                            for (Item item : items) {
                    %>
                    <tr>
                        <td><%= item.getItemCode() %></td>
                        <td><%= item.getTitle() %></td>
                        <td><%= item.getAuthor() %></td>
                        <td><%= item.getCategory() != null ? item.getCategory() : "-" %></td>
                        <td>Rs. <%= item.getPrice() %></td>
                        <td><%= item.getQuantity() %></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/items?action=edit&id=<%= item.getItemId() %>" 
                               class="btn btn-small">Edit</a>
                            <form style="display: inline;" method="post" 
                                  onsubmit="return confirm('Are you sure you want to delete this item?')">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
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