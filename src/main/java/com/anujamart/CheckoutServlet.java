package com.anujamart;

import com.anujamart.model.CartItem;
import com.anujamart.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() {
        this.cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.html?error=Please+login+to+checkout");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        try {
            List<CartItem> items = cartService.getCart(userId);
            if (items.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            double total = cartService.getCartTotal(userId);

            request.setAttribute("cartItems", items);
            request.setAttribute("cartTotal", total);

            request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Checkout error: " + e.getMessage());
        }
    }
}
