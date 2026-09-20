package com.anujamart.dao;

import com.anujamart.DBConnection;
import com.anujamart.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public Product findById(int id) throws SQLException {
        String sql = "SELECT id, seller_id, name, category, price, quantity, description, image_url, created_at FROM products WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToProduct(rs);
                }
            }
        }
        return null;
    }

    public List<Product> findAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT id, seller_id, name, category, price, quantity, description, image_url, created_at FROM products ORDER BY id DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRowToProduct(rs));
            }
        }
        return list;
    }

    public List<Product> findBySellerId(int sellerId) throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT id, seller_id, name, category, price, quantity, description, image_url, created_at FROM products WHERE seller_id = ? ORDER BY id DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, sellerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToProduct(rs));
                }
            }
        }
        return list;
    }

    public List<Product> searchAndFilter(String query, String category) throws SQLException {
        List<Product> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT id, seller_id, name, category, price, quantity, description, image_url, created_at FROM products WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (category != null && !category.trim().isEmpty() && !category.equalsIgnoreCase("ALL")) {
            sql.append(" AND LOWER(category) = LOWER(?)");
            params.add(category.trim());
        }

        if (query != null && !query.trim().isEmpty()) {
            sql.append(" AND (LOWER(name) LIKE ? OR LOWER(description) LIKE ?)");
            String wildcard = "%" + query.trim().toLowerCase() + "%";
            params.add(wildcard);
            params.add(wildcard);
        }

        sql.append(" ORDER BY id DESC");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToProduct(rs));
                }
            }
        }
        return list;
    }

    public List<String> findAllCategories() throws SQLException {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM products WHERE category IS NOT NULL AND category != '' ORDER BY category ASC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        }
        return categories;
    }

    public boolean create(Product product) throws SQLException {
        String sql = "INSERT INTO products (seller_id, name, category, price, quantity, description, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (product.getSellerId() != null) {
                ps.setInt(1, product.getSellerId());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            ps.setString(2, product.getName());
            ps.setString(3, product.getCategory());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getQuantity());
            ps.setString(6, product.getDescription());
            ps.setString(7, product.getImageUrl());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        product.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean update(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, category = ?, price = ?, quantity = ?, description = ?, image_url = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getDescription());
            ps.setString(6, product.getImageUrl());
            ps.setInt(7, product.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteBySeller(int id, int sellerId) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ? AND seller_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, sellerId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean reduceStock(int productId, int quantity) throws SQLException {
        String sql = "UPDATE products SET quantity = quantity - ? WHERE id = ? AND quantity >= ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            return ps.executeUpdate() > 0;
        }
    }

    private Product mapRowToProduct(ResultSet rs) throws SQLException {
        int sellerId = rs.getInt("seller_id");
        Integer sellerIdObj = rs.wasNull() ? null : sellerId;
        return new Product(
                rs.getInt("id"),
                sellerIdObj,
                rs.getString("name"),
                rs.getString("category"),
                rs.getDouble("price"),
                rs.getInt("quantity"),
                rs.getString("description"),
                rs.getString("image_url"),
                rs.getTimestamp("created_at")
        );
    }
}
