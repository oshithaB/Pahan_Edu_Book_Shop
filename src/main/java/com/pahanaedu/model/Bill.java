package com.pahanaedu.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Bill model class representing sales transactions.
 */
public class Bill {
    private int billId;
    private String billNumber;
    private int customerId;
    private String customerName;
    private String customerAccount;
    private int userId;
    private String userName;
    private LocalDateTime billDate;
    private BigDecimal totalAmount;
    private List<BillItem> billItems;
    
    // Default constructor
    public Bill() {
        this.billItems = new ArrayList<>();
        this.billDate = LocalDateTime.now();
    }
    
    // Constructor with essential fields
    public Bill(String billNumber, int customerId, int userId, BigDecimal totalAmount) {
        this();
        this.billNumber = billNumber;
        this.customerId = customerId;
        this.userId = userId;
        this.totalAmount = totalAmount;
    }
    
    // Getters and setters
    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }
    
    public String getBillNumber() { return billNumber; }
    public void setBillNumber(String billNumber) { this.billNumber = billNumber; }
    
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getCustomerAccount() { return customerAccount; }
    public void setCustomerAccount(String customerAccount) { this.customerAccount = customerAccount; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public LocalDateTime getBillDate() { return billDate; }
    public void setBillDate(LocalDateTime billDate) { this.billDate = billDate; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public List<BillItem> getBillItems() { return billItems; }
    public void setBillItems(List<BillItem> billItems) { this.billItems = billItems; }
    
    // Helper method to add bill item
    public void addBillItem(BillItem item) {
        this.billItems.add(item);
    }
    
    @Override
    public String toString() {
        return "Bill{" +
                "billId=" + billId +
                ", billNumber='" + billNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", totalAmount=" + totalAmount +
                ", billDate=" + billDate +
                '}';
    }
}