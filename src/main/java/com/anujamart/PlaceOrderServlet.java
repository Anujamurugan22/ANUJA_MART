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
            response.sendRedirect(request.getContextPath() + "/login.html?error=Please+login+to+place+order");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        // Parse individual delivery address fields
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String houseNo = request.getParameter("houseNo");
        String streetArea = request.getParameter("streetArea");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String pincode = request.getParameter("pincode");
        String directAddress = request.getParameter("shippingAddress");

        String shippingAddress = null;

        // If composite form fields were submitted, validate all required fields
        if (fullName != null || houseNo != null || streetArea != null || city != null || state != null || pincode != null) {
            if (fullName == null || fullName.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty() ||
                houseNo == null || houseNo.trim().isEmpty() ||
                streetArea == null || streetArea.trim().isEmpty() ||
                city == null || city.trim().isEmpty() ||
                state == null || state.trim().isEmpty() ||
                pincode == null || pincode.trim().isEmpty()) {

                response.sendRedirect(request.getContextPath() + "/checkout?error=" +
                        java.net.URLEncoder.encode("All address fields (Full Name, Phone Number, House/Door Number, Street/Area, City, State, Pincode) are required.", "UTF-8"));
                return;
            }

            shippingAddress = fullName.trim() + " (Phone: " + phone.trim() + "), " +
                              houseNo.trim() + ", " + streetArea.trim() + ", " +
                              city.trim() + ", " + state.trim() + " - " + pincode.trim();
        } else if (directAddress != null && !directAddress.trim().isEmpty()) {
            shippingAddress = directAddress.trim();
        }

        if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/checkout?error=" +
                    java.net.URLEncoder.encode("Delivery address is required.", "UTF-8"));
            return;
        }

        String paymentMethod = request.getParameter("paymentMethod");
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            paymentMethod = "Cash on Delivery";
        }

        try {
            int orderId = orderService.checkout(userId, shippingAddress, paymentMethod);
            // Redirect to Order Success Page
            response.sendRedirect(request.getContextPath() + "/order-success?orderId=" + orderId);
        } catch (Exception e) {
            System.err.println("Failed to place order for user " + userId + ": " + e.getMessage());
            e.printStackTrace();
            String errorMsg = e.getMessage() != null ? e.getMessage() : "Failed to place order";
            response.sendRedirect(request.getContextPath() + "/checkout?error=" + java.net.URLEncoder.encode(errorMsg, "UTF-8"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/checkout");
    }
}
