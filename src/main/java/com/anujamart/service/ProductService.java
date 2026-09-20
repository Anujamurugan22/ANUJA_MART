package com.anujamart.service;

import com.anujamart.dao.ProductDAO;
import com.anujamart.model.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public Product getProductById(int id) throws SQLException {
        return productDAO.findById(id);
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.findAll();
    }

    public List<Product> getProductsBySeller(int sellerId) throws SQLException {
        return productDAO.findBySellerId(sellerId);
    }

    public List<Product> searchAndFilter(String query, String category) throws SQLException {
        return productDAO.searchAndFilter(query, category);
    }

    public List<String> getAllCategories() throws SQLException {
        return productDAO.findAllCategories();
    }

    public Product addProduct(Integer sellerId, String name, String category, double price, int quantity, String description, String imageUrl) throws Exception {
        validateProduct(name, category, price, quantity);

        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            imageUrl = "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500";
        }

        Product product = new Product(sellerId, name.trim(), category.trim(), price, quantity, description.trim(), imageUrl.trim());
        boolean created = productDAO.create(product);
        if (!created) {
            throw new SQLException("Failed to add product");
        }
        return product;
    }

    public boolean updateProduct(int id, Integer sellerId, String name, String category, double price, int quantity, String description, String imageUrl) throws Exception {
        validateProduct(name, category, price, quantity);

        Product existing = productDAO.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }

        // Verify seller ownership if sellerId is provided
        if (sellerId != null && existing.getSellerId() != null && !sellerId.equals(existing.getSellerId())) {
            throw new SecurityException("Unauthorized to modify this product");
        }

        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            imageUrl = existing.getImageUrl();
        }

        existing.setName(name.trim());
        existing.setCategory(category.trim());
        existing.setPrice(price);
        existing.setQuantity(quantity);
        existing.setDescription(description != null ? description.trim() : "");
        existing.setImageUrl(imageUrl.trim());

        return productDAO.update(existing);
    }

    public boolean deleteProduct(int id, Integer sellerId, boolean isAdmin) throws Exception {
        Product existing = productDAO.findById(id);
        if (existing == null) {
            return false;
        }

        if (!isAdmin && sellerId != null && existing.getSellerId() != null && !sellerId.equals(existing.getSellerId())) {
            throw new SecurityException("Unauthorized to delete this product");
        }

        return productDAO.delete(id);
    }

    private void validateProduct(String name, String category, double price, int quantity) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Product category is required");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Product price must be greater than 0");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }
    }
}
