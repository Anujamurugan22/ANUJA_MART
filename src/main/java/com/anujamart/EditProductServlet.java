package com.anujamart;

import com.anujamart.model.Product;
import com.anujamart.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/edit-product")
public class EditProductServlet extends HttpServlet {

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
            response.sendRedirect(request.getContextPath() + "/products?view=seller");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            Product product = productService.getProductById(id);
            if (product == null) {
                response.sendRedirect(request.getContextPath() + "/products?view=seller&error=Product+not+found");
                return;
            }

            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/views/edit-product.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/products?view=seller&error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
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
            int id = Integer.parseInt(idStr);
            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);

            productService.updateProduct(id, sellerId, name, category, price, quantity, description, imageUrl);

            response.sendRedirect(request.getContextPath() + "/products?view=seller&message=product_updated");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/edit-product?id=" + idStr + "&error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
