package com.anujamart;

import com.anujamart.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

    private OrderService orderService;

    @Override
    public void init() {
        this.orderService = new OrderService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String shippingAddress = request.getParameter("shippingAddress");
        String paymentMethod = request.getParameter("paymentMethod");

        try {
            int orderId = orderService.checkout(userId, shippingAddress, paymentMethod);
            response.sendRedirect(request.getContextPath() + "/orders?message=order_placed&orderId=" + orderId);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/checkout?error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/checkout");
    }
}
