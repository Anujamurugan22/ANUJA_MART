<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ANUJA MART - Admin Dashboard</title>
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
        }
        .header {
            background: #2e1e4a;
            color: white;
            padding: 18px 5%;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header h1 {
            font-size: 22px;
        }
        .header h1 span {
            color: #b588f7;
        }
        .container {
            max-width: 1250px;
            margin: 30px auto;
            padding: 0 20px;
        }
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        .stat-card {
            background: white;
            padding: 22px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(108, 63, 197, 0.08);
            border-left: 5px solid #6c3fc5;
        }
        .stat-title {
            font-size: 13px;
            text-transform: uppercase;
            color: #777;
            font-weight: 600;
        }
        .stat-value {
            font-size: 28px;
            font-weight: 700;
            color: #2e1e4a;
            margin-top: 5px;
        }
        .section-card {
            background: white;
            padding: 25px;
            border-radius: 14px;
            box-shadow: 0 4px 15px rgba(108, 63, 197, 0.08);
            margin-bottom: 30px;
        }
        .section-title {
            font-size: 20px;
            font-weight: 700;
            color: #2e1e4a;
            margin-bottom: 18px;
            border-bottom: 2px solid #f2ecfb;
            padding-bottom: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
            text-align: left;
        }
        th {
            background: #ede4fb;
            color: #403653;
            padding: 12px 15px;
            font-weight: 600;
        }
        td {
            padding: 12px 15px;
            border-bottom: 1px solid #f2eefa;
        }
        tr:hover td {
            background: #faf8fd;
        }
        .role-badge {
            padding: 3px 8px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 600;
        }
        .role-admin { background: #e3d2fd; color: #4b1f8c; }
        .role-seller { background: #d0f0fd; color: #0c5460; }
        .role-buyer { background: #d4edda; color: #155724; }
        .btn-action {
            background: #6c3fc5;
            color: white;
            padding: 6px 12px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 13px;
            font-weight: 600;
        }
        .btn-delete {
            background: #dc3545;
            color: white;
            padding: 6px 12px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 13px;
        }
        .status-select {
            padding: 5px 10px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 13px;
        }
        .alert {
            padding: 12px 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            background: #d4edda;
            color: #155724;
        }
    </style>
</head>
<body>

    <div class="header">
        <h1><span>ANUJA</span> MART - System Administration</h1>
        <div>
            <a href="${pageContext.request.contextPath}/products" style="color:#ede4fb; text-decoration:none; margin-right:20px;">Browse Store</a>
            <a href="${pageContext.request.contextPath}/logout" style="background:#dc3545; color:white; padding:7px 15px; border-radius:6px; text-decoration:none; font-size:13px; font-weight:600;">Logout</a>
        </div>
    </div>

    <div class="container">

        <c:if test="${param.message eq 'success'}">
            <div class="alert">Action completed successfully!</div>
        </c:if>

        <!-- Stats Counter Cards -->
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-title">Total Registered Users</div>
                <div class="stat-value">${users.size()}</div>
            </div>
            <div class="stat-card" style="border-left-color: #28a745;">
                <div class="stat-title">Active Products</div>
                <div class="stat-value">${products.size()}</div>
            </div>
            <div class="stat-card" style="border-left-color: #fd7e14;">
                <div class="stat-title">Total Orders Placed</div>
                <div class="stat-value">${orders.size()}</div>
            </div>
        </div>

        <!-- Users Management Section -->
        <div class="section-card">
            <div class="section-title">Registered Users (${users.size()})</div>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Joined Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="u" items="${users}">
                        <tr>
                            <td>#${u.id}</td>
                            <td><strong><c:out value="${u.name}"/></strong></td>
                            <td><c:out value="${u.email}"/></td>
                            <td>
                                <span class="role-badge <c:choose><c:when test='${u.role eq \"ADMIN\"}'>role-admin</c:when><c:when test='${u.role eq \"SELLER\"}'>role-seller</c:when><c:otherwise>role-buyer</c:otherwise></c:choose>">
                                    <c:out value="${u.role}"/>
                                </span>
                            </td>
                            <td><fmt:formatDate value="${u.createdAt}" pattern="dd MMM yyyy, HH:mm"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Orders Management Section -->
        <div class="section-card">
            <div class="section-title">All Customer Orders (${orders.size()})</div>
            <table>
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Customer</th>
                        <th>Total Amount</th>
                        <th>Current Status</th>
                        <th>Update Status</th>
                        <th>Date Placed</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="o" items="${orders}">
                        <tr>
                            <td><strong>#${o.id}</strong></td>
                            <td><c:out value="${o.buyerName}"/> (${o.buyerEmail})</td>
                            <td><strong>₹<fmt:formatNumber value="${o.totalAmount}" pattern="#,##0.00"/></strong></td>
                            <td><span style="background:#ede4fb; padding:3px 8px; border-radius:10px; font-weight:600;"><c:out value="${o.status}"/></span></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin" method="post" style="display:flex; gap:8px; align-items:center;">
                                    <input type="hidden" name="action" value="updateOrderStatus">
                                    <input type="hidden" name="orderId" value="${o.id}">
                                    <select name="status" class="status-select">
                                        <option value="CONFIRMED" <c:if test="${o.status eq 'CONFIRMED'}">selected</c:if>>CONFIRMED</option>
                                        <option value="SHIPPED" <c:if test="${o.status eq 'SHIPPED'}">selected</c:if>>SHIPPED</option>
                                        <option value="DELIVERED" <c:if test="${o.status eq 'DELIVERED'}">selected</c:if>>DELIVERED</option>
                                        <option value="CANCELLED" <c:if test="${o.status eq 'CANCELLED'}">selected</c:if>>CANCELLED</option>
                                    </select>
                                    <button type="submit" class="btn-action">Update</button>
                                </form>
                            </td>
                            <td><fmt:formatDate value="${o.createdAt}" pattern="dd MMM yyyy, HH:mm"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Products Moderation Section -->
        <div class="section-card">
            <div class="section-title">All Product Listings (${products.size()})</div>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Product Name</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Stock</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${products}">
                        <tr>
                            <td>#${p.id}</td>
                            <td><strong><c:out value="${p.name}"/></strong></td>
                            <td><c:out value="${p.category}"/></td>
                            <td>₹<fmt:formatNumber value="${p.price}" pattern="#,##0.00"/></td>
                            <td>${p.quantity}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin" method="post" onsubmit="return confirm('Are you sure you want to remove this product as admin?');">
                                    <input type="hidden" name="action" value="deleteProduct">
                                    <input type="hidden" name="productId" value="${p.id}">
                                    <button type="submit" class="btn-delete">Remove Listing</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </div>

</body>
</html>
