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

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

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
            response.sendRedirect(request.getContextPath() + "/login.html?error=Please+login+to+view+your+cart");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        try {
            List<CartItem> cartItems = cartService.getCart(userId);
            double total = cartService.getCartTotal(userId);

            request.setAttribute("cartItems", cartItems);
            request.setAttribute("cartTotal", total);

            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to load cart: " + e.getMessage());
        }
    }
}
