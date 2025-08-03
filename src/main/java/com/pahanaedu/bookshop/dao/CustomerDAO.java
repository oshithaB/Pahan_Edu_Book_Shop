package com.pahanaedu.bookshop.dao;

import com.pahanaedu.bookshop.model.Customer;
import com.pahanaedu.bookshop.factory.BookshopProductFactory;
import com.pahanaedu.bookshop.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CustomerDAO {
    private final DatabaseConnection dbConnection;
    private final BookshopProductFactory productFactory;
    
    public CustomerDAO() {
        this.dbConnection = DatabaseConnection.getInstance();
        this.productFactory = BookshopProductFactory.getInstance();
    }
    
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE is_active = true ORDER BY name";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }
        }
        return customers;
    }
    
    public Customer getCustomerById(int id) throws SQLException {
        String sql = "SELECT * FROM customers WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCustomer(rs);
            }
        }
        return null;
    }
    
    public boolean addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (account_number, name, address, telephone, email) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customer.getAccountNumber());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getTelephone());
            stmt.setString(5, customer.getEmail());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET account_number = ?, name = ?, address = ?, telephone = ?, email = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customer.getAccountNumber());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getTelephone());
            stmt.setString(5, customer.getEmail());
            stmt.setInt(6, customer.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteCustomer(int id) throws SQLException {
        String sql = "UPDATE customers SET is_active = false WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<Customer> searchCustomers(String searchTerm) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        
        // Use HashMap for search field mapping
        Map<String, String> searchFields = new HashMap<>();
        searchFields.put("name", "Customer Name");
        searchFields.put("account_number", "Account Number");
        searchFields.put("email", "Email Address");
        searchFields.put("telephone", "Phone Number");
        
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM customers WHERE is_active = true AND (");
        List<String> conditions = new ArrayList<>();
        
        for (String field : searchFields.keySet()) {
            conditions.add(field + " LIKE ?");
        }
        
        sqlBuilder.append(String.join(" OR ", conditions));
        sqlBuilder.append(") ORDER BY name");
        String sql = sqlBuilder.toString();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + searchTerm + "%";
            
            int paramIndex = 1;
            for (String field : searchFields.keySet()) {
                stmt.setString(paramIndex++, searchPattern);
            }
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }
        }
        return customers;
    }
    
    public int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM customers WHERE is_active = true";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    public String generateAccountNumber() throws SQLException {
        String sql = "SELECT MAX(CAST(SUBSTRING(account_number, 5) AS UNSIGNED)) FROM customers WHERE account_number LIKE 'CUST%'";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            int nextNumber = 1;
            if (rs.next()) {
                nextNumber = rs.getInt(1) + 1;
            }
            
            return String.format("CUST%03d", nextNumber);
        }
    }
    
    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setAccountNumber(rs.getString("account_number"));
        customer.setName(rs.getString("name"));
        customer.setAddress(rs.getString("address"));
        customer.setTelephone(rs.getString("telephone"));
        customer.setEmail(rs.getString("email"));
        customer.setActive(rs.getBoolean("is_active"));
        customer.setCreatedAt(rs.getTimestamp("created_at"));
        customer.setUpdatedAt(rs.getTimestamp("updated_at"));
        return customer;
    }
}