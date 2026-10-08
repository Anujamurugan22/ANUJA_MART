package com.anujamart.service;

import com.anujamart.dao.OrderDAO;
import com.anujamart.dao.ReviewDAO;
import com.anujamart.model.Review;

import java.sql.SQLException;
import java.util.List;

public class ReviewService {

    private final ReviewDAO reviewDAO;
    private final OrderDAO orderDAO;

    public ReviewService() {
        this.reviewDAO = new ReviewDAO();
        this.orderDAO = new OrderDAO();
    }

    public ReviewService(ReviewDAO reviewDAO, OrderDAO orderDAO) {
        this.reviewDAO = reviewDAO;
        this.orderDAO = orderDAO;
    }

    public Review addReview(Integer buyerId, int productId, int rating, String comment) throws Exception {
        if (buyerId == null || buyerId <= 0) {
            throw new SecurityException("You must be logged in as a buyer to submit a review");
        }
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5 stars");
        }
        if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("Review comment cannot be empty");
        }

        // Verify that the buyer actually purchased the product
        if (!orderDAO.hasBuyerPurchasedProduct(buyerId, productId)) {
            throw new SecurityException("You can only review products that you have purchased.");
        }

        Review review = new Review(buyerId, productId, rating, comment.trim());
        boolean success = reviewDAO.saveOrUpdate(review);
        if (!success) {
            throw new SQLException("Failed to save review");
        }
        return review;
    }

    public boolean hasBuyerPurchasedProduct(int buyerId, int productId) throws SQLException {
        return orderDAO.hasBuyerPurchasedProduct(buyerId, productId);
    }

    public Review getBuyerReview(int buyerId, int productId) throws SQLException {
        return reviewDAO.findByBuyerAndProduct(buyerId, productId);
    }

    public List<Review> getReviewsForProduct(int productId) throws SQLException {
        return reviewDAO.findByProductId(productId);
    }

    public double getAverageRating(int productId) throws SQLException {
        return reviewDAO.getAverageRating(productId);
    }

    public int getReviewCount(int productId) throws SQLException {
        return reviewDAO.getReviewCount(productId);
    }
}

