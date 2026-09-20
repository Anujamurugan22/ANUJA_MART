package com.anujamart.dao;

import com.anujamart.DBConnection;
import com.anujamart.model.Order;
import com.anujamart.model.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public int createOrder(Order order, List<OrderItem> items) throws SQLException {
        String orderSql = "INSERT INTO orders (buyer_id, total_amount, status, shipping_address, payment_method) VALUES (?, ?, ?, ?, ?)";
        String itemSql = "INSERT INTO order_items (order_id, product_id, product_name, unit_price, quantity, seller_id) VALUES (?, ?, ?, ?, ?, ?)";
        String stockSql = "UPDATE products SET quantity = quantity - ? WHERE id = ? AND quantity >= ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                int orderId = 0;
                try (PreparedStatement psOrder = con.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                    psOrder.setInt(1, order.getBuyerId());
                    psOrder.setDouble(2, order.getTotalAmount());
                    psOrder.setString(3, order.getStatus());
                    psOrder.setString(4, order.getShippingAddress());
                    psOrder.setString(5, order.getPaymentMethod());
                    psOrder.executeUpdate();

                    try (ResultSet rs = psOrder.getGeneratedKeys()) {
                        if (rs.next()) {
                            orderId = rs.getInt(1);
                            order.setId(orderId);
                        } else {
                            throw new SQLException("Failed to retrieve generated order ID");
                        }
                    }
                }

                try (PreparedStatement psItem = con.prepareStatement(itemSql);
                     PreparedStatement psStock = con.prepareStatement(stockSql)) {

                    for (OrderItem item : items) {
                        // Insert Order Item
                        psItem.setInt(1, orderId);
                        psItem.setInt(2, item.getProductId());
                        psItem.setString(3, item.getProductName());
                        psItem.setDouble(4, item.getUnitPrice());
                        psItem.setInt(5, item.getQuantity());
                        if (item.getSellerId() != null) {
                            psItem.setInt(6, item.getSellerId());
                        } else {
                            psItem.setNull(6, java.sql.Types.INTEGER);
                        }
                        psItem.addBatch();

                        // Deduct product stock
                        psStock.setInt(1, item.getQuantity());
                        psStock.setInt(2, item.getProductId());
                        psStock.setInt(3, item.getQuantity());
                        int updated = psStock.executeUpdate();
                        if (updated == 0) {
                            throw new SQLException("Insufficient stock for product ID: " + item.getProductId());
                        }
                    }
                    psItem.executeBatch();
                }

                con.commit();
                return orderId;
            } catch (Exception e) {
                con.rollback();
                throw new SQLException("Transaction rolled back: " + e.getMessage(), e);
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    public Order findById(int orderId) throws SQLException {
        String sql = "SELECT o.id, o.buyer_id, o.total_amount, o.status, o.shipping_address, o.payment_method, o.created_at, " +
                     "u.name AS buyer_name, u.email AS buyer_email " +
                     "FROM orders o " +
                     "LEFT JOIN users u ON o.buyer_id = u.id " +
                     "WHERE o.id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order order = mapRowToOrder(rs);
                    order.setItems(getOrderItems(con, orderId));
                    return order;
                }
            }
        }
        return null;
    }

    public List<Order> findByBuyerId(int buyerId) throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.id, o.buyer_id, o.total_amount, o.status, o.shipping_address, o.payment_method, o.created_at, " +
                     "u.name AS buyer_name, u.email AS buyer_email " +
                     "FROM orders o " +
                     "LEFT JOIN users u ON o.buyer_id = u.id " +
                     "WHERE o.buyer_id = ? " +
                     "ORDER BY o.id DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, buyerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = mapRowToOrder(rs);
                    order.setItems(getOrderItems(con, order.getId()));
                    list.add(order);
                }
            }
        }
        return list;
    }

    public List<OrderItem> findIncomingItemsBySellerId(int sellerId) throws SQLException {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT oi.id, oi.order_id, oi.product_id, oi.product_name, oi.unit_price, oi.quantity, oi.seller_id, oi.created_at " +
                     "FROM order_items oi " +
                     "WHERE oi.seller_id = ? " +
                     "ORDER BY oi.id DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, sellerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToOrderItem(rs));
                }
            }
        }
        return list;
    }

    public List<Order> findAll() throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.id, o.buyer_id, o.total_amount, o.status, o.shipping_address, o.payment_method, o.created_at, " +
                     "u.name AS buyer_name, u.email AS buyer_email " +
                     "FROM orders o " +
                     "LEFT JOIN users u ON o.buyer_id = u.id " +
                     "ORDER BY o.id DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order order = mapRowToOrder(rs);
                order.setItems(getOrderItems(con, order.getId()));
                list.add(order);
            }
        }
        return list;
    }

    public boolean updateStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        }
    }

    private List<OrderItem> getOrderItems(Connection con, int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT id, order_id, product_id, product_name, unit_price, quantity, seller_id, created_at " +
                     "FROM order_items WHERE order_id = ? ORDER BY id ASC";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapRowToOrderItem(rs));
                }
            }
        }
        return items;
    }

    private Order mapRowToOrder(ResultSet rs) throws SQLException {
        Order order = new Order(
                rs.getInt("id"),
                rs.getInt("buyer_id"),
                rs.getDouble("total_amount"),
                rs.getString("status"),
                rs.getString("shipping_address"),
                rs.getString("payment_method"),
                rs.getTimestamp("created_at")
        );
        order.setBuyerName(rs.getString("buyer_name"));
        order.setBuyerEmail(rs.getString("buyer_email"));
        return order;
    }

    private OrderItem mapRowToOrderItem(ResultSet rs) throws SQLException {
        int sellerId = rs.getInt("seller_id");
        Integer sellerIdObj = rs.wasNull() ? null : sellerId;
        return new OrderItem(
                rs.getInt("id"),
                rs.getInt("order_id"),
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getDouble("unit_price"),
                rs.getInt("quantity"),
                sellerIdObj,
                rs.getTimestamp("created_at")
        );
    }
}
