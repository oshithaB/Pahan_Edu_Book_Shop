<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cashier Dashboard - Pahana Edu</title>
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
        
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }
        
        .stat-card {
            background: white;
            padding: 1.5rem;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        
        .stat-number {
            font-size: 36px;
            font-weight: bold;
            color: #667eea;
            margin-bottom: 0.5rem;
        }
        
        .stat-label {
            color: #666;
            font-size: 14px;
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
        
        .quick-actions {
            background: white;
            padding: 1.5rem;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .quick-actions h3 {
            margin-bottom: 1rem;
            color: #333;
        }
        
        .actions-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
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
            <a href="${pageContext.request.contextPath}/cashier/dashboard" class="nav-item active">Dashboard</a>
            <a href="${pageContext.request.contextPath}/cashier/customers" class="nav-item">Customers</a>
            <a href="${pageContext.request.contextPath}/cashier/bills" class="nav-item">Create Bill</a>
            <a href="${pageContext.request.contextPath}/cashier/bill-list" class="nav-item">View Bills</a>
            <a href="${pageContext.request.contextPath}/help" class="nav-item">Help</a>
        </div>
    </div>
    
    <div class="main-content">
        <h1 class="page-title">Cashier Dashboard</h1>
        
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-number">${totalCustomers}</div>
                <div class="stat-label">Total Customers</div>
            </div>
            
            <div class="stat-card">
                <div class="stat-number">${totalItems}</div>
                <div class="stat-label">Available Items</div>
            </div>
        </div>
        
        <div class="quick-actions">
            <h3>Quick Actions</h3>
            <div class="actions-grid">
                <a href="${pageContext.request.contextPath}/cashier/customers" class="btn">Manage Customers</a>
                <a href="${pageContext.request.contextPath}/cashier/bills" class="btn">Create New Bill</a>
                <a href="${pageContext.request.contextPath}/cashier/bill-list" class="btn">View All Bills</a>
                <a href="${pageContext.request.contextPath}/help" class="btn">Help</a>
            </div>
        </div>
    </div>
</body>
</html>