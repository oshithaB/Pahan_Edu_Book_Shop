package com.pahanaedu.dao;

import com.pahanaedu.model.Item;
import com.pahanaedu.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Item operations.
 * Handles all database operations related to items (books).
 */
public class ItemDAO {
    
    /**
     * Retrieves all items from the database.
     * @return List of all items
     */
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items ORDER BY title";
        
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                items.add(mapResultSetToItem(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    /**
     * Searches items by title, author, or item code.
     * @param searchTerm Search term
     * @return List of matching items
     */
    public List<Item> searchItems(String searchTerm) {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items WHERE title LIKE ? OR author LIKE ? OR item_code LIKE ? ORDER BY title";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            String searchPattern = "%" + searchTerm + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);
            
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                items.add(mapResultSetToItem(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    /**
     * Adds a new item to the database.
     * @param item Item object to add
     * @return true if successful, false otherwise
     */
    public boolean addItem(Item item) {
        String query = "INSERT INTO items (item_code, title, author, category, price, quantity, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, item.getItemCode());
            statement.setString(2, item.getTitle());
            statement.setString(3, item.getAuthor());
            statement.setString(4, item.getCategory());
            statement.setBigDecimal(5, item.getPrice());
            statement.setInt(6, item.getQuantity());
            statement.setString(7, item.getDescription());
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Updates an existing item in the database.
     * @param item Item object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateItem(Item item) {
        String query = "UPDATE items SET item_code = ?, title = ?, author = ?, category = ?, price = ?, quantity = ?, description = ? WHERE item_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, item.getItemCode());
            statement.setString(2, item.getTitle());
            statement.setString(3, item.getAuthor());
            statement.setString(4, item.getCategory());
            statement.setBigDecimal(5, item.getPrice());
            statement.setInt(6, item.getQuantity());
            statement.setString(7, item.getDescription());
            statement.setInt(8, item.getItemId());
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deletes an item from the database.
     * @param itemId Item ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteItem(int itemId) {
        String query = "DELETE FROM items WHERE item_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, itemId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retrieves an item by ID.
     * @param itemId Item ID
     * @return Item object if found, null otherwise
     */
    public Item getItemById(int itemId) {
        String query = "SELECT * FROM items WHERE item_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, itemId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToItem(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Updates item quantity after a sale.
     * @param itemId Item ID
     * @param quantitySold Quantity sold
     * @return true if successful, false otherwise
     */
    public boolean updateItemQuantity(int itemId, int quantitySold) {
        String query = "UPDATE items SET quantity = quantity - ? WHERE item_id = ? AND quantity >= ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, quantitySold);
            statement.setInt(2, itemId);
            statement.setInt(3, quantitySold);
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Maps ResultSet to Item object.
     * @param resultSet ResultSet from database query
     * @return Item object
     * @throws SQLException if SQL error occurs
     */
    private Item mapResultSetToItem(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        item.setItemId(resultSet.getInt("item_id"));
        item.setItemCode(resultSet.getString("item_code"));
        item.setTitle(resultSet.getString("title"));
        item.setAuthor(resultSet.getString("author"));
        item.setCategory(resultSet.getString("category"));
        item.setPrice(resultSet.getBigDecimal("price"));
        item.setQuantity(resultSet.getInt("quantity"));
        item.setDescription(resultSet.getString("description"));
        return item;
    }
}