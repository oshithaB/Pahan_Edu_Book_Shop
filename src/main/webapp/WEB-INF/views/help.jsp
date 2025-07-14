<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Help - Pahana Edu</title>
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
        
        .main-content {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 2rem;
        }
        
        .help-container {
            background: white;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .page-title {
            font-size: 28px;
            margin-bottom: 2rem;
            color: #333;
        }
        
        .help-section {
            margin-bottom: 2rem;
        }
        
        .help-section h2 {
            color: #667eea;
            margin-bottom: 1rem;
        }
        
        .help-section h3 {
            color: #333;
            margin-bottom: 0.5rem;
            margin-top: 1rem;
        }
        
        .help-section p {
            margin-bottom: 1rem;
            color: #666;
        }
        
        .help-section ul {
            margin-left: 2rem;
            margin-bottom: 1rem;
        }
        
        .help-section li {
            margin-bottom: 0.5rem;
            color: #666;
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
        }
        
        .btn:hover {
            transform: translateY(-2px);
        }
        
        .btn-secondary {
            background: #6c757d;
        }
        
        .feature-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 1.5rem;
            margin-top: 2rem;
        }
        
        .feature-card {
            background: #f8f9fa;
            padding: 1.5rem;
            border-radius: 8px;
            border-left: 4px solid #667eea;
        }
        
        .feature-card h4 {
            color: #333;
            margin-bottom: 0.5rem;
        }
        
        .feature-card p {
            color: #666;
            font-size: 14px;
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
    
    <div class="main-content">
        <div class="help-container">
            <h1 class="page-title">Help & System Guide</h1>
            
            <div class="help-section">
                <h2>Welcome to Pahana Edu Bookshop Management System</h2>
                <p>This system helps you manage bookshop operations efficiently. Below you'll find information about how to use different features based on your role.</p>
            </div>
            
            <% if ("ADMIN".equals(session.getAttribute("role"))) { %>
            <div class="help-section">
                <h2>Admin Features</h2>
                <p>As an administrator, you have access to all system features:</p>
                
                <div class="feature-grid">
                    <div class="feature-card">
                        <h4>User Management</h4>
                        <p>Add, edit, and delete users (admins and cashiers). You can also activate/deactivate user accounts.</p>
                    </div>
                    
                    <div class="feature-card">
                        <h4>Item Management</h4>
                        <p>Manage book inventory including adding new books, updating information, prices, and stock quantities.</p>
                    </div>
                    
                    <div class="feature-card">
                        <h4>Sales Reports</h4>
                        <p>View comprehensive sales reports including total revenue, books sold, and all bill records.</p>
                    </div>
                    
                    <div class="feature-card">
                        <h4>Dashboard Overview</h4>
                        <p>Get a quick overview of key metrics including total users, customers, items, and sales summary.</p>
                    </div>
                </div>
                
                <h3>How to Add a New User:</h3>
                <ul>
                    <li>Go to "Users" from the navigation menu</li>
                    <li>Click "Add User" button</li>
                    <li>Fill in all required fields (username, password, role, full name)</li>
                    <li>Select role as either "ADMIN" or "CASHIER"</li>
                    <li>Click "Save" to create the user</li>
                </ul>
                
                <h3>How to Manage Items:</h3>
                <ul>
                    <li>Go to "Items" from the navigation menu</li>
                    <li>Click "Add Item" to add new books</li>
                    <li>Fill in item details: code, title, author, category, price, and quantity</li>
                    <li>Use "Edit" to modify existing items</li>
                    <li>Use "Delete" to remove items (be careful as this is permanent)</li>
                </ul>
            </div>
            <% } %>
            
            <% if ("CASHIER".equals(session.getAttribute("role"))) { %>
            <div class="help-section">
                <h2>Cashier Features</h2>
                <p>As a cashier, you can manage customers and create bills:</p>
                
                <div class="feature-grid">
                    <div class="feature-card">
                        <h4>Customer Management</h4>
                        <p>Register new customers and manage existing customer information including contact details.</p>
                    </div>
                    
                    <div class="feature-card">
                        <h4>Bill Creation</h4>
                        <p>Create bills for customers with multiple items. The system will automatically calculate totals and print bills.</p>
                    </div>
                    
                    <div class="feature-card">
                        <h4>Customer Search</h4>
                        <p>Quick search functionality to find customers by name or account number when creating bills.</p>
                    </div>
                    
                    <div class="feature-card">
                        <h4>Item Search</h4>
                        <p>Easy item search when adding items to bills. Search by item code, title, or author.</p>
                    </div>
                </div>
                
                <h3>How to Register a New Customer:</h3>
                <ul>
                    <li>Go to "Customers" from the navigation menu</li>
                    <li>Click "Add Customer" button</li>
                    <li>Fill in required fields: account number, name, address, phone</li>
                    <li>Email is optional but recommended</li>
                    <li>Click "Save" to register the customer</li>
                </ul>
                
                <h3>How to Create a Bill:</h3>
                <ul>
                    <li>Go to "Create Bill" from the navigation menu</li>
                    <li>Start typing customer name or account number to search</li>
                    <li>Select the customer from suggestions</li>
                    <li>Add items by selecting from dropdown</li>
                    <li>Enter quantity for each item</li>
                    <li>Click "Add Item" to add more items if needed</li>
                    <li>Review the total amount</li>
                    <li>Click "Create Bill" to generate and print the bill</li>
                </ul>
            </div>
            <% } %>
            
            <div class="help-section">
                <h2>General System Features</h2>
                
                <h3>Navigation:</h3>
                <ul>
                    <li>Use the main navigation menu to access different sections</li>
                    <li>The active section is highlighted in blue</li>
                    <li>Click on the logo to return to the dashboard</li>
                </ul>
                
                <h3>Search Functionality:</h3>
                <ul>
                    <li>Most search fields provide auto-suggestions as you type</li>
                    <li>Search is case-insensitive</li>
                    <li>You can search by multiple criteria (name, code, etc.)</li>
                </ul>
                
                <h3>Security:</h3>
                <ul>
                    <li>Always logout when done using the system</li>
                    <li>Sessions expire after 30 minutes of inactivity</li>
                    <li>Only access features appropriate to your role</li>
                </ul>
            </div>
            
            <div class="help-section">
                <h2>Troubleshooting</h2>
                
                <h3>Common Issues:</h3>
                <ul>
                    <li><strong>Can't login:</strong> Check username and password, ensure your account is active</li>
                    <li><strong>Search not working:</strong> Try different search terms or check spelling</li>
                    <li><strong>Bill not printing:</strong> Check your browser's print settings and printer connection</li>
                    <li><strong>Data not saving:</strong> Ensure all required fields are filled correctly</li>
                </ul>
                
                <h3>Tips for Best Performance:</h3>
                <ul>
                    <li>Use Chrome, Firefox, or Edge browsers for best compatibility</li>
                    <li>Keep your browser updated</li>
                    <li>Clear browser cache if experiencing issues</li>
                    <li>Ensure stable internet connection</li>
                </ul>
            </div>
            
            <div class="help-section">
                <h2>Contact Support</h2>
                <p>If you need additional help or encounter technical issues:</p>
                <ul>
                    <li>Contact your system administrator</li>
                    <li>Report any bugs or issues immediately</li>
                    <li>Keep track of error messages for troubleshooting</li>
                </ul>
            </div>
            
            <div style="text-align: center; margin-top: 2rem;">
                <% if ("ADMIN".equals(session.getAttribute("role"))) { %>
                    <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn">Back to Dashboard</a>
                <% } else { %>
                    <a href="${pageContext.request.contextPath}/cashier/dashboard" class="btn">Back to Dashboard</a>
                <% } %>
            </div>
        </div>
    </div>
</body>
</html>