<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Placed Successfully - ANUJA MART</title>
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
            max-width: 800px;
            margin: 40px auto;
            padding: 0 20px;
        }
        .success-card {
            background: white;
            border-radius: 16px;
            box-shadow: 0 6px 25px rgba(108, 63, 197, 0.1);
            overflow: hidden;
            border: 1px solid #ede4fb;
            text-align: center;
            padding: 40px 30px;
        }
        .success-icon {
            width: 76px;
            height: 76px;
            background: #d4edda;
            color: #28a745;
            font-size: 42px;
            font-weight: bold;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 20px;
            box-shadow: 0 4px 15px rgba(40, 167, 69, 0.2);
        }
        .success-title {
            font-size: 28px;
            font-weight: 700;
            color: #155724;
            margin-bottom: 8px;
        }
        .order-id-badge {
            display: inline-block;
            background: #ede4fb;
            color: #6c3fc5;
            font-size: 18px;
            font-weight: 700;
            padding: 8px 20px;
            border-radius: 30px;
            margin: 10px 0 15px;
        }
        .thank-you-msg {
            color: #55496b;
            font-size: 16px;
            margin-bottom: 30px;
        }
        .details-box {
            background: #faf7fd;
            border-radius: 12px;
            border: 1px solid #eee4f8;
            padding: 24px;
            text-align: left;
            margin-bottom: 30px;
        }
        .details-row {
            display: flex;
            justify-content: space-between;
            padding: 10px 0;
            border-bottom: 1px solid #f0e8f7;
            font-size: 15px;
        }
        .details-row:last-child {
            border-bottom: none;
        }
        .details-label {
            color: #665b7a;
            font-weight: 500;
        }
        .details-value {
            color: #222;
            font-weight: 600;
            text-align: right;
            max-width: 60%;
        }
        .status-pill {
            background: #e6f9ed;
            color: #1a7f37;
            padding: 3px 10px;
            border-radius: 12px;
            font-size: 13px;
            font-weight: 600;
        }
        .items-heading {
            font-size: 16px;
            font-weight: 700;
            color: #2e1e4a;
            margin: 20px 0 10px;
            text-align: left;
        }
        .items-table {
            width: 100%;
            border-collapse: collapse;
            text-align: left;
            font-size: 14px;
            margin-bottom: 10px;
        }
        .items-table th {
            background: #ede4fb;
            padding: 10px;
            color: #403653;
        }
        .items-table td {
            padding: 10px;
            border-bottom: 1px solid #f0e8f7;
        }
        .actions-group {
            display: flex;
            gap: 15px;
            justify-content: center;
            margin-top: 20px;
            flex-wrap: wrap;
        }
        .btn {
            display: inline-block;
            padding: 13px 28px;
            border-radius: 10px;
            font-size: 15px;
            font-weight: 600;
            text-decoration: none;
            cursor: pointer;
            transition: all 0.2s;
        }
        .btn-primary {
            background: #6c3fc5;
            color: white;
            border: 2px solid #6c3fc5;
        }
        .btn-primary:hover {
            background: #5831a3;
            border-color: #5831a3;
        }
        .btn-secondary {
            background: white;
            color: #6c3fc5;
            border: 2px solid #6c3fc5;
        }
        .btn-secondary:hover {
            background: #f3ecff;
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
            <a href="${pageContext.request.contextPath}/logout" class="nav-link" style="color:#dc3545;">Logout</a>
        </div>
    </nav>

    <div class="container">
        <div class="success-card">
            <div class="success-icon">✓</div>
            <h1 class="success-title">✓ Order Placed Successfully!</h1>
            <div class="order-id-badge">Order ID: #${order.id}</div>
            <p class="thank-you-msg">Thank you for shopping with ANUJA MART.</p>

            <div class="details-box">
                <div class="details-row">
                    <span class="details-label">Order Status:</span>
                    <span class="details-value"><span class="status-pill"><c:out value="${order.status}"/></span></span>
                </div>
                <div class="details-row">
                    <span class="details-label">Total Amount:</span>
                    <span class="details-value" style="color:#6c3fc5; font-size:17px;">₹<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></span>
                </div>
                <div class="details-row">
                    <span class="details-label">Payment Method:</span>
                    <span class="details-value"><c:out value="${order.paymentMethod}"/></span>
                </div>
                <div class="details-row">
                    <span class="details-label">Shipping Address:</span>
                    <span class="details-value"><c:out value="${order.shippingAddress}"/></span>
                </div>

                <c:if test="${not empty order.items}">
                    <div class="items-heading">Ordered Items</div>
                    <table class="items-table">
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Qty</th>
                                <th>Price</th>
                                <th style="text-align:right;">Subtotal</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${order.items}">
                                <tr>
                                    <td><strong><c:out value="${item.productName}"/></strong></td>
                                    <td>${item.quantity}</td>
                                    <td>₹<fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00"/></td>
                                    <td style="text-align:right; font-weight:600;">₹<fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>

            <div class="actions-group">
                <a href="${pageContext.request.contextPath}/orders" class="btn btn-primary">View My Orders</a>
                <a href="${pageContext.request.contextPath}/products" class="btn btn-secondary">Continue Shopping</a>
            </div>
        </div>
    </div>

</body>
</html>
