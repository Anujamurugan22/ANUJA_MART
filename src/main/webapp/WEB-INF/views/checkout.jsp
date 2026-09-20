<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ANUJA MART - Checkout</title>
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
        .container {
            max-width: 1100px;
            margin: 40px auto;
            padding: 0 20px;
        }
        .checkout-layout {
            display: flex;
            gap: 30px;
            align-items: flex-start;
        }
        .checkout-main {
            flex: 2;
        }
        .checkout-summary {
            flex: 1;
            background: white;
            border-radius: 14px;
            padding: 25px;
            box-shadow: 0 4px 15px rgba(108, 63, 197, 0.08);
            position: sticky;
            top: 90px;
        }
        .card {
            background: white;
            border-radius: 14px;
            padding: 25px;
            box-shadow: 0 4px 15px rgba(108, 63, 197, 0.08);
            margin-bottom: 25px;
        }
        .card-header {
            font-size: 18px;
            font-weight: 700;
            color: #2e1e4a;
            margin-bottom: 18px;
            padding-bottom: 12px;
            border-bottom: 1px solid #f0eaf7;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .step-num {
            width: 28px;
            height: 28px;
            background: #6c3fc5;
            color: white;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 14px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 6px;
            font-weight: 600;
            color: #403653;
            font-size: 14px;
        }
        input, textarea, select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd4e8;
            border-radius: 8px;
            font-size: 14px;
            outline: none;
        }
        input:focus, textarea:focus, select:focus {
            border-color: #6c3fc5;
        }
        .payment-option {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 15px;
            border: 1px solid #ddd4e8;
            border-radius: 8px;
            margin-bottom: 10px;
            cursor: pointer;
            transition: all 0.2s;
        }
        .payment-option:hover {
            border-color: #6c3fc5;
            background: #fbf9ff;
        }
        .payment-option input[type="radio"] {
            width: auto;
        }
        .item-row {
            display: flex;
            justify-content: space-between;
            padding: 10px 0;
            font-size: 14px;
            border-bottom: 1px solid #f5f0fa;
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
        .btn-submit {
            display: block;
            width: 100%;
            background: #6c3fc5;
            color: white;
            text-align: center;
            padding: 15px;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 700;
            border: none;
            cursor: pointer;
            margin-top: 25px;
            transition: background 0.2s;
        }
        .btn-submit:hover {
            background: #5831a3;
        }
        .alert {
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            background: #f8d7da;
            color: #721c24;
            font-size: 14px;
        }
    </style>
</head>
<body>

    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="logo">
            <div class="logo-icon">🛍</div>
            <h1><span>ANUJA</span> MART</h1>
        </a>
        <div style="font-size: 14px; color: #666;">
            Secure Checkout 🔒
        </div>
    </nav>

    <div class="container">
        <c:if test="${not empty param.error}">
            <div class="alert"><c:out value="${param.error}"/></div>
        </c:if>

        <form action="${pageContext.request.contextPath}/place-order" method="post">
            <div class="checkout-layout">
                <div class="checkout-main">

                    <!-- Step 1: Shipping Address -->
                    <div class="card">
                        <div class="card-header">
                            <div class="step-num">1</div>
                            <div>Shipping Address</div>
                        </div>

                        <div class="form-group">
                            <label>Delivery Address</label>
                            <textarea name="shippingAddress" rows="3" placeholder="Enter street address, building/flat no., city, state and PIN code" required>Plot 42, Anna Nagar West, Chennai, Tamil Nadu - 600040</textarea>
                        </div>
                    </div>

                    <!-- Step 2: Payment Method (Mock Payment) -->
                    <div class="card">
                        <div class="card-header">
                            <div class="step-num">2</div>
                            <div>Payment Method (Mock Payment Step)</div>
                        </div>

                        <label class="payment-option">
                            <input type="radio" name="paymentMethod" value="MOCK_UPI" checked>
                            <div>
                                <strong>UPI / QR Code (Mock)</strong>
                                <div style="font-size: 12px; color: #777;">Google Pay, PhonePe, Paytm, BHIM UPI</div>
                            </div>
                        </label>

                        <label class="payment-option">
                            <input type="radio" name="paymentMethod" value="MOCK_CARD">
                            <div>
                                <strong>Credit / Debit Card (Mock)</strong>
                                <div style="font-size: 12px; color: #777;">Visa, MasterCard, RuPay</div>
                            </div>
                        </label>

                        <label class="payment-option">
                            <input type="radio" name="paymentMethod" value="MOCK_COD">
                            <div>
                                <strong>Cash on Delivery (COD)</strong>
                                <div style="font-size: 12px; color: #777;">Pay with cash upon receipt</div>
                            </div>
                        </label>
                    </div>

                </div>

                <!-- Order Summary Sidebar -->
                <div class="checkout-summary">
                    <div class="card-header" style="padding-top:0;">Order Review</div>

                    <c:forEach var="item" items="${cartItems}">
                        <div class="item-row">
                            <span><c:out value="${item.product.name}"/> × ${item.quantity}</span>
                            <span>₹<fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00"/></span>
                        </div>
                    </c:forEach>

                    <div style="margin-top: 15px; font-size: 14px; color: #666;">
                        <div style="display:flex; justify-content:space-between; margin-bottom: 6px;">
                            <span>Subtotal</span>
                            <span>₹<fmt:formatNumber value="${cartTotal}" pattern="#,##0.00"/></span>
                        </div>
                        <div style="display:flex; justify-content:space-between; margin-bottom: 6px;">
                            <span>Delivery</span>
                            <span style="color:#1a7f37; font-weight:600;">FREE</span>
                        </div>
                    </div>

                    <div class="summary-total">
                        <span>Total to Pay</span>
                        <span>₹<fmt:formatNumber value="${cartTotal}" pattern="#,##0.00"/></span>
                    </div>

                    <button type="submit" class="btn-submit">Confirm & Place Order 🛍️</button>
                    <a href="${pageContext.request.contextPath}/cart" style="display:block; text-align:center; margin-top:15px; color:#6c3fc5; text-decoration:none; font-size:14px;">← Back to Cart</a>
                </div>
            </div>
        </form>
    </div>

</body>
</html>
