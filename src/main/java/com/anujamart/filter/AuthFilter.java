package com.anujamart.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI().substring(request.getContextPath().length());

        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("userRole") : null;
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        // Admin-only routes
        if (path.startsWith("/admin")) {
            if (userId == null || !"ADMIN".equalsIgnoreCase(role)) {
                response.sendRedirect(request.getContextPath() + "/login.html?error=Admin+access+required");
                return;
            }
        }

        // Seller-only routes
        if (path.startsWith("/seller") || path.startsWith("/add-product") || path.startsWith("/edit-product")) {
            if (userId == null || (!"SELLER".equalsIgnoreCase(role) && !"ADMIN".equalsIgnoreCase(role))) {
                response.sendRedirect(request.getContextPath() + "/login.html?error=Seller+login+required");
                return;
            }
        }

        // Buyer-only protected checkout
        if (path.startsWith("/checkout") || path.startsWith("/place-order")) {
            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login.html?error=Please+login+to+complete+your+order");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
