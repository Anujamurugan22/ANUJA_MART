package com.anujamart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/api/v1/health")
public class HealthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String dbStatus = "DOWN";
        try (Connection con = DBConnection.getConnection()) {
            if (con != null && !con.isClosed()) {
                dbStatus = "UP";
            }
        } catch (Exception e) {
            dbStatus = "DOWN";
        }

        PrintWriter out = response.getWriter();
        out.print("{\"status\":\"UP\",\"db\":\"" + dbStatus + "\"}");
        out.flush();
    }
}
