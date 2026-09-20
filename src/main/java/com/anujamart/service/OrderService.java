package com.anujamart.service;

import com.anujamart.dao.CartDAO;
import com.anujamart.dao.OrderDAO;
import com.anujamart.dao.ProductDAO;
import com.anujamart.model.CartItem;
import com.anujamart.model.Order;
import com.anujamart.model.OrderItem;
import com.anujamart.model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private final OrderDAO orderDAO;
    private final CartDAO cartDAO;
    private final ProductDAO productDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.cartDAO = new CartDAO();
        this.productDAO = new ProductDAO();
    }

    public OrderService(OrderDAO orderDAO, CartDAO cartDAO, ProductDAO productDAO) {
        this.orderDAO = orderDAO;
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
    }

    public int checkout(int buyerId, String shippingAddress, String paymentMethod) throws Exception {
        if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Shipping address is required");
        }
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            paymentMethod = "MOCK_PAYMENT";
        }

        List<CartItem> cartItems = cartDAO.getCartItems(buyerId);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cannot checkout with an empty cart");
        }

        double totalAmount = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem ci : cartItems) {
            Product p = productDAO.findById(ci.getProductId());
            if (p == null) {
                throw new IllegalStateException("Product not found: ID " + ci.getProductId());
            }
            if (p.getQuantity() < ci.getQuantity()) {
                throw new IllegalStateException("Insufficient stock for product '" + p.getName() + "'. Available: " + p.getQuantity());
            }

            double subtotal = p.getPrice() * ci.getQuantity();
            totalAmount += subtotal;

            OrderItem oi = new OrderItem(
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    ci.getQuantity(),
                    p.getSellerId()
            );
            orderItems.add(oi);
        }

        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setTotalAmount(totalAmount);
        order.setStatus("CONFIRMED");
        order.setShippingAddress(shippingAddress.trim());
        order.setPaymentMethod(paymentMethod.trim());

        int orderId = orderDAO.createOrder(order, orderItems);

        // Clear cart after successful order creation
        cartDAO.clearCart(buyerId);

        return orderId;
    }

    public Order getOrderById(int orderId) throws SQLException {
        return orderDAO.findById(orderId);
    }

    public List<Order> getBuyerOrders(int buyerId) throws SQLException {
        return orderDAO.findByBuyerId(buyerId);
    }

    public List<OrderItem> getSellerIncomingOrders(int sellerId) throws SQLException {
        return orderDAO.findIncomingItemsBySellerId(sellerId);
    }

    public List<Order> getAllOrders() throws SQLException {
        return orderDAO.findAll();
    }

    public boolean updateOrderStatus(int orderId, String status) throws SQLException {
        return orderDAO.updateStatus(orderId, status);
    }
}
