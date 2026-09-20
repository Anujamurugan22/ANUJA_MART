package com.anujamart;

import com.anujamart.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/update-cart")
public class UpdateCartServlet extends HttpServlet {

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
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String action = request.getParameter("action");
        String cartItemIdStr = request.getParameter("cartItemId");
        String quantityStr = request.getParameter("quantity");

        try {
            int cartItemId = Integer.parseInt(cartItemIdStr);

            if ("remove".equalsIgnoreCase(action)) {
                cartService.removeFromCart(cartItemId, userId);
            } else if ("update".equalsIgnoreCase(action)) {
                int quantity = Integer.parseInt(quantityStr);
                cartService.updateQuantity(cartItemId, userId, quantity);
            }

            response.sendRedirect(request.getContextPath() + "/cart");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/cart?error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
