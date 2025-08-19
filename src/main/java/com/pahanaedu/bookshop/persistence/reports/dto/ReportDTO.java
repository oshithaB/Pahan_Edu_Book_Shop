package com.pahanaedu.bookshop.persistence.reports.dto;

import java.util.Date;
import java.util.List;

/**
 * Base DTO for all report types
 * Uses the same database connection pattern as existing code
 */
public class ReportDTO {
    private String reportId;
    private String reportType;
    private String reportTitle;
    private Date generatedAt;
    private String generatedBy;
    private List<String> filters;
    private Object data;
    private ReportSummary summary;

    public ReportDTO() {
        this.generatedAt = new Date();
    }

    public ReportDTO(String reportType, String reportTitle) {
        this();
        this.reportType = reportType;
        this.reportTitle = reportTitle;
    }

    // Getters and Setters
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public Date getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Date generatedAt) {
        this.generatedAt = generatedAt;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ReportSummary getSummary() {
        return summary;
    }

    public void setSummary(ReportSummary summary) {
        this.summary = summary;
    }

    /**
     * Inner class for report summary statistics
     */
    public static class ReportSummary {
        private int totalRecords;
        private double totalValue;
        private String currency;
        private Date dateRangeStart;
        private Date dateRangeEnd;

        public ReportSummary() {
            this.currency = "USD";
        }

        public ReportSummary(int totalRecords, double totalValue) {
            this();
            this.totalRecords = totalRecords;
            this.totalValue = totalValue;
        }

        // Getters and Setters
        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public double getTotalValue() {
            return totalValue;
        }

        public void setTotalValue(double totalValue) {
            this.totalValue = totalValue;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Date getDateRangeStart() {
            return dateRangeStart;
        }

        public void setDateRangeStart(Date dateRangeStart) {
            this.dateRangeStart = dateRangeStart;
        }

        public Date getDateRangeEnd() {
            return dateRangeEnd;
        }

        public void setDateRangeEnd(Date dateRangeEnd) {
            this.dateRangeEnd = dateRangeEnd;
        }
    }
} 