package com.anujamart.service;

import com.anujamart.dao.UserDAO;
import com.anujamart.model.User;
import com.anujamart.util.PasswordUtil;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User registerUser(String name, String email, String password, String role) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("A valid email address is required");
        }
        if (password == null || password.trim().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters");
        }
        if (role == null || (!role.equalsIgnoreCase("BUYER") && !role.equalsIgnoreCase("SELLER") && !role.equalsIgnoreCase("ADMIN"))) {
            throw new IllegalArgumentException("Invalid role specified");
        }

        String normalizedEmail = email.trim().toLowerCase();
        if (userDAO.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("Email already registered: " + normalizedEmail);
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        User user = new User(name.trim(), normalizedEmail, hashedPassword, role.toUpperCase());
        boolean created = userDAO.create(user);
        if (!created) {
            throw new SQLException("Failed to create user in database");
        }
        return user;
    }

    public User authenticate(String email, String password) throws Exception {
        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            return null;
        }

        User user = userDAO.findByEmail(email.trim().toLowerCase());
        if (user == null) {
            return null;
        }

        if (PasswordUtil.checkPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User getUserById(int id) throws SQLException {
        return userDAO.findById(id);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDAO.findAll();
    }
}
