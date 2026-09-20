package com.anujamart;

import com.anujamart.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() {
        this.cartService = new CartService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.html?error=Please+login+to+add+items+to+cart");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String productIdStr = request.getParameter("productId");
        String quantityStr = request.getParameter("quantity");

        int quantity = 1;
        if (quantityStr != null && !quantityStr.trim().isEmpty()) {
            try {
                quantity = Integer.parseInt(quantityStr);
            } catch (NumberFormatException ignored) {
            }
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            cartService.addToCart(userId, productId, quantity);
            response.sendRedirect(request.getContextPath() + "/products?message=added_to_cart");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/products?error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
