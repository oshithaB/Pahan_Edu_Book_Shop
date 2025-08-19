package com.pahanaedu.bookshop.persistence.reports.dto;

import com.pahanaedu.bookshop.business.bill.bill.model.Bill;
import java.util.List;
import java.util.Date;
import java.util.Calendar;


 //DTO for bill/sales reports

public class BillReportDTO extends ReportDTO {
    private List<Bill> bills;
    private BillSummary billSummary;

    public BillReportDTO() {
        super("BILL", "Bill Report");
    }

    public BillReportDTO(List<Bill> bills) {
        this();
        this.bills = bills;
        this.billSummary = new BillSummary(bills);
        this.setData(bills);
        this.setSummary(billSummary);
    }

    // Getters and Setters
    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public BillSummary getBillSummary() {
        return billSummary;
    }

    public void setBillSummary(BillSummary billSummary) {
        this.billSummary = billSummary;
    }

    /**
     * Inner class for bill-specific summary statistics
     */
    public static class BillSummary extends ReportSummary {
        private int totalBills;
        private double totalRevenue;
        private int billsThisWeek;
        private double revenueThisWeek;
        private int billsThisMonth;
        private double revenueThisMonth;
        private double averageBillAmount;

        public BillSummary() {
            super();
        }

        public BillSummary(List<Bill> bills) {
            super();
            calculateSummary(bills);
        }

        private void calculateSummary(List<Bill> bills) {
            this.totalBills = bills.size();
            this.setTotalRecords(totalBills);
            
            // Calculate total revenue
            this.totalRevenue = bills.stream()
                .mapToDouble(b -> b.getTotalAmount() != null ? b.getTotalAmount().doubleValue() : 0.0)
                .sum();
            this.setTotalValue(totalRevenue);
            
            // Calculate average bill amount
            this.averageBillAmount = totalBills > 0 ? totalRevenue / totalBills : 0.0;
            
            // Calculate this week's statistics
            Calendar weekStart = Calendar.getInstance();
            weekStart.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            weekStart.set(Calendar.HOUR_OF_DAY, 0);
            weekStart.set(Calendar.MINUTE, 0);
            weekStart.set(Calendar.SECOND, 0);
            weekStart.set(Calendar.MILLISECOND, 0);
            
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
            
            // Calculate this month's statistics
            Calendar monthStart = Calendar.getInstance();
            monthStart.set(Calendar.DAY_OF_MONTH, 1);
            monthStart.set(Calendar.HOUR_OF_DAY, 0);
            monthStart.set(Calendar.MINUTE, 0);
            monthStart.set(Calendar.SECOND, 0);
            monthStart.set(Calendar.MILLISECOND, 0);
            
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
    }
} 