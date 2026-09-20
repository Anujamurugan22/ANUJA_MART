<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ANUJA MART - <c:choose><c:when test="${isSeller}">Incoming Orders</c:when><c:otherwise>Order History</c:otherwise></c:choose></title>
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
            max-width: 1050px;
            margin: 40px auto;
            padding: 0 20px;
        }
        .page-title {
            font-size: 26px;
            font-weight: 700;
            color: #2e1e4a;
            margin-bottom: 25px;
        }
        .order-card {
            background: white;
            border-radius: 14px;
            box-shadow: 0 4px 15px rgba(108, 63, 197, 0.08);
            margin-bottom: 25px;
            overflow: hidden;
            border: 1px solid #ede4fb;
        }
        .order-card-header {
            background: #fdfbfe;
            padding: 18px 24px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #ede4fb;
            font-size: 14px;
        }
        .order-meta {
            display: flex;
            gap: 25px;
            color: #55496b;
        }
        .order-meta strong {
            display: block;
            color: #222;
            font-size: 15px;
        }
        .status-badge {
            background: #e6f9ed;
            color: #1a7f37;
            padding: 5px 12px;
            border-radius: 20px;
            font-weight: 600;
            font-size: 13px;
        }
        .order-items-list {
            padding: 20px 24px;
        }
        .order-item-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px solid #f6f0fa;
        }
        .order-item-row:last-child {
            border-bottom: none;
        }
        .order-card-footer {
            background: #faf8fd;
            padding: 14px 24px;
            font-size: 13px;
            color: #666;
            border-top: 1px solid #ede4fb;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .table-responsive {
            background: white;
            border-radius: 14px;
            box-shadow: 0 4px 15px rgba(108, 63, 197, 0.08);
            overflow-x: auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            text-align: left;
            font-size: 14px;
        }
        th {
            background: #ede4fb;
            color: #403653;
            padding: 14px 18px;
            font-weight: 600;
        }
        td {
            padding: 14px 18px;
            border-bottom: 1px solid #f3eefa;
        }
        tr:hover td {
            background: #faf8fd;
        }
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            background: white;
            border-radius: 14px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
        }
        .alert {
            padding: 14px 20px;
            border-radius: 8px;
            margin-bottom: 25px;
            font-size: 15px;
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
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
            <c:choose>
                <c:when test="${isSeller}">
                    <a href="${pageContext.request.contextPath}/seller.html" class="nav-link">Dashboard</a>
                    <a href="${pageContext.request.contextPath}/products?view=seller" class="nav-link">My Listings</a>
                    <a href="${pageContext.request.contextPath}/logout" class="nav-link" style="color:#dc3545;">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/products" class="nav-link">Browse Products</a>
                    <a href="${pageContext.request.contextPath}/cart" class="nav-link">Cart 🛒</a>
                    <a href="${pageContext.request.contextPath}/logout" class="nav-link" style="color:#dc3545;">Logout</a>
                </c:otherwise>
            </c:choose>
        </div>
    </nav>

    <div class="container">

        <c:if test="${param.message eq 'order_placed'}">
            <div class="alert">
                🎉 <strong>Order Placed Successfully!</strong> Thank you for your purchase. Your order #${param.orderId} is confirmed.
            </div>
        </c:if>

        <c:choose>
            <!-- Seller View: Incoming Orders -->
            <c:when test="${isSeller}">
                <h2 class="page-title">Seller Incoming Orders</h2>

                <c:choose>
                    <c:when test="${empty incomingItems}">
                        <div class="empty-state">
                            <h3>No incoming orders yet</h3>
                            <p style="color:#777; margin-top:8px;">When buyers purchase your products, the order details will appear here.</p>
                            <br>
                            <a href="${pageContext.request.contextPath}/products?view=seller" style="color:#6c3fc5; font-weight:600; text-decoration:none;">View My Product Listings →</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-responsive">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Item #</th>
                                        <th>Order ID</th>
                                        <th>Product Name</th>
                                        <th>Unit Price</th>
                                        <th>Quantity</th>
                                        <th>Total Earnings</th>
                                        <th>Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${incomingItems}">
                                        <tr>
                                            <td>#${item.id}</td>
                                            <td><strong>Order #${item.orderId}</strong></td>
                                            <td><c:out value="${item.productName}"/></td>
                                            <td>₹<fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00"/></td>
                                            <td><span style="background:#ede4fb; padding:3px 8px; border-radius:12px; font-weight:600;">${item.quantity}</span></td>
                                            <td><strong>₹<fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00"/></strong></td>
                                            <td><fmt:formatDate value="${item.createdAt}" pattern="dd MMM yyyy, HH:mm"/></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:when>

            <!-- Buyer View: Past Orders History -->
            <c:otherwise>
                <h2 class="page-title">My Order History</h2>

                <c:choose>
                    <c:when test="${empty buyerOrders}">
                        <div class="empty-state">
                            <h3>You have not placed any orders yet</h3>
                            <p style="color:#777; margin:10px 0 20px;">Browse our catalog and pick something you love!</p>
                            <a href="${pageContext.request.contextPath}/products" style="display:inline-block; background:#6c3fc5; color:white; padding:12px 24px; border-radius:8px; text-decoration:none; font-weight:600;">Browse Products Now →</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="order" items="${buyerOrders}">
                            <div class="order-card">
                                <div class="order-card-header">
                                    <div class="order-meta">
                                        <div>
                                            <span>Order Placed</span>
                                            <strong><fmt:formatDate value="${order.createdAt}" pattern="dd MMM yyyy, HH:mm"/></strong>
                                        </div>
                                        <div>
                                            <span>Order Number</span>
                                            <strong>#${order.id}</strong>
                                        </div>
                                        <div>
                                            <span>Total Amount</span>
                                            <strong>₹<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></strong>
                                        </div>
                                    </div>

                                    <div>
                                        <span class="status-badge">${order.status}</span>
                                    </div>
                                </div>

                                <div class="order-items-list">
                                    <c:forEach var="item" items="${order.items}">
                                        <div class="order-item-row">
                                            <div>
                                                <strong><c:out value="${item.productName}"/></strong>
                                                <div style="font-size:13px; color:#777;">Quantity: ${item.quantity} × ₹<fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00"/></div>
                                            </div>
                                            <div style="font-weight:600; color:#403653;">
                                                ₹<fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00"/>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>

                                <div class="order-card-footer">
                                    <div><strong>Shipping Address:</strong> <c:out value="${order.shippingAddress}"/></div>
                                    <div><strong>Payment:</strong> <c:out value="${order.paymentMethod}"/></div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>

    </div>

</body>
</html>
