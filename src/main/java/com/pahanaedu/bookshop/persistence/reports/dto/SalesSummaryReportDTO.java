package com.pahanaedu.bookshop.persistence.reports.dto;

import com.pahanaedu.bookshop.business.customer.model.Customer;
import com.pahanaedu.bookshop.business.book.model.Book;
import com.pahanaedu.bookshop.business.bill.bill.model.Bill;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

/**
 * DTO for comprehensive sales summary reports
 * Uses the same database connection pattern as existing code
 */
public class SalesSummaryReportDTO extends ReportDTO {
    private List<Customer> customers;
    private List<Book> books;
    private List<Bill> bills;
    private SalesSummary salesSummary;

    public SalesSummaryReportDTO() {
        super("SALES_SUMMARY", "Sales Summary Report");
    }

    public SalesSummaryReportDTO(List<Customer> customers, List<Book> books, List<Bill> bills) {
        this();
        this.customers = customers;
        this.books = books;
        this.bills = bills;
        this.salesSummary = new SalesSummary(customers, books, bills);
        this.setData(new Object[]{customers, books, bills});
        this.setSummary(salesSummary);
    }

    // Getters and Setters
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public SalesSummary getSalesSummary() {
        return salesSummary;
    }

    public void setSalesSummary(SalesSummary salesSummary) {
        this.salesSummary = salesSummary;
    }

    /**
     * Inner class for comprehensive sales summary statistics
     */
    public static class SalesSummary extends ReportSummary {
        private int totalCustomers;
        private int totalBooks;
        private int totalBills;
        private double totalRevenue;
        private int activeCustomers;
        private int availableBooks;
        private int outOfStockBooks;
        private double totalInventoryValue;
        private int billsThisWeek;
        private double revenueThisWeek;
        private int billsThisMonth;
        private double revenueThisMonth;
        private double averageBillAmount;
        private double averageCustomerValue;

        public SalesSummary() {
            super();
        }

        public SalesSummary(List<Customer> customers, List<Book> books, List<Bill> bills) {
            super();
            calculateSummary(customers, books, bills);
        }

        private void calculateSummary(List<Customer> customers, List<Book> books, List<Bill> bills) {
            // Customer statistics
            this.totalCustomers = customers.size();
            this.activeCustomers = (int) customers.stream()
                .filter(Customer::isActive)
                .count();
            
            // Book statistics
            this.totalBooks = books.size();
            this.availableBooks = (int) books.stream()
                .filter(b -> b.getQuantity() > 0)
                .count();
            this.outOfStockBooks = (int) books.stream()
                .filter(b -> b.getQuantity() <= 0)
                .count();
            this.totalInventoryValue = books.stream()
                .mapToDouble(b -> {
                    double price = b.getPrice() != null ? b.getPrice().doubleValue() : 0.0;
                    int quantity = b.getQuantity();
                    return price * quantity;
                })
                .sum();
            
            // Bill statistics
            this.totalBills = bills.size();
            this.totalRevenue = bills.stream()
                .mapToDouble(b -> b.getTotalAmount() != null ? b.getTotalAmount().doubleValue() : 0.0)
                .sum();
            this.averageBillAmount = totalBills > 0 ? totalRevenue / totalBills : 0.0;
            this.averageCustomerValue = totalCustomers > 0 ? totalRevenue / totalCustomers : 0.0;
            
            // Set base summary values
            this.setTotalRecords(totalBills);
            this.setTotalValue(totalRevenue);
            
            // Calculate time-based statistics
            Calendar weekStart = Calendar.getInstance();
            weekStart.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            weekStart.set(Calendar.HOUR_OF_DAY, 0);
            weekStart.set(Calendar.MINUTE, 0);
            weekStart.set(Calendar.SECOND, 0);
            weekStart.set(Calendar.MILLISECOND, 0);
            
            Calendar monthStart = Calendar.getInstance();
            monthStart.set(Calendar.DAY_OF_MONTH, 1);
            monthStart.set(Calendar.HOUR_OF_DAY, 0);
            monthStart.set(Calendar.MINUTE, 0);
            monthStart.set(Calendar.SECOND, 0);
            monthStart.set(Calendar.MILLISECOND, 0);
            
            // This week's statistics
            this.billsThisWeek = (int) bills.stream()
                .filter(bill -> {
                    if (bill.getCreatedAt() != null) {
                        return bill.getCreatedAt().after(weekStart.getTime());
                    }
                    return false;
                })
                .count();
            
            this.revenueThisWeek = bills.stream()
                .filter(bill -> {
                    if (bill.getCreatedAt() != null) {
                        return bill.getCreatedAt().after(weekStart.getTime());
                    }
                    return false;
                })
                .mapToDouble(b -> b.getTotalAmount() != null ? b.getTotalAmount().doubleValue() : 0.0)
                .sum();
            
            // This month's statistics
            this.billsThisMonth = (int) bills.stream()
                .filter(bill -> {
                    if (bill.getCreatedAt() != null) {
                        return bill.getCreatedAt().after(monthStart.getTime());
                    }
                    return false;
                })
                .count();
            
            this.revenueThisMonth = bills.stream()
                .filter(bill -> {
                    if (bill.getCreatedAt() != null) {
                        return bill.getCreatedAt().after(monthStart.getTime());
                    }
                    return false;
                })
                .mapToDouble(b -> b.getTotalAmount() != null ? b.getTotalAmount().doubleValue() : 0.0)
                .sum();
        }

