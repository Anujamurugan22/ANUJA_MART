package com.anujamart.dao;

import com.anujamart.DBConnection;
import com.anujamart.model.CartItem;
import com.anujamart.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public List<CartItem> getCartItems(int userId) throws SQLException {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT c.id AS cart_id, c.user_id, c.product_id, c.quantity AS cart_quantity, c.created_at AS cart_created_at, " +
                     "p.id AS p_id, p.seller_id, p.name AS p_name, p.category, p.price, p.quantity AS p_quantity, p.description, p.image_url, p.created_at AS p_created_at " +
                     "FROM cart_items c " +
                     "JOIN products p ON c.product_id = p.id " +
                     "WHERE c.user_id = ? " +
                     "ORDER BY c.id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartItem item = new CartItem(
                            rs.getInt("cart_id"),
                            rs.getInt("user_id"),
                            rs.getInt("product_id"),
                            rs.getInt("cart_quantity"),
                            rs.getTimestamp("cart_created_at")
                    );

                    int sellerId = rs.getInt("seller_id");
                    Integer sellerIdObj = rs.wasNull() ? null : sellerId;
                    Product p = new Product(
                            rs.getInt("p_id"),
                            sellerIdObj,
                            rs.getString("p_name"),
                            rs.getString("category"),
                            rs.getDouble("price"),
                            rs.getInt("p_quantity"),
                            rs.getString("description"),
                            rs.getString("image_url"),
                            rs.getTimestamp("p_created_at")
                    );
                    item.setProduct(p);
                    list.add(item);
                }
            }
        }
        return list;
    }

    public void addItem(int userId, int productId, int quantity) throws SQLException {
        // Check if item is already in user's cart
        String checkSql = "SELECT id, quantity FROM cart_items WHERE user_id = ? AND product_id = ?";
        try (Connection con = DBConnection.getConnection()) {
            try (PreparedStatement checkPs = con.prepareStatement(checkSql)) {
                checkPs.setInt(1, userId);
                checkPs.setInt(2, productId);
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next()) {
                        int existingId = rs.getInt("id");
                        int newQty = rs.getInt("quantity") + quantity;
                        String updateSql = "UPDATE cart_items SET quantity = ? WHERE id = ?";
                        try (PreparedStatement updatePs = con.prepareStatement(updateSql)) {
                            updatePs.setInt(1, newQty);
                            updatePs.setInt(2, existingId);
                            updatePs.executeUpdate();
                            return;
                        }
                    }
                }
            }

            // Otherwise insert new item
            String insertSql = "INSERT INTO cart_items (user_id, product_id, quantity) VALUES (?, ?, ?)";
            try (PreparedStatement insertPs = con.prepareStatement(insertSql)) {
                insertPs.setInt(1, userId);
                insertPs.setInt(2, productId);
                insertPs.setInt(3, quantity);
                insertPs.executeUpdate();
            }
        }
    }

    public boolean updateQuantity(int cartItemId, int userId, int quantity) throws SQLException {
        if (quantity <= 0) {
            return removeItem(cartItemId, userId);
        }
        String sql = "UPDATE cart_items SET quantity = ? WHERE id = ? AND user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);
            ps.setInt(3, userId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean removeItem(int cartItemId, int userId) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE id = ? AND user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cartItemId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    public void clearCart(int userId) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    public int getCartCount(int userId) throws SQLException {
        String sql = "SELECT COALESCE(SUM(quantity), 0) FROM cart_items WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}
