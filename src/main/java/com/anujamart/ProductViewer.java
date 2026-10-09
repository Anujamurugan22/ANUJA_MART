package com.anujamart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductViewer {
    public static void main(String[] args) {
        String sql = "SELECT * FROM products";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("========== ANUJA MART PRODUCTS ==========");

            int count = 0;

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Price: Rs. " + rs.getDouble("price"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
                System.out.println("-----------------------------------------");
                count++;
            }

            System.out.println("Total products: " + count);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}