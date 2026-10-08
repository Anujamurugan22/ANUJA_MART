package com.anujamart.dao;

import com.anujamart.DBConnection;
import com.anujamart.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public boolean create(Review review) throws SQLException {
        return saveOrUpdate(review);
    }

    public boolean saveOrUpdate(Review review) throws SQLException {
        if (review.getBuyerId() == null) {
            throw new SQLException("Buyer ID is required to submit a review");
        }

        // Check if the buyer already reviewed this product
        String checkSql = "SELECT id FROM reviews WHERE buyer_id = ? AND product_id = ?";
        try (Connection con = DBConnection.getConnection()) {
            try (PreparedStatement checkPs = con.prepareStatement(checkSql)) {
                checkPs.setInt(1, review.getBuyerId());
                checkPs.setInt(2, review.getProductId());
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next()) {
                        int existingId = rs.getInt("id");
                        String updateSql = "UPDATE reviews SET rating = ?, comment = ?, review_date = CURRENT_TIMESTAMP WHERE id = ?";
                        try (PreparedStatement updatePs = con.prepareStatement(updateSql)) {
                            updatePs.setInt(1, review.getRating());
                            updatePs.setString(2, review.getComment());
                            updatePs.setInt(3, existingId);
                            int affected = updatePs.executeUpdate();
                            review.setId(existingId);
                            return affected > 0;
                        }
                    }
                }
            }

            // Insert new review
            String insertSql = "INSERT INTO reviews (buyer_id, product_id, rating, comment, review_date) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
            try (PreparedStatement insertPs = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertPs.setInt(1, review.getBuyerId());
                insertPs.setInt(2, review.getProductId());
                insertPs.setInt(3, review.getRating());
                insertPs.setString(4, review.getComment());

                int affected = insertPs.executeUpdate();
                if (affected > 0) {
                    try (ResultSet rs = insertPs.getGeneratedKeys()) {
                        if (rs.next()) {
                            review.setId(rs.getInt(1));
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public Review findByBuyerAndProduct(int buyerId, int productId) throws SQLException {
        String sql = "SELECT r.id, r.buyer_id, r.product_id, r.rating, r.comment, r.review_date, u.name AS buyer_name " +
                     "FROM reviews r " +
                     "LEFT JOIN users u ON r.buyer_id = u.id " +
                     "WHERE r.buyer_id = ? AND r.product_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, buyerId);
            ps.setInt(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String buyerName = rs.getString("buyer_name");
                    if (buyerName == null || buyerName.trim().isEmpty()) {
                        buyerName = "Verified Customer";
                    }
                    return new Review(
                            rs.getInt("id"),
                            buyerId,
                            buyerName,
                            rs.getInt("product_id"),
                            rs.getInt("rating"),
                            rs.getString("comment"),
                            rs.getTimestamp("review_date")
                    );
                }
            }
        }
        return null;
    }

    public List<Review> findByProductId(int productId) throws SQLException {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT r.id, r.buyer_id, r.product_id, r.rating, r.comment, r.review_date, u.name AS buyer_name " +
                     "FROM reviews r " +
                     "LEFT JOIN users u ON r.buyer_id = u.id " +
                     "WHERE r.product_id = ? " +
                     "ORDER BY r.id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int buyerId = rs.getInt("buyer_id");
                    Integer buyerIdObj = rs.wasNull() ? null : buyerId;
                    String buyerName = rs.getString("buyer_name");
                    if (buyerName == null || buyerName.trim().isEmpty()) {
                        buyerName = "Verified Customer";
                    }
                    Review rev = new Review(
                            rs.getInt("id"),
                            buyerIdObj,
                            buyerName,
                            rs.getInt("product_id"),
                            rs.getInt("rating"),
                            rs.getString("comment"),
                            rs.getTimestamp("review_date")
                    );
                    list.add(rev);
                }
            }
        }
        return list;
    }

    public double getAverageRating(int productId) throws SQLException {
        String sql = "SELECT COALESCE(AVG(rating), 0) FROM reviews WHERE product_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Math.round(rs.getDouble(1) * 10.0) / 10.0;
                }
            }
        }
        return 0.0;
    }

    public int getReviewCount(int productId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM reviews WHERE product_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}
