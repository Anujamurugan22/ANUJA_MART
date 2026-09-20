<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ANUJA MART - Shopping Cart</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Arial, sans-serif;
        }
        body {
            background: #f8f3ff;
            color: #333;
            min-height: 100vh;
        }
        .navbar {
            height: 70px;
            background: #ffffff;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 5%;
            border-bottom: 2px solid #ede4fb;
        }
        .logo {
            display: flex;
            align-items: center;
            gap: 10px;
            text-decoration: none;
            color: #222;
        }
        .logo-icon {
            width: 40px;
            height: 40px;
            background: #6c3fc5;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 20px;
        }
        .logo h1 {
            font-size: 24px;
            font-weight: 700;
        }
        .logo h1 span {
            color: #6c3fc5;
        }
        .nav-links {
            display: flex;
            align-items: center;
            gap: 20px;
        }
        .nav-link {
            text-decoration: none;
            color: #55496b;
            font-weight: 500;
            font-size: 15px;
            padding: 8px 12px;
            border-radius: 6px;
        }
        .nav-link:hover {
            color: #6c3fc5;
            background: #f3ecff;
        }
        .container {
            max-width: 1100px;
            margin: 40px auto;
            padding: 0 20px;
        }
        .cart-layout {
            display: flex;
            gap: 30px;
            align-items: flex-start;
        }
        .cart-items {
            flex: 2;
            background: white;
            border-radius: 14px;
            padding: 25px;
            box-shadow: 0 4px 15px rgba(108, 63, 197, 0.08);
        }
        .cart-summary {
            flex: 1;
            background: white;
            border-radius: 14px;
            padding: 25px;
            box-shadow: 0 4px 15px rgba(108, 63, 197, 0.08);
            position: sticky;
            top: 90px;
        }
        .cart-header {
            font-size: 22px;
            font-weight: 700;
            color: #2e1e4a;
            margin-bottom: 20px;
            border-bottom: 1px solid #f0eaf7;
            padding-bottom: 15px;
        }
        .cart-item {
            display: flex;
            align-items: center;
            gap: 20px;
            padding: 18px 0;
            border-bottom: 1px solid #f3eefa;
        }
        .item-img {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 8px;
            background: #ede4fb;
        }
        .item-details {
            flex: 1;
        }
        .item-name {
            font-weight: 600;
            font-size: 16px;
            color: #222;
            margin-bottom: 4px;
        }
        .item-category {
            font-size: 13px;
            color: #6c3fc5;
            margin-bottom: 6px;
        }
        .item-price {
            font-size: 15px;
            font-weight: 700;
            color: #403653;
        }
        .item-actions {
            display: flex;
            align-items: center;
            gap: 15px;
        }
        .qty-form {
            display: flex;
            align-items: center;
            gap: 6px;
        }
        .qty-input {
            width: 55px;
            padding: 6px;
            border: 1px solid #ddd;
            border-radius: 6px;
            text-align: center;
            font-size: 14px;
        }
        .btn-update {
            background: #6c3fc5;
            color: white;
            border: none;
            padding: 6px 12px;
            border-radius: 6px;
            font-size: 13px;
            cursor: pointer;
        }
        .btn-remove {
            background: #fee;
            color: #cf222e;
            border: 1px solid #ffdcd7;
            padding: 6px 12px;
            border-radius: 6px;
            font-size: 13px;
            cursor: pointer;
            text-decoration: none;
        }
        .btn-remove:hover {
            background: #ffdcd7;
        }
        .item-subtotal {
            font-size: 16px;
            font-weight: 700;
            color: #6c3fc5;
            min-width: 90px;
            text-align: right;
        }
        .summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 12px;
            font-size: 15px;
            color: #555;
        }
        .summary-total {
            display: flex;
            justify-content: space-between;
            margin-top: 18px;
            padding-top: 15px;
            border-top: 2px solid #ede4fb;
            font-size: 20px;
            font-weight: 700;
            color: #2e1e4a;
        }
        .btn-checkout {
            display: block;
            width: 100%;
            background: #6c3fc5;
            color: white;
            text-align: center;
            padding: 15px;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 700;
            text-decoration: none;
            margin-top: 25px;
            transition: background 0.2s;
        }
        .btn-checkout:hover {
            background: #5831a3;
        }
        .btn-continue {
            display: block;
            text-align: center;
            margin-top: 15px;
            color: #6c3fc5;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
        }
        .empty-cart {
            text-align: center;
            padding: 60px 20px;
            background: white;
            border-radius: 14px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
        }
        .empty-icon {
            font-size: 50px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>

    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="logo">
            <div class="logo-icon">🛍</div>
            <h1><span>ANUJA</span> MART</h1>
        </a>

        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/products" class="nav-link">Browse Products</a>
            <a href="${pageContext.request.contextPath}/orders" class="nav-link">My Orders</a>
            <a href="${pageContext.request.contextPath}/logout" class="nav-link" style="color: #dc3545;">Logout</a>
        </div>
    </nav>

    <div class="container">
        <c:choose>
            <c:when test="${empty cartItems}">
                <div class="empty-cart">
                    <div class="empty-icon">🛒</div>
                    <h2>Your Shopping Cart is Empty</h2>
                    <p style="color: #777; margin: 10px 0 25px;">Explore our catalog and find amazing deals on electronics, fashion, beauty and more!</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn-checkout" style="display:inline-block; width:auto; padding: 12px 30px;">Start Shopping Now →</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="cart-layout">
                    <div class="cart-items">
                        <div class="cart-header">
                            Shopping Cart (${cartItems.size()} items)
                        </div>

                        <c:forEach var="item" items="${cartItems}">
                            <div class="cart-item">
                                <img src="${item.product.imageUrl}" alt="<c:out value='${item.product.name}'/>" class="item-img" onerror="this.src='https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500';">

                                <div class="item-details">
                                    <div class="item-category"><c:out value="${item.product.category}"/></div>
                                    <div class="item-name"><c:out value="${item.product.name}"/></div>
                                    <div class="item-price">₹<fmt:formatNumber value="${item.product.price}" pattern="#,##0.00"/> each</div>
                                </div>

                                <div class="item-actions">
                                    <form action="${pageContext.request.contextPath}/update-cart" method="post" class="qty-form">
                                        <input type="hidden" name="cartItemId" value="${item.id}">
                                        <input type="hidden" name="action" value="update">
                                        <input type="number" name="quantity" value="${item.quantity}" min="1" max="99" class="qty-input">
                                        <button type="submit" class="btn-update">Update</button>
                                    </form>

                                    <form action="${pageContext.request.contextPath}/update-cart" method="post" style="display:inline;">
                                        <input type="hidden" name="cartItemId" value="${item.id}">
                                        <input type="hidden" name="action" value="remove">
                                        <button type="submit" class="btn-remove">Remove</button>
                                    </form>
                                </div>

                                <div class="item-subtotal">
                                    ₹<fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00"/>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="cart-summary">
                        <div class="cart-header">Order Summary</div>

                        <div class="summary-row">
                            <span>Subtotal</span>
                            <span>₹<fmt:formatNumber value="${cartTotal}" pattern="#,##0.00"/></span>
                        </div>
                        <div class="summary-row">
                            <span>Estimated Shipping</span>
                            <span style="color: #1a7f37; font-weight: 600;">FREE</span>
                        </div>
                        <div class="summary-row">
                            <span>Taxes & Fees</span>
                            <span>₹0.00</span>
                        </div>

                        <div class="summary-total">
                            <span>Total</span>
                            <span>₹<fmt:formatNumber value="${cartTotal}" pattern="#,##0.00"/></span>
                        </div>

                        <a href="${pageContext.request.contextPath}/checkout" class="btn-checkout">Proceed to Checkout →</a>
                        <a href="${pageContext.request.contextPath}/products" class="btn-continue">← Continue Shopping</a>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>
