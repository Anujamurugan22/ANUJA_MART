package com.anujamart.model;

import java.sql.Timestamp;

public class Review {
    private int id;
    private Integer buyerId;
    private String buyerName;
    private int productId;
    private int rating;
    private String comment;
    private Timestamp reviewDate;

    public Review() {
    }

    public Review(int id, Integer buyerId, String buyerName, int productId, int rating, String comment, Timestamp reviewDate) {
        this.id = id;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public Review(Integer buyerId, int productId, int rating, String comment) {
        this.buyerId = buyerId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Timestamp reviewDate) {
        this.reviewDate = reviewDate;
    }
}