        // Getters and Setters
        public int getTotalCustomers() {
            return totalCustomers;
        }

        public void setTotalCustomers(int totalCustomers) {
            this.totalCustomers = totalCustomers;
        }

        public int getTotalBooks() {
            return totalBooks;
        }

        public void setTotalBooks(int totalBooks) {
            this.totalBooks = totalBooks;
        }

        public int getTotalBills() {
            return totalBills;
        }

        public void setTotalBills(int totalBills) {
            this.totalBills = totalBills;
        }

        public double getTotalRevenue() {
            return totalRevenue;
        }

        public void setTotalRevenue(double totalRevenue) {
            this.totalRevenue = totalRevenue;
        }

        public int getActiveCustomers() {
            return activeCustomers;
        }

        public void setActiveCustomers(int activeCustomers) {
            this.activeCustomers = activeCustomers;
        }

        public int getAvailableBooks() {
            return availableBooks;
        }

        public void setAvailableBooks(int availableBooks) {
            this.availableBooks = availableBooks;
        }

        public int getOutOfStockBooks() {
            return outOfStockBooks;
        }

        public void setOutOfStockBooks(int outOfStockBooks) {
            this.outOfStockBooks = outOfStockBooks;
        }

        public double getTotalInventoryValue() {
            return totalInventoryValue;
        }

        public void setTotalInventoryValue(double totalInventoryValue) {
            this.totalInventoryValue = totalInventoryValue;
        }

        public int getBillsThisWeek() {
            return billsThisWeek;
        }

        public void setBillsThisWeek(int billsThisWeek) {
            this.billsThisWeek = billsThisWeek;
        }

        public double getRevenueThisWeek() {
            return revenueThisWeek;
        }

        public void setRevenueThisWeek(double revenueThisWeek) {
            this.revenueThisWeek = revenueThisWeek;
        }

        public int getBillsThisMonth() {
            return billsThisMonth;
        }

        public void setBillsThisMonth(int billsThisMonth) {
            this.billsThisMonth = billsThisMonth;
        }

        public double getRevenueThisMonth() {
            return revenueThisMonth;
        }

        public void setRevenueThisMonth(double revenueThisMonth) {
            this.revenueThisMonth = revenueThisMonth;
        }

        public double getAverageBillAmount() {
            return averageBillAmount;
        }

        public void setAverageBillAmount(double averageBillAmount) {
            this.averageBillAmount = averageBillAmount;
        }

        public double getAverageCustomerValue() {
            return averageCustomerValue;
        }

        public void setAverageCustomerValue(double averageCustomerValue) {
            this.averageCustomerValue = averageCustomerValue;
        }
    }
} 