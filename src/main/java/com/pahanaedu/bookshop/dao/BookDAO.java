package com.pahanaedu.bookshop.dao;

import com.pahanaedu.bookshop.model.Book;
import com.pahanaedu.bookshop.factory.BookshopProductFactory;
import com.pahanaedu.bookshop.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class BookDAO {
    private final DatabaseConnection dbConnection;
    private final BookshopProductFactory productFactory;
    
    public BookDAO() {
        this.dbConnection = DatabaseConnection.getInstance();
        this.productFactory = BookshopProductFactory.getInstance();
    }
    
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE is_active = true ORDER BY title";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        }
        return books;
    }
    
    public Book getBookById(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToBook(rs);
            }
        }
        return null;
    }
    
    public boolean addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, isbn, category, price, quantity, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getCategory());
            stmt.setBigDecimal(5, book.getPrice());
            stmt.setInt(6, book.getQuantity());
            stmt.setString(7, book.getDescription());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateBook(Book book) throws SQLException {
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, category = ?, price = ?, quantity = ?, description = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getCategory());
            stmt.setBigDecimal(5, book.getPrice());
            stmt.setInt(6, book.getQuantity());
            stmt.setString(7, book.getDescription());
            stmt.setInt(8, book.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteBook(int id) throws SQLException {
        String sql = "UPDATE books SET is_active = false WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<Book> searchBooks(String searchTerm) throws SQLException {
        List<Book> books = new ArrayList<>();
        
        // Use HashMap for search field mapping
        Map<String, String> searchFields = new HashMap<>();
        searchFields.put("title", "Book Title");
        searchFields.put("author", "Author Name");
        searchFields.put("isbn", "ISBN Number");
        searchFields.put("category", "Category");
        
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM books WHERE is_active = true AND (");
        List<String> conditions = new ArrayList<>();
        
        for (String field : searchFields.keySet()) {
            conditions.add(field + " LIKE ?");
        }
        
        sqlBuilder.append(String.join(" OR ", conditions));
        sqlBuilder.append(") ORDER BY title");
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
                books.add(mapResultSetToBook(rs));
            }
        }
        return books;
    }
    
    public int getBookCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM books WHERE is_active = true";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setIsbn(rs.getString("isbn"));
        book.setCategory(rs.getString("category"));
        book.setPrice(rs.getBigDecimal("price"));
        book.setQuantity(rs.getInt("quantity"));
        book.setDescription(rs.getString("description"));
        book.setActive(rs.getBoolean("is_active"));
        book.setCreatedAt(rs.getTimestamp("created_at"));
        book.setUpdatedAt(rs.getTimestamp("updated_at"));
        return book;
    }
}