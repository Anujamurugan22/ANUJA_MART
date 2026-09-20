package com.anujamart;

import com.anujamart.model.Product;
import com.anujamart.service.CartService;
import com.anujamart.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {

    private ProductService productService;
    private CartService cartService;

    @Override
    public void init() {
        this.productService = new ProductService();
        this.cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String search = request.getParameter("search");
        String category = request.getParameter("category");
        String viewMode = request.getParameter("view");

        HttpSession session = request.getSession(false);
        String userRole = null;
        Integer userId = null;
        int cartCount = 0;

        if (session != null) {
            userRole = (String) session.getAttribute("userRole");
            userId = (Integer) session.getAttribute("userId");
            if (userId != null && "BUYER".equalsIgnoreCase(userRole)) {
                try {
                    cartCount = cartService.getCartCount(userId);
                } catch (Exception e) {
                    // Ignore cart count failure
                }
            }
        }

        try {
            List<Product> products;
            // If explicitly requested seller view or logged in as seller viewing their own products
            if ("seller".equalsIgnoreCase(viewMode) || ("SELLER".equalsIgnoreCase(userRole) && !"buyer".equalsIgnoreCase(viewMode))) {
                if (userId != null) {
                    products = productService.getProductsBySeller(userId);
                } else {
                    products = productService.getAllProducts();
                }
                request.setAttribute("isSellerView", true);
            } else {
                products = productService.searchAndFilter(search, category);
                request.setAttribute("isSellerView", false);
            }

            List<String> categories = productService.getAllCategories();

            request.setAttribute("products", products);
            request.setAttribute("categories", categories);
            request.setAttribute("selectedCategory", category != null ? category : "All");
            request.setAttribute("searchQuery", search != null ? search : "");
            request.setAttribute("cartCount", cartCount);

            request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to load products: " + e.getMessage());
        }
    }
}