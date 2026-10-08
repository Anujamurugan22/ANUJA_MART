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
        .form-row {
            display: flex;
            gap: 15px;
        }
        .form-row .form-group {
            flex: 1;
        }
        @media (max-width: 600px) {
            .form-row {
                flex-direction: column;
                gap: 0;
            }
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
                            <div>Enter Delivery Address</div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="fullName">Full Name *</label>
                                <input type="text" id="fullName" name="fullName" value="<c:out value='${sessionScope.userName}'/>" placeholder="e.g. Priya Sharma" required>
                            </div>
                            <div class="form-group">
                                <label for="phone">Phone Number *</label>
                                <input type="tel" id="phone" name="phone" placeholder="e.g. 9876543210" pattern="[0-9]{10}" title="Please enter a valid 10-digit mobile number" required>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="houseNo">House / Door Number *</label>
                                <input type="text" id="houseNo" name="houseNo" placeholder="e.g. Flat 4B, Blossom Apts / Door 24" required>
                            </div>
                            <div class="form-group">
                                <label for="streetArea">Street / Area *</label>
                                <input type="text" id="streetArea" name="streetArea" placeholder="e.g. 2nd Main Road, Anna Nagar" required>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="city">City *</label>
                                <input type="text" id="city" name="city" placeholder="e.g. Chennai" required>
                            </div>
                            <div class="form-group">
                                <label for="state">State *</label>
                                <input type="text" id="state" name="state" placeholder="e.g. Tamil Nadu" required>
                            </div>
                            <div class="form-group">
                                <label for="pincode">Pincode *</label>
                                <input type="text" id="pincode" name="pincode" placeholder="e.g. 600040" pattern="[0-9]{6}" title="Please enter a valid 6-digit PIN code" required>
                            </div>
                        </div>
                    </div>

                    <!-- Step 2: Payment Method (Mock Payment) -->
                    <div class="card">
                        <div class="card-header">
                            <div class="step-num">2</div>
                            <div>Select Payment Method (Mock Payment)</div>
                        </div>

                        <label class="payment-option">
                            <input type="radio" name="paymentMethod" value="Cash on Delivery" checked>
                            <div>
                                <strong>Cash on Delivery (COD)</strong>
                                <div style="font-size: 12px; color: #777;">Pay with cash upon delivery</div>
                            </div>
                        </label>

                        <label class="payment-option">
                            <input type="radio" name="paymentMethod" value="Mock UPI">
                            <div>
                                <strong>Mock UPI</strong>
                                <div style="font-size: 12px; color: #777;">Google Pay, PhonePe, Paytm, BHIM</div>
                            </div>
                        </label>

                        <label class="payment-option">
                            <input type="radio" name="paymentMethod" value="Mock Card">
                            <div>
                                <strong>Mock Card</strong>
                                <div style="font-size: 12px; color: #777;">Credit / Debit Card (Simulated payment, no details stored)</div>
                            </div>
                        </label>
                    </div>

                </div>

                <!-- Order Summary Sidebar -->
                <div class="checkout-summary">
                    <div class="card-header" style="padding-top:0;">Order Summary</div>

                    <table style="width: 100%; border-collapse: collapse; font-size: 13px; margin-bottom: 12px;">
                        <thead>
                            <tr style="border-bottom: 1px solid #ede4fb; color: #665b7a; text-align: left;">
                                <th style="padding: 6px 0;">Product</th>
                                <th style="padding: 6px 4px; text-align: center;">Qty</th>
                                <th style="padding: 6px 4px; text-align: right;">Price</th>
                                <th style="padding: 6px 0; text-align: right;">Subtotal</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${cartItems}">
                                <tr style="border-bottom: 1px solid #f6f0fa;">
                                    <td style="padding: 8px 0; font-weight: 600; color: #222;"><c:out value="${item.product.name}"/></td>
                                    <td style="padding: 8px 4px; text-align: center;">${item.quantity}</td>
                                    <td style="padding: 8px 4px; text-align: right;">₹<fmt:formatNumber value="${item.product.price}" pattern="#,##0.00"/></td>
                                    <td style="padding: 8px 0; text-align: right; font-weight: 600; color: #6c3fc5;">₹<fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

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
                        <span>Total Amount</span>
                        <span>₹<fmt:formatNumber value="${cartTotal}" pattern="#,##0.00"/></span>
                    </div>

                    <button type="submit" class="btn-submit">Place Order 🛍️</button>
                    <a href="${pageContext.request.contextPath}/cart" style="display:block; text-align:center; margin-top:15px; color:#6c3fc5; text-decoration:none; font-size:14px;">← Back to Cart</a>
                </div>
            </div>
        </form>
    </div>

</body>
</html>
