package com.pahanaedu.bookshop.persistence.reports.service;

import com.pahanaedu.bookshop.persistence.reports.dto.*;
import com.pahanaedu.bookshop.persistence.dao.CustomerDAO;
import com.pahanaedu.bookshop.persistence.dao.BookDAO;
import com.pahanaedu.bookshop.persistence.dao.BillDAO;
import com.pahanaedu.bookshop.business.customer.model.Customer;
import com.pahanaedu.bookshop.business.book.model.Book;
import com.pahanaedu.bookshop.business.bill.bill.model.Bill;
import com.pahanaedu.bookshop.persistence.resource.resource.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


 // Service class for generating reports

public class ReportService {
    private final CustomerDAO customerDAO;
    private final BookDAO bookDAO;
    private final BillDAO billDAO;
    private final Set<String> validReportTypes;

    public ReportService() {
        // Use the same database connection pattern as existing code
        this.customerDAO = new CustomerDAO();
        this.bookDAO = new BookDAO();
        this.billDAO = new BillDAO();
        
        // Define valid report types
        this.validReportTypes = new HashSet<>(Arrays.asList(
            "CUSTOMER", "BOOK", "BILL", "SALES_SUMMARY"
        ));
    }

    /**
     * Check if a report type is valid
     */
    public boolean isValidReportType(String reportType) {
        return reportType != null && validReportTypes.contains(reportType.toUpperCase());
    }

    /**
     * Generate customer report
     */
    public CustomerReportDTO generateCustomerReport() {
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            return new CustomerReportDTO(customers);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate customer report", e);
        }
    }

    /**
     * Generate book inventory report
     */
    public BookReportDTO generateBookReport() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            return new BookReportDTO(books);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate book report", e);
        }
    }

    /**
     * Generate bill/sales report
     */
    public BillReportDTO generateBillReport() {
        try {
            List<Bill> bills = billDAO.getAllBills();
            return new BillReportDTO(bills);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate bill report", e);
        }
    }

    /**
     * Generate comprehensive sales summary report
     */
    public SalesSummaryReportDTO generateSalesSummaryReport() {
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            List<Book> books = bookDAO.getAllBooks();
            List<Bill> bills = billDAO.getAllBills();
            
            return new SalesSummaryReportDTO(customers, books, bills);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate sales summary report", e);
        }
    }

    /**
     * Generate report based on type
     */
    public ReportDTO generateReport(String reportType) {
        if (!isValidReportType(reportType)) {
            throw new IllegalArgumentException("Invalid report type: " + reportType);
        }

        switch (reportType.toUpperCase()) {
            case "CUSTOMER":
                return generateCustomerReport();
            case "BOOK":
                return generateBookReport();
            case "BILL":
                return generateBillReport();
            case "SALES_SUMMARY":
                return generateSalesSummaryReport();
            default:
                throw new IllegalArgumentException("Unsupported report type: " + reportType);
        }
    }

    /**
     * Get available report types
     */
    public Set<String> getAvailableReportTypes() {
        return new HashSet<>(validReportTypes);
    }
} 