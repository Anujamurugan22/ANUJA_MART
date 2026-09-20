package com.anujamart;

import com.anujamart.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/add-product")
public class ProductServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() {
        this.productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/add-product.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String priceStr = request.getParameter("price");
        String quantityStr = request.getParameter("quantity");
        String description = request.getParameter("description");
        String imageUrl = request.getParameter("imageUrl");

        HttpSession session = request.getSession(false);
        Integer sellerId = null;
        if (session != null && session.getAttribute("userId") != null) {
            sellerId = (Integer) session.getAttribute("userId");
        }

        try {
            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);

            productService.addProduct(sellerId, name, category, price, quantity, description, imageUrl);

            // Redirect back to seller dashboard / products page with success notification
            response.sendRedirect(request.getContextPath() + "/products?message=product_added");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/add-product.html?error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}