package com.anujamart;

import com.anujamart.model.Order;
import com.anujamart.model.Product;
import com.anujamart.model.User;
import com.anujamart.service.OrderService;
import com.anujamart.service.ProductService;
import com.anujamart.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private UserService userService;
    private ProductService productService;
    private OrderService orderService;

    @Override
    public void init() {
        this.userService = new UserService();
        this.productService = new ProductService();
        this.orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/login.html?error=Admin+access+required");
            return;
        }

        try {
            List<User> users = userService.getAllUsers();
            List<Product> products = productService.getAllProducts();
            List<Order> orders = orderService.getAllOrders();

            request.setAttribute("users", users);
            request.setAttribute("products", products);
            request.setAttribute("orders", orders);

            request.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Admin error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("updateOrderStatus".equalsIgnoreCase(action)) {
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                String status = request.getParameter("status");
                orderService.updateOrderStatus(orderId, status);
            } else if ("deleteProduct".equalsIgnoreCase(action)) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                productService.deleteProduct(productId, null, true);
            }

            response.sendRedirect(request.getContextPath() + "/admin?message=success");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin?error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
