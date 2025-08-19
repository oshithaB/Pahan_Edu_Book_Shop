package com.pahanaedu.bookshop.persistence.reports.dto;

import com.pahanaedu.bookshop.business.customer.model.Customer;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

/**
 * DTO for customer reports
 * Uses the same database connection pattern as existing code
 */
public class CustomerReportDTO extends ReportDTO {
    private List<Customer> customers;
    private CustomerSummary customerSummary;

    public CustomerReportDTO() {
        super("CUSTOMER", "Customer Report");
    }

    public CustomerReportDTO(List<Customer> customers) {
        this();
        this.customers = customers;
        this.customerSummary = new CustomerSummary(customers);
        this.setData(customers);
        this.setSummary(customerSummary);
    }

    // Getters and Setters
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public CustomerSummary getCustomerSummary() {
        return customerSummary;
    }

    public void setCustomerSummary(CustomerSummary customerSummary) {
        this.customerSummary = customerSummary;
    }

    /**
     * Inner class for customer-specific summary statistics
     */
    public static class CustomerSummary extends ReportSummary {
        private int totalCustomers;
        private int activeCustomers;
        private int newCustomersThisMonth;
        private int newCustomersThisWeek;

        public CustomerSummary() {
            super();
        }

        public CustomerSummary(List<Customer> customers) {
            super();
            calculateSummary(customers);
        }

        private void calculateSummary(List<Customer> customers) {
            this.totalCustomers = customers.size();
            this.setTotalRecords(totalCustomers);
            
            // Count active customers
            this.activeCustomers = (int) customers.stream()
                .filter(Customer::isActive)
                .count();
            
            // Calculate new customers this month
            Calendar cal = Calendar.getInstance();
            int currentMonth = cal.get(Calendar.MONTH);
            int currentYear = cal.get(Calendar.YEAR);
            
            this.newCustomersThisMonth = (int) customers.stream()
                .filter(customer -> {
                    if (customer.getCreatedAt() != null) {
                        Calendar customerCal = Calendar.getInstance();
                        customerCal.setTime(customer.getCreatedAt());
                        return customerCal.get(Calendar.MONTH) == currentMonth && 
                               customerCal.get(Calendar.YEAR) == currentYear;
                    }
                    return false;
                })
                .count();
            
            // Calculate new customers this week
            Calendar weekStart = Calendar.getInstance();
            weekStart.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            weekStart.set(Calendar.HOUR_OF_DAY, 0);
            weekStart.set(Calendar.MINUTE, 0);
            weekStart.set(Calendar.SECOND, 0);
            weekStart.set(Calendar.MILLISECOND, 0);
            
            this.newCustomersThisWeek = (int) customers.stream()
                .filter(customer -> {
                    if (customer.getCreatedAt() != null) {
                        return customer.getCreatedAt().after(weekStart.getTime());
                    }
                    return false;
                })
                .count();
        }

        // Getters and Setters
        public int getTotalCustomers() {
            return totalCustomers;
        }

        public void setTotalCustomers(int totalCustomers) {
            this.totalCustomers = totalCustomers;
        }

        public int getActiveCustomers() {
            return activeCustomers;
        }

        public void setActiveCustomers(int activeCustomers) {
            this.activeCustomers = activeCustomers;
        }

        public int getNewCustomersThisMonth() {
            return newCustomersThisMonth;
        }

        public void setNewCustomersThisMonth(int newCustomersThisMonth) {
            this.newCustomersThisMonth = newCustomersThisMonth;
        }

        public int getNewCustomersThisWeek() {
            return newCustomersThisWeek;
        }

        public void setNewCustomersThisWeek(int newCustomersThisWeek) {
            this.newCustomersThisWeek = newCustomersThisWeek;
        }
    }
} 