package com.anujamart;

import com.anujamart.model.Order;
import com.anujamart.model.OrderItem;
import com.anujamart.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    private OrderService orderService;

    @Override
    public void init() {
        this.orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.html?error=Please+login+to+view+orders");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("userRole");

        try {
            if ("SELLER".equalsIgnoreCase(role)) {
                // Incoming orders for seller's products
                List<OrderItem> incomingItems = orderService.getSellerIncomingOrders(userId);
                request.setAttribute("isSeller", true);
                request.setAttribute("incomingItems", incomingItems);
            } else {
                // Buyer's order history
                List<Order> buyerOrders = orderService.getBuyerOrders(userId);
                request.setAttribute("isSeller", false);
                request.setAttribute("buyerOrders", buyerOrders);
            }

            request.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to load orders: " + e.getMessage());
        }
    }
}
