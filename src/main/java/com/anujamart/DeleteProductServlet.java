package com.anujamart;

import com.anujamart.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/delete-product")
public class DeleteProductServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() {
        this.productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);

            HttpSession session = request.getSession(false);
            Integer sellerId = null;
            boolean isAdmin = false;

            if (session != null) {
                if (session.getAttribute("userId") != null) {
                    sellerId = (Integer) session.getAttribute("userId");
                }
                String role = (String) session.getAttribute("userRole");
                if ("ADMIN".equalsIgnoreCase(role)) {
                    isAdmin = true;
                }
            }

            productService.deleteProduct(id, sellerId, isAdmin);
            response.sendRedirect(request.getContextPath() + "/products?message=deleted");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/products?error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}