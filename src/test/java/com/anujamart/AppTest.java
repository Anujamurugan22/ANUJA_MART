package com.anujamart;

import com.anujamart.model.CartItem;
import com.anujamart.model.Order;
import com.anujamart.model.OrderItem;
import com.anujamart.model.Product;
import com.anujamart.model.User;
import com.anujamart.service.CartService;
import com.anujamart.service.OrderService;
import com.anujamart.service.ProductService;
import com.anujamart.service.UserService;
import com.anujamart.util.PasswordUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @BeforeAll
    public static void setup() {
        // Initialize DB connection and schema
        DBConnection.initDatabase();
    }

    @Test
    public void testPasswordHashing() {
        String raw = "securePass123";
        String hash = PasswordUtil.hashPassword(raw);
        assertNotNull(hash);
        assertTrue(PasswordUtil.checkPassword(raw, hash));
        assertFalse(PasswordUtil.checkPassword("wrongPass", hash));
    }

    @Test
    public void testDatabaseConnection() throws Exception {
        try (Connection con = DBConnection.getConnection()) {
            assertNotNull(con);
            assertFalse(con.isClosed());
        }
    }

    @Test
    public void testUserRegistrationAndAuth() throws Exception {
        UserService userService = new UserService();

        // Test Seed Admin
        User admin = userService.authenticate("admin@anujamart.com", "admin123");
        assertNotNull(admin, "Admin should authenticate");
        assertEquals("ADMIN", admin.getRole());

        // Register new test buyer
        String email = "testbuyer" + System.currentTimeMillis() + "@test.com";
        User buyer = userService.registerUser("Test Buyer", email, "secretPass", "BUYER");
        assertNotNull(buyer);
        assertTrue(buyer.getId() > 0);

        // Authenticate new buyer
        User authenticated = userService.authenticate(email, "secretPass");
        assertNotNull(authenticated);
        assertEquals("BUYER", authenticated.getRole());

        // Invalid password test
        User failed = userService.authenticate(email, "wrongPassword");
        assertNull(failed);
    }

    @Test
    public void testProductAndCartAndOrderFlow() throws Exception {
        UserService userService = new UserService();
        ProductService productService = new ProductService();
        CartService cartService = new CartService();
        OrderService orderService = new OrderService();

        // 1. Authenticate Seller
        User seller = userService.authenticate("seller@anujamart.com", "seller123");
        assertNotNull(seller);

        // 2. Seller adds product
        String prodName = "Test Wireless Mouse " + System.currentTimeMillis();
        Product prod = productService.addProduct(seller.getId(), prodName, "Electronics", 499.00, 20, "Test optical mouse", "");
        assertNotNull(prod);
        assertTrue(prod.getId() > 0);

        // 3. Search and filter product
        List<Product> searchResults = productService.searchAndFilter("Wireless Mouse", "Electronics");
        assertFalse(searchResults.isEmpty());

        // 4. Authenticate Buyer
        User buyer = userService.authenticate("buyer@anujamart.com", "buyer123");
        assertNotNull(buyer);

        // 5. Buyer adds to cart
        cartService.addToCart(buyer.getId(), prod.getId(), 2);
        List<CartItem> cartItems = cartService.getCart(buyer.getId());
        assertFalse(cartItems.isEmpty());
        double total = cartService.getCartTotal(buyer.getId());
        assertTrue(total >= 998.00);

        // 6. Checkout and create order
        int orderId = orderService.checkout(buyer.getId(), "123 Anna Nagar, Chennai", "MOCK_UPI");
        assertTrue(orderId > 0);

        // 7. Verify order created
        Order order = orderService.getOrderById(orderId);
        assertNotNull(order);
        assertEquals("CONFIRMED", order.getStatus());
        assertEquals("123 Anna Nagar, Chennai", order.getShippingAddress());

        // 8. Verify buyer order history
        List<Order> buyerOrders = orderService.getBuyerOrders(buyer.getId());
        assertFalse(buyerOrders.isEmpty());

        // 9. Verify product stock was reduced
        Product updatedProd = productService.getProductById(prod.getId());
        assertEquals(18, updatedProd.getQuantity(), "Stock should be reduced from 20 to 18");

        // 10. Verify seller incoming orders
        List<OrderItem> sellerOrders = orderService.getSellerIncomingOrders(seller.getId());
        assertFalse(sellerOrders.isEmpty(), "Seller should see incoming order item");
        boolean foundSellerItem = false;
        for (OrderItem oi : sellerOrders) {
            if (oi.getProductId() == prod.getId()) {
                foundSellerItem = true;
                assertEquals(2, oi.getQuantity());
            }
        }
        assertTrue(foundSellerItem, "Incoming order should contain the purchased product");

        // 11. Admin verification & status update
        List<Order> allOrders = orderService.getAllOrders();
        assertFalse(allOrders.isEmpty(), "Admin should be able to view all orders");

        boolean statusUpdated = orderService.updateOrderStatus(orderId, "SHIPPED");
        assertTrue(statusUpdated);
        Order updatedOrder = orderService.getOrderById(orderId);
        assertEquals("SHIPPED", updatedOrder.getStatus());

        // 12. Product update and delete
        boolean prodUpdated = productService.updateProduct(prod.getId(), seller.getId(), prodName + " Updated", "Electronics", 599.00, 15, "Updated description", "");
        assertTrue(prodUpdated);
        Product checkedProd = productService.getProductById(prod.getId());
        assertEquals(599.00, checkedProd.getPrice());
        assertEquals(15, checkedProd.getQuantity());

        // 13. Cart update and remove
        cartService.addToCart(buyer.getId(), prod.getId(), 1);
        List<CartItem> newCart = cartService.getCart(buyer.getId());
        assertEquals(1, newCart.size());
        int cartItemId = newCart.get(0).getId();

        cartService.updateQuantity(cartItemId, buyer.getId(), 3);
        List<CartItem> updatedCart = cartService.getCart(buyer.getId());
        assertEquals(3, updatedCart.get(0).getQuantity());

        cartService.removeFromCart(cartItemId, buyer.getId());
        List<CartItem> emptyCart = cartService.getCart(buyer.getId());
        assertTrue(emptyCart.isEmpty());
    }
}
