package com.anujamart;

import com.anujamart.util.PasswordUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static HikariDataSource dataSource;
    private static volatile boolean initialized = false;

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName("org.h2.Driver");
            // Persistent H2 database with AUTO_SERVER mode to support multi-process access and persistence across restarts
            config.setJdbcUrl("jdbc:h2:~/anujamart;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE;NON_KEYWORDS=USER,VALUE");
            config.setUsername("sa");
            config.setPassword("");
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(10000);

            dataSource = new HikariDataSource(config);
            initDatabase();
        } catch (Exception e) {
            System.err.println("Failed to initialize HikariCP DataSource: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource is not initialized");
        }
        return dataSource.getConnection();
    }

    public static synchronized void initDatabase() {
        if (initialized) {
            return;
        }
        try (Connection con = dataSource.getConnection()) {
            executeSqlScript(con, "/schema.sql");
            seedInitialData(con);
            initialized = true;
            System.out.println("ANUJA Mart H2 database successfully initialized.");
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void executeSqlScript(Connection con, String resourcePath) {
        try (InputStream in = DBConnection.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                System.err.println("SQL resource not found: " + resourcePath);
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    String trimmed = line.trim();
                    if (trimmed.startsWith("--") || trimmed.isEmpty()) {
                        continue;
                    }
                    sb.append(line).append("\n");
                    if (trimmed.endsWith(";")) {
                        try (Statement stmt = con.createStatement()) {
                            stmt.execute(sb.toString());
                        }
                        sb.setLength(0);
                    }
                }
                if (sb.length() > 0 && !sb.toString().trim().isEmpty()) {
                    try (Statement stmt = con.createStatement()) {
                        stmt.execute(sb.toString());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to execute SQL script " + resourcePath + ": " + e.getMessage());
        }
    }

    private static void seedInitialData(Connection con) {
        try {
            // Check if users already exist
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users")) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return; // Already seeded
                }
            }

            // Seed Admin
            String userSql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                // 1. Admin
                ps.setString(1, "Admin User");
                ps.setString(2, "admin@anujamart.com");
                ps.setString(3, PasswordUtil.hashPassword("admin123"));
                ps.setString(4, "ADMIN");
                ps.executeUpdate();

                // 2. Seller
                ps.setString(1, "Anuja Seller");
                ps.setString(2, "seller@anujamart.com");
                ps.setString(3, PasswordUtil.hashPassword("seller123"));
                ps.setString(4, "SELLER");
                ps.executeUpdate();
                int sellerId = 2;
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        sellerId = rs.getInt(1);
                    }
                }

                // 3. Buyer
                ps.setString(1, "Priya Buyer");
                ps.setString(2, "buyer@anujamart.com");
                ps.setString(3, PasswordUtil.hashPassword("buyer123"));
                ps.setString(4, "BUYER");
                ps.executeUpdate();

                // Seed Initial Products
                String prodSql = "INSERT INTO products (seller_id, name, category, price, quantity, description, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement psProd = con.prepareStatement(prodSql)) {
                    Object[][] demoProducts = new Object[][]{
                        {sellerId, "Wireless Noise-Canceling Headphones", "Electronics", 2999.00, 45, "Over-ear Bluetooth headphones with active noise cancellation and 30-hour battery life.", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500"},
                        {sellerId, "Smart Fitness Watch", "Electronics", 1999.00, 30, "Waterproof fitness tracker with heart rate monitor, sleep tracking, and color display.", "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500"},
                        {sellerId, "Men's Premium Cotton Shirt", "Fashion", 1299.00, 50, "Breathable 100% organic cotton slim-fit casual shirt suitable for all occasions.", "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=500"},
                        {sellerId, "Women's Floral Summer Dress", "Fashion", 1899.00, 25, "Lightweight, elegant floral print maxi dress crafted from soft, flowing fabric.", "https://images.unsplash.com/photo-1572804013309-59a88b7e92f1?w=500"},
                        {sellerId, "Organic Aloe Vera Face Cream", "Beauty", 499.00, 80, "Daily hydrating face cream with natural aloe vera extract and Vitamin E.", "https://images.unsplash.com/photo-1556228720-195a672e8a03?w=500"},
                        {sellerId, "Pure Organic Green Tea (100 Bags)", "Grocery", 349.00, 100, "Whole leaf green tea rich in natural antioxidants for a refreshing morning brew.", "https://images.unsplash.com/photo-1564890369478-c89ca6d9cde9?w=500"},
                        {sellerId, "Stainless Steel Thermal Bottle (1L)", "Home", 799.00, 60, "Double-wall vacuum insulated water bottle keeping beverages cold for 24h or hot for 12h.", "https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=500"}
                    };

                    for (Object[] p : demoProducts) {
                        psProd.setInt(1, (Integer) p[0]);
                        psProd.setString(2, (String) p[1]);
                        psProd.setString(3, (String) p[2]);
                        psProd.setDouble(4, (Double) p[3]);
                        psProd.setInt(5, (Integer) p[4]);
                        psProd.setString(6, (String) p[5]);
                        psProd.setString(7, (String) p[6]);
                        psProd.addBatch();
                    }
                    psProd.executeBatch();
                }
            }
            System.out.println("ANUJA Mart demo seed data successfully populated.");
        } catch (Exception e) {
            System.err.println("Failed to seed initial data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}