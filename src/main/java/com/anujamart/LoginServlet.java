package com.anujamart;

import com.anujamart.model.User;
import com.anujamart.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userService.authenticate(email, password);

            if (user != null) {
                // Invalidate any existing session to prevent session fixation attacks
                HttpSession oldSession = request.getSession(false);
                if (oldSession != null) {
                    oldSession.invalidate();
                }

                // Create fresh new session
                HttpSession session = request.getSession(true);
                session.setMaxInactiveInterval(30 * 60); // 30 minutes timeout
                session.setAttribute("currentUser", user);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getName());
                session.setAttribute("userEmail", user.getEmail());
                session.setAttribute("userRole", user.getRole());

                // Route by role
                String role = user.getRole();
                if ("SELLER".equalsIgnoreCase(role)) {
                    response.sendRedirect(request.getContextPath() + "/seller.html");
                } else if ("ADMIN".equalsIgnoreCase(role)) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                } else {
                    response.sendRedirect(request.getContextPath() + "/products");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/login.html?error=invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/login.html?error=db_error");
        }
    }
}