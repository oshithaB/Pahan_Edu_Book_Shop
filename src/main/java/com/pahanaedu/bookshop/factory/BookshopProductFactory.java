package com.pahanaedu.bookshop.factory;

import com.pahanaedu.bookshop.model.Book;
import com.pahanaedu.bookshop.model.Customer;
import java.math.BigDecimal;

/**
 * Concrete Creator class for Bookshop products
 * Implements the Factory Pattern for creating Book and Customer objects
 */
public class BookshopProductFactory extends ProductFactory {
    
    // Singleton instance
    private static BookshopProductFactory instance;
    private static final Object lock = new Object();
    
    // Product type constants
    public static final String BOOK_TYPE = "book";
    public static final String CUSTOMER_TYPE = "customer";
    
    // Private constructor for singleton
    private BookshopProductFactory() {}
    
    /**
     * Get singleton instance of BookshopProductFactory
     * @return BookshopProductFactory instance
     */
    public static BookshopProductFactory getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new BookshopProductFactory();
                }
            }
        }
        return instance;
    }
    
    @Override
    public Product createProduct(String type) {
        return createProduct(type, (Object[]) null);
    }
    
    @Override
    public Product createProduct(String type, Object... params) {
        if (type == null) {
            throw new IllegalArgumentException("Product type cannot be null");
        }
        
        switch (type.toLowerCase()) {
            case BOOK_TYPE:
                return createBook(params);
            case CUSTOMER_TYPE:
                return createCustomer(params);
            default:
                throw new IllegalArgumentException("Unknown product type: " + type);
        }
    }
    
    @Override
    public String[] getSupportedTypes() {
        return new String[]{BOOK_TYPE, CUSTOMER_TYPE};
    }
    
    /**
     * Create a Book product
     * @param params optional parameters for book creation
     * @return Book instance
     */
    private Product createBook(Object... params) {
        Book book = new Book();
        
        if (params != null && params.length >= 6) {
            // Create book with parameters: title, author, isbn, category, price, quantity
            book.setTitle((String) params[0]);
            book.setAuthor((String) params[1]);
            book.setIsbn((String) params[2]);
            book.setCategory((String) params[3]);
            book.setPrice((BigDecimal) params[4]);
            book.setQuantity((Integer) params[5]);
            
            if (params.length > 6) {
                book.setDescription((String) params[6]);
            }
        }
        
        return book;
    }
    
    /**
     * Create a Customer product
     * @param params optional parameters for customer creation
     * @return Customer instance
     */
    private Product createCustomer(Object... params) {
        Customer customer = new Customer();
        
        if (params != null && params.length >= 5) {
            // Create customer with parameters: accountNumber, name, address, telephone, email
            customer.setAccountNumber((String) params[0]);
            customer.setName((String) params[1]);
            customer.setAddress((String) params[2]);
            customer.setTelephone((String) params[3]);
            customer.setEmail((String) params[4]);
        }
        
        return customer;
    }
    
    /**
     * Create a book with specific parameters
     * @param title book title
     * @param author book author
     * @param isbn book ISBN
     * @param category book category
     * @param price book price
     * @param quantity book quantity
     * @return Book instance
     */
    public Book createBook(String title, String author, String isbn, String category, BigDecimal price, int quantity) {
        return (Book) createProduct(BOOK_TYPE, title, author, isbn, category, price, quantity);
    }
    
    /**
     * Create a customer with specific parameters
     * @param accountNumber customer account number
     * @param name customer name
     * @param address customer address
     * @param telephone customer telephone
     * @param email customer email
     * @return Customer instance
     */
    public Customer createCustomer(String accountNumber, String name, String address, String telephone, String email) {
        return (Customer) createProduct(CUSTOMER_TYPE, accountNumber, name, address, telephone, email);
    }
}