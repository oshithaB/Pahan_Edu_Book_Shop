package com.pahanaedu.bookshop.factory;

/**
 * Abstract Creator class for Product Factory Pattern
 * Defines the factory method for creating products
 */
public abstract class ProductFactory {
    
    /**
     * Factory method to create products
     * @param type the type of product to create
     * @return Product instance
     */
    public abstract Product createProduct(String type);
    
    /**
     * Factory method to create products with parameters
     * @param type the type of product to create
     * @param params parameters for product creation
     * @return Product instance
     */
    public abstract Product createProduct(String type, Object... params);
    
    /**
     * Get the supported product types
     * @return array of supported product types
     */
    public abstract String[] getSupportedTypes();
    
    /**
     * Check if a product type is supported
     * @param type product type to check
     * @return true if supported, false otherwise
     */
    public boolean isTypeSupported(String type) {
        if (type == null) return false;
        
        String[] supportedTypes = getSupportedTypes();
        for (String supportedType : supportedTypes) {
            if (supportedType.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Create a product with validation
     * @param type product type
     * @param params creation parameters
     * @return validated Product instance
     * @throws IllegalArgumentException if type is not supported or validation fails
     */
    public Product createValidatedProduct(String type, Object... params) {
        if (!isTypeSupported(type)) {
            throw new IllegalArgumentException("Unsupported product type: " + type);
        }
        
        Product product = createProduct(type, params);
        if (product != null && !product.validate()) {
            throw new IllegalArgumentException("Product validation failed for type: " + type);
        }
        
        return product;
    }
}