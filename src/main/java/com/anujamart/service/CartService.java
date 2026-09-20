package com.anujamart.service;

import com.anujamart.dao.CartDAO;
import com.anujamart.dao.ProductDAO;
import com.anujamart.model.CartItem;
import com.anujamart.model.Product;

import java.sql.SQLException;
import java.util.List;

public class CartService {

    private final CartDAO cartDAO;
    private final ProductDAO productDAO;

    public CartService() {
        this.cartDAO = new CartDAO();
        this.productDAO = new ProductDAO();
    }

    public CartService(CartDAO cartDAO, ProductDAO productDAO) {
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
    }

    public List<CartItem> getCart(int userId) throws SQLException {
        return cartDAO.getCartItems(userId);
    }

    public void addToCart(int userId, int productId, int quantity) throws Exception {
        if (quantity <= 0) {
            quantity = 1;
        }

        Product product = productDAO.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Requested quantity exceeds available stock (" + product.getQuantity() + " available)");
        }

        cartDAO.addItem(userId, productId, quantity);
    }

    public boolean updateQuantity(int cartItemId, int userId, int quantity) throws Exception {
        return cartDAO.updateQuantity(cartItemId, userId, quantity);
    }

    public boolean removeFromCart(int cartItemId, int userId) throws SQLException {
        return cartDAO.removeItem(cartItemId, userId);
    }

    public void clearCart(int userId) throws SQLException {
        cartDAO.clearCart(userId);
    }

    public int getCartCount(int userId) throws SQLException {
        return cartDAO.getCartCount(userId);
    }

    public double getCartTotal(int userId) throws SQLException {
        List<CartItem> items = cartDAO.getCartItems(userId);
        double total = 0.0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }
}
