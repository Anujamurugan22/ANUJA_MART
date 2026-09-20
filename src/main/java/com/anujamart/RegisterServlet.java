package com.anujamart;

import com.anujamart.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        try {
            userService.registerUser(name, email, password, role);
            // Redirect to login page with success notification
            response.sendRedirect(request.getContextPath() + "/login.html?registered=true");
        } catch (Exception e) {
            String errorMsg = e.getMessage() != null ? e.getMessage() : "Registration failed";
            response.sendRedirect(request.getContextPath() + "/register.html?error=" + java.net.URLEncoder.encode(errorMsg, "UTF-8"));
        }
    }
}