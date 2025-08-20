package com.pahanaedu.bookshop.persistence.reports.dto;

import com.pahanaedu.bookshop.business.book.model.Book;
import java.util.List;

/**
 * DTO for book inventory reports
 * Uses the same database connection pattern as existing code
 */
public class BookReportDTO extends ReportDTO {
    private List<Book> books;
    private BookSummary bookSummary;

    public BookReportDTO() {
        super("BOOK", "Book Inventory Report");
    }

    public BookReportDTO(List<Book> books) {
        this();
        this.books = books;
        this.bookSummary = new BookSummary(books);
        this.setData(books);
        this.setSummary(bookSummary);
    }

    // Getters and Setters
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public BookSummary getBookSummary() {
        return bookSummary;
    }

    public void setBookSummary(BookSummary bookSummary) {
        this.bookSummary = bookSummary;
    }

    /**
     * Inner class for book-specific summary statistics
     */
    public static class BookSummary extends ReportSummary {
        private int totalBooks;
        private int availableBooks;
        private int outOfStockBooks;
        private double totalInventoryValue;
        private int lowStockBooks;
        private int categories;

        public BookSummary() {
            super();
        }

        public BookSummary(List<Book> books) {
            super();
            calculateSummary(books);
        }

        private void calculateSummary(List<Book> books) {
            this.totalBooks = books.size();
            this.setTotalRecords(totalBooks);
            
            // Count available books (quantity > 0)
            this.availableBooks = (int) books.stream()
                .filter(b -> b.getQuantity() > 0)
                .count();
            
            // Count out of stock books (quantity <= 0)
            this.outOfStockBooks = (int) books.stream()
                .filter(b -> b.getQuantity() <= 0)
                .count();
            
            // Calculate total inventory value
            this.totalInventoryValue = books.stream()
                .mapToDouble(b -> {
                    double price = b.getPrice() != null ? b.getPrice().doubleValue() : 0.0;
                    int quantity = b.getQuantity();
                    return price * quantity;
                })
                .sum();
            this.setTotalValue(totalInventoryValue);
            
            // Count low stock books (quantity <= 5 but > 0)
            this.lowStockBooks = (int) books.stream()
                .filter(b -> b.getQuantity() > 0 && b.getQuantity() <= 5)
                .count();
            
            // Count unique categories
            this.categories = (int) books.stream()
                .filter(b -> b.getCategory() != null && !b.getCategory().trim().isEmpty())
                .map(Book::getCategory)
                .distinct()
                .count();
        }

        // Getters and Setters
        public int getTotalBooks() {
            return totalBooks;
        }

        public void setTotalBooks(int totalBooks) {
            this.totalBooks = totalBooks;
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

        public int getLowStockBooks() {
            return lowStockBooks;
        }

        public void setLowStockBooks(int lowStockBooks) {
            this.lowStockBooks = lowStockBooks;
        }

        public int getCategories() {
            return categories;
        }

        public void setCategories(int categories) {
            this.categories = categories;
        }
    }
} 