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
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            String host = getEnvOrProperty("DB_HOST", "MYSQLHOST", "MYSQL_HOST");
            String port = getEnvOrProperty("DB_PORT", "MYSQLPORT", "MYSQL_PORT");
            String dbName = getEnvOrProperty("DB_NAME", "MYSQLDATABASE", "MYSQL_DATABASE");
            String username = getEnvOrProperty("DB_USER", "MYSQLUSER", "MYSQL_USER");
            String password = getEnvOrProperty("DB_PASSWORD", "MYSQLPASSWORD", "MYSQL_PASSWORD");
            String rawUrl = getEnvOrProperty("DB_URL", "MYSQL_URL", "db.url");

            String jdbcUrl;

            if (rawUrl != null && !rawUrl.trim().isEmpty()) {
                jdbcUrl = rawUrl.trim();
                if (jdbcUrl.startsWith("mysql://")) {
                    jdbcUrl = "jdbc:" + jdbcUrl;
                }
                if (!jdbcUrl.contains("?")) {
                    jdbcUrl += "?useSSL=true&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                }
            } else if (host != null && !host.trim().isEmpty()) {
                String p = (port != null && !port.trim().isEmpty()) ? port.trim() : "3306";
                String d = (dbName != null && !dbName.trim().isEmpty()) ? dbName.trim() : "anujamart";
                jdbcUrl = String.format("jdbc:mysql://%s:%s/%s?useSSL=true&allowPublicKeyRetrieval=true&serverTimezone=UTC", host.trim(), p, d);
            } else {
                // Safe Local Development Fallback
                jdbcUrl = System.getProperty("db.url", "jdbc:mysql://127.0.0.1:3306/anujamart?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata");
                if (username == null || username.trim().isEmpty()) {
                    username = System.getProperty("db.user", "root");
                }
                if (password == null || password.trim().isEmpty()) {
                    password = System.getProperty("db.password", "2008");
                }
            }

            if (username == null || username.trim().isEmpty()) {
                username = "root";
            }
            if (password == null) {
                password = "";
            }

            config.setJdbcUrl(jdbcUrl);
            config.setUsername(username);
            config.setPassword(password);
            
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(10000);

            dataSource = new HikariDataSource(config);
            System.out.println("HikariCP connection pool initialized for: " + jdbcUrl.replaceAll(":[^/@:]+@", ":***@"));
        } catch (Exception e) {
            System.err.println("Failed to initialize HikariCP DataSource: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String getEnvOrProperty(String... keys) {
        for (String key : keys) {
            String val = System.getenv(key);
            if (val != null && !val.trim().isEmpty()) {
                return val.trim();
            }
            val = System.getProperty(key);
            if (val != null && !val.trim().isEmpty()) {
                return val.trim();
            }
        }
        return null;
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
        if (dataSource == null) {
            System.err.println("Cannot initialize database: DataSource is not initialized.");
            return;
        }
        try (Connection con = dataSource.getConnection()) {
            executeSqlScript(con, "/schema.sql");
            seedInitialData(con);
            initialized = true;
            System.out.println("ANUJA Mart MySQL database successfully initialized.");
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
                        } catch (Exception ex) {
                            // Ignored if table already exists or constraint already present
                        }
                        sb.setLength(0);
                    }
                }
                if (sb.length() > 0 && !sb.toString().trim().isEmpty()) {
                    try (Statement stmt = con.createStatement()) {
                        stmt.execute(sb.toString());
                    } catch (Exception ex) {
                        // Ignored
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to execute SQL script " + resourcePath + ": " + e.getMessage());
        }
    }

    private static void seedInitialData(Connection con) {
        try {
            // Ensure demo users exist individually
            ensureUser(con, "Admin User", "admin@anujamart.com", "admin123", "ADMIN");
            int sellerId = ensureUser(con, "Anuja Seller", "seller@anujamart.com", "seller123", "SELLER");
            ensureUser(con, "Priya Buyer", "buyer@anujamart.com", "buyer123", "BUYER");

            // Seed full 100-product catalog idempotently
            com.anujamart.util.ProductCatalogSeed.seedProducts(con, sellerId);
            System.out.println("ANUJA Mart demo seed data successfully populated.");
        } catch (Exception e) {
            System.err.println("Failed to seed initial data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static int ensureUser(Connection con, String name, String email, String password, String role) throws SQLException {
        String checkSql = "SELECT id, password FROM users WHERE email = ?";
        try (PreparedStatement psCheck = con.prepareStatement(checkSql)) {
            psCheck.setString(1, email);
            try (ResultSet rs = psCheck.executeQuery()) {
                if (rs.next()) {
                    int existingId = rs.getInt("id");
                    String existingPass = rs.getString("password");
                    // If password is not hashed with bcrypt, upgrade it
                    if (existingPass != null && !existingPass.startsWith("$2a$") && !existingPass.startsWith("$2b$") && !existingPass.startsWith("$2y$")) {
                        try (PreparedStatement psUp = con.prepareStatement("UPDATE users SET password = ? WHERE id = ?")) {
                            psUp.setString(1, PasswordUtil.hashPassword(password));
                            psUp.setInt(2, existingId);
                            psUp.executeUpdate();
                        }
                    }
                    return existingId;
                }
            }
        }

        String insertSql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, PasswordUtil.hashPassword(password));
            ps.setString(4, role);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 1;
    }

    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}