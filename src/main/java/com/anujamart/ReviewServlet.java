package com.anujamart;

import com.anujamart.model.Review;
import com.anujamart.service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/reviews")
public class ReviewServlet extends HttpServlet {

    private ReviewService reviewService;

    @Override
    public void init() {
        this.reviewService = new ReviewService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String prodIdStr = request.getParameter("productId");
        if (prodIdStr == null || prodIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        try {
            int productId = Integer.parseInt(prodIdStr);
            List<Review> reviews = reviewService.getReviewsForProduct(productId);
            double avgRating = reviewService.getAverageRating(productId);
            int count = reviewService.getReviewCount(productId);

            HttpSession session = request.getSession(false);
            boolean isLoggedIn = (session != null && session.getAttribute("userId") != null);
            boolean canReview = false;
            Review userReview = null;

            if (isLoggedIn) {
                int userId = (Integer) session.getAttribute("userId");
                canReview = reviewService.hasBuyerPurchasedProduct(userId, productId);
                if (canReview) {
                    userReview = reviewService.getBuyerReview(userId, productId);
                }
            }

            // Return JSON for AJAX requests
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("\"productId\":").append(productId).append(",");
            json.append("\"avgRating\":").append(avgRating).append(",");
            json.append("\"count\":").append(count).append(",");
            json.append("\"isLoggedIn\":").append(isLoggedIn).append(",");
            json.append("\"canReview\":").append(canReview).append(",");
            if (userReview != null) {
                json.append("\"userReview\":{");
                json.append("\"rating\":").append(userReview.getRating()).append(",");
                json.append("\"comment\":\"").append(escapeJson(userReview.getComment())).append("\"");
                json.append("},");
            } else {
                json.append("\"userReview\":null,");
            }
            json.append("\"reviews\":[");
            for (int i = 0; i < reviews.size(); i++) {
                Review r = reviews.get(i);
                if (i > 0) json.append(",");
                json.append("{");
                json.append("\"id\":").append(r.getId()).append(",");
                json.append("\"buyerName\":\"").append(escapeJson(r.getBuyerName())).append("\",");
                json.append("\"rating\":").append(r.getRating()).append(",");
                json.append("\"comment\":\"").append(escapeJson(r.getComment())).append("\",");
                json.append("\"date\":\"").append(r.getReviewDate() != null ? r.getReviewDate().toString() : "").append("\"");
                json.append("}");
            }
            json.append("]}");
            out.print(json.toString());
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.html?error=" + java.net.URLEncoder.encode("Please login to submit a review", "UTF-8"));
            return;
        }

        int buyerId = (Integer) session.getAttribute("userId");
        String prodIdStr = request.getParameter("productId");
        String ratingStr = request.getParameter("rating");
        String comment = request.getParameter("comment");
        String source = request.getParameter("source");

        try {
            int productId = Integer.parseInt(prodIdStr);
            int rating = Integer.parseInt(ratingStr);

            reviewService.addReview(buyerId, productId, rating, comment);

            if ("orders".equalsIgnoreCase(source)) {
                response.sendRedirect(request.getContextPath() + "/orders?message=review_success");
            } else {
                response.sendRedirect(request.getContextPath() + "/products?message=review_success#prod-" + productId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = e.getMessage() != null ? e.getMessage() : "Failed to submit review";
            if ("orders".equalsIgnoreCase(source)) {
                response.sendRedirect(request.getContextPath() + "/orders?error=" + java.net.URLEncoder.encode(errorMsg, "UTF-8"));
            } else {
                response.sendRedirect(request.getContextPath() + "/products?error=" + java.net.URLEncoder.encode(errorMsg, "UTF-8"));
            }
        }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
