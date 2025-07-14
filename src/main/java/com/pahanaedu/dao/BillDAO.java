package com.pahanaedu.dao;

import com.pahanaedu.model.Bill;
import com.pahanaedu.model.BillItem;
import com.pahanaedu.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Data Access Object for Bill operations.
 * Handles all database operations related to bills and bill items.
 */
public class BillDAO {
    
    /**
     * Retrieves all bills from the database.
     * @return List of all bills
     */
    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT b.*, c.name as customer_name, c.account_number as customer_account, " +
                      "u.full_name as user_name FROM bills b " +
                      "JOIN customers c ON b.customer_id = c.customer_id " +
                      "JOIN users u ON b.user_id = u.user_id " +
                      "ORDER BY b.bill_date DESC";
        
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                bills.add(mapResultSetToBill(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }
    
    /**
     * Saves a new bill to the database.
     * @param bill Bill object to save
     * @return Generated bill ID, or -1 if failed
     */
    public int saveBill(Bill bill) {
        String billQuery = "INSERT INTO bills (bill_number, customer_id, user_id, bill_date, total_amount) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);
            
            try (PreparedStatement billStatement = connection.prepareStatement(billQuery, Statement.RETURN_GENERATED_KEYS)) {
                billStatement.setString(1, bill.getBillNumber());
                billStatement.setInt(2, bill.getCustomerId());
                billStatement.setInt(3, bill.getUserId());
                billStatement.setTimestamp(4, Timestamp.valueOf(bill.getBillDate()));
                billStatement.setBigDecimal(5, bill.getTotalAmount());
                
                int affectedRows = billStatement.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    return -1;
                }
                
                ResultSet generatedKeys = billStatement.getGeneratedKeys();
                int billId = -1;
                if (generatedKeys.next()) {
                    billId = generatedKeys.getInt(1);
                }
                
                // Save bill items
                if (billId > 0 && !bill.getBillItems().isEmpty()) {
                    saveBillItems(connection, billId, bill.getBillItems());
                }
                
                connection.commit();
                return billId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    /**
     * Saves bill items to the database.
     * @param connection Database connection
     * @param billId Bill ID
     * @param billItems List of bill items
     * @throws SQLException if SQL error occurs
     */
    private void saveBillItems(Connection connection, int billId, List<BillItem> billItems) throws SQLException {
        String itemQuery = "INSERT INTO bill_items (bill_id, item_id, item_code, item_title, unit_price, quantity, tax_percentage, discount_percentage, total_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement itemStatement = connection.prepareStatement(itemQuery)) {
            for (BillItem item : billItems) {
                itemStatement.setInt(1, billId);
                itemStatement.setInt(2, item.getItemId());
                itemStatement.setString(3, item.getItemCode());
                itemStatement.setString(4, item.getItemTitle());
                itemStatement.setBigDecimal(5, item.getUnitPrice());
                itemStatement.setInt(6, item.getQuantity());
                itemStatement.setBigDecimal(7, item.getTaxPercentage());
                itemStatement.setBigDecimal(8, item.getDiscountPercentage());
                itemStatement.setBigDecimal(9, item.getTotalPrice());
                itemStatement.addBatch();
            }
            itemStatement.executeBatch();
        }
    }
    
    /**
     * Retrieves a bill by ID with its items.
     * @param billId Bill ID
     * @return Bill object if found, null otherwise
     */
    public Bill getBillById(int billId) {
        String query = "SELECT b.*, c.name as customer_name, c.account_number as customer_account, " +
                      "u.full_name as user_name FROM bills b " +
                      "JOIN customers c ON b.customer_id = c.customer_id " +
                      "JOIN users u ON b.user_id = u.user_id " +
                      "WHERE b.bill_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, billId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                Bill bill = mapResultSetToBill(resultSet);
                bill.setBillItems(getBillItems(billId));
                return bill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Retrieves bill items for a specific bill.
     * @param billId Bill ID
     * @return List of bill items
     */
    public List<BillItem> getBillItems(int billId) {
        List<BillItem> billItems = new ArrayList<>();
        String query = "SELECT * FROM bill_items WHERE bill_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, billId);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                billItems.add(mapResultSetToBillItem(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billItems;
    }
    
    /**
     * Gets sales summary information.
     * @return Map containing sales summary data
     */
    public Map<String, Object> getSalesSummary() {
        Map<String, Object> summary = new HashMap<>();
        String query = "SELECT COUNT(*) as total_bills, SUM(total_amount) as total_revenue, " +
                      "SUM(bi.quantity) as total_books_sold FROM bills b " +
                      "LEFT JOIN bill_items bi ON b.bill_id = bi.bill_id";
        
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            if (resultSet.next()) {
                summary.put("totalBills", resultSet.getInt("total_bills"));
                summary.put("totalRevenue", resultSet.getBigDecimal("total_revenue"));
                summary.put("totalBooksSold", resultSet.getInt("total_books_sold"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return summary;
    }
    
    /**
     * Maps ResultSet to Bill object.
     * @param resultSet ResultSet from database query
     * @return Bill object
     * @throws SQLException if SQL error occurs
     */
    private Bill mapResultSetToBill(ResultSet resultSet) throws SQLException {
        Bill bill = new Bill();
        bill.setBillId(resultSet.getInt("bill_id"));
        bill.setBillNumber(resultSet.getString("bill_number"));
        bill.setCustomerId(resultSet.getInt("customer_id"));
        bill.setCustomerName(resultSet.getString("customer_name"));
        bill.setCustomerAccount(resultSet.getString("customer_account"));
        bill.setUserId(resultSet.getInt("user_id"));
        bill.setUserName(resultSet.getString("user_name"));
        bill.setBillDate(resultSet.getTimestamp("bill_date").toLocalDateTime());
        bill.setTotalAmount(resultSet.getBigDecimal("total_amount"));
        return bill;
    }
    
    /**
     * Maps ResultSet to BillItem object.
     * @param resultSet ResultSet from database query
     * @return BillItem object
     * @throws SQLException if SQL error occurs
     */
    private BillItem mapResultSetToBillItem(ResultSet resultSet) throws SQLException {
        BillItem billItem = new BillItem();
        billItem.setBillItemId(resultSet.getInt("bill_item_id"));
        billItem.setBillId(resultSet.getInt("bill_id"));
        billItem.setItemId(resultSet.getInt("item_id"));
        billItem.setItemCode(resultSet.getString("item_code"));
        billItem.setItemTitle(resultSet.getString("item_title"));
        billItem.setUnitPrice(resultSet.getBigDecimal("unit_price"));
        billItem.setQuantity(resultSet.getInt("quantity"));
        
        // Handle tax and discount percentages with null checks
        BigDecimal taxPercentage = resultSet.getBigDecimal("tax_percentage");
        billItem.setTaxPercentage(taxPercentage != null ? taxPercentage : BigDecimal.ZERO);
        
        BigDecimal discountPercentage = resultSet.getBigDecimal("discount_percentage");
        billItem.setDiscountPercentage(discountPercentage != null ? discountPercentage : BigDecimal.ZERO);
        
        billItem.setTotalPrice(resultSet.getBigDecimal("total_price"));
        return billItem;
    }
}