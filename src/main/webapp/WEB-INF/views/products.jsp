<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ANUJA MART - Products</title>
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
            box-shadow: 0 2px 8px rgba(108, 63, 197, 0.05);
            position: sticky;
            top: 0;
            z-index: 100;
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
            transition: all 0.2s;
        }
        .nav-link:hover {
            color: #6c3fc5;
            background: #f3ecff;
        }
        .cart-badge {
            background: #6c3fc5;
            color: white;
            padding: 2px 8px;
            border-radius: 12px;
            font-size: 12px;
            margin-left: 4px;
        }
        .btn-primary {
            background: #6c3fc5;
            color: white !important;
            padding: 8px 16px;
            border-radius: 8px;
            font-weight: 600;
            text-decoration: none;
            display: inline-block;
        }
        .btn-primary:hover {
            background: #5a32a6;
        }
        .hero {
            background: linear-gradient(135deg, #7d3fc1 0%, #5a2e91 100%);
            color: white;
            padding: 40px 5%;
            text-align: center;
        }
        .hero h2 {
            font-size: 32px;
            margin-bottom: 10px;
        }
        .hero p {
            font-size: 16px;
            opacity: 0.9;
            margin-bottom: 25px;
        }
        .search-box {
            max-width: 650px;
            margin: 0 auto;
            display: flex;
            gap: 10px;
            background: white;
            padding: 6px;
            border-radius: 30px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
        }
        .search-box input {
            flex: 1;
            border: none;
            padding: 12px 20px;
            font-size: 15px;
            border-radius: 30px;
            outline: none;
        }
        .search-box button {
            background: #6c3fc5;
            color: white;
            border: none;
            padding: 12px 28px;
            border-radius: 30px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s;
        }
        .search-box button:hover {
            background: #5831a3;
        }
        .container {
            max-width: 1250px;
            margin: 30px auto;
            padding: 0 20px;
        }
        .categories-bar {
            display: flex;
            gap: 12px;
            overflow-x: auto;
            padding-bottom: 15px;
            margin-bottom: 25px;
        }
        .category-pill {
            text-decoration: none;
            padding: 8px 18px;
            border-radius: 20px;
            background: white;
            color: #55496b;
            font-size: 14px;
            font-weight: 500;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
            transition: all 0.2s;
            white-space: nowrap;
        }
        .category-pill:hover, .category-pill.active {
            background: #6c3fc5;
            color: white;
        }
        .view-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .view-title {
            font-size: 22px;
            color: #333;
            font-weight: 700;
        }
        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
            gap: 25px;
        }
        .product-card {
            background: white;
            border-radius: 14px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(108, 63, 197, 0.08);
            display: flex;
            flex-direction: column;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .product-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 25px rgba(108, 63, 197, 0.15);
        }
        .product-img {
            width: 100%;
            height: 190px;
            object-fit: cover;
            background: #ede4fb;
        }
        .product-info {
            padding: 18px;
            display: flex;
            flex-direction: column;
            flex: 1;
        }
        .product-category {
            font-size: 12px;
            text-transform: uppercase;
            color: #6c3fc5;
            font-weight: 700;
            letter-spacing: 0.5px;
            margin-bottom: 6px;
        }
        .product-name {
            font-size: 17px;
            font-weight: 600;
            color: #222;
            margin-bottom: 8px;
            line-height: 1.3;
        }
        .product-desc {
            font-size: 13px;
            color: #6d6380;
            margin-bottom: 15px;
            line-height: 1.4;
            flex: 1;
        }
        .product-bottom {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: auto;
            padding-top: 12px;
            border-top: 1px solid #f0eaf7;
        }
        .product-price {
            font-size: 20px;
            font-weight: 700;
            color: #2e1e4a;
        }
        .btn-add-cart {
            background: #6c3fc5;
            color: white;
            padding: 9px 16px;
            border-radius: 8px;
            text-decoration: none;
            font-size: 13px;
            font-weight: 600;
            border: none;
            cursor: pointer;
            transition: background 0.2s;
        }
        .btn-add-cart:hover {
            background: #5831a3;
        }
        .btn-edit {
            background: #f0a500;
            color: white;
            padding: 7px 12px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 13px;
            font-weight: 600;
        }
        .btn-delete {
            background: #dc3545;
            color: white;
            padding: 7px 12px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 13px;
            font-weight: 600;
        }
        .btn-delete:hover {
            background: #bd2130;
        }
        .stock-badge {
            font-size: 12px;
            padding: 2px 6px;
            border-radius: 4px;
            font-weight: 600;
        }
        .stock-in {
            background: #e6f9ed;
            color: #1a7f37;
        }
        .stock-out {
            background: #ffebe9;
            color: #cf222e;
        }
        .alert {
            padding: 12px 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
        }
        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .alert-danger {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            background: white;
            border-radius: 14px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
        }
        .empty-state h3 {
            font-size: 20px;
            color: #55496b;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="logo">
            <div class="logo-icon">🛍</div>
            <h1><span>ANUJA</span> MART</h1>
        </a>

        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/products" class="nav-link">Browse Products</a>

            <c:choose>
                <c:when test="${not empty sessionScope.userRole and sessionScope.userRole eq 'SELLER'}">
                    <a href="${pageContext.request.contextPath}/seller.html" class="nav-link">Dashboard</a>
                    <a href="${pageContext.request.contextPath}/products?view=seller" class="nav-link">My Listings</a>
                    <a href="${pageContext.request.contextPath}/orders" class="nav-link">Incoming Orders</a>
                    <a href="${pageContext.request.contextPath}/logout" class="nav-link" style="color: #dc3545;">Logout</a>
                </c:when>
                <c:when test="${not empty sessionScope.userRole and sessionScope.userRole eq 'ADMIN'}">
                    <a href="${pageContext.request.contextPath}/admin" class="nav-link">Admin Panel</a>
                    <a href="${pageContext.request.contextPath}/logout" class="nav-link" style="color: #dc3545;">Logout</a>
                </c:when>
                <c:when test="${not empty sessionScope.userRole and sessionScope.userRole eq 'BUYER'}">
                    <a href="${pageContext.request.contextPath}/cart" class="nav-link">Cart 🛒 <span class="cart-badge">${cartCount}</span></a>
                    <a href="${pageContext.request.contextPath}/orders" class="nav-link">My Orders</a>
                    <a href="${pageContext.request.contextPath}/logout" class="nav-link" style="color: #dc3545;">Logout (${sessionScope.userName})</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/cart" class="nav-link">Cart 🛒</a>
                    <a href="${pageContext.request.contextPath}/login.html" class="nav-link">Login</a>
                    <a href="${pageContext.request.contextPath}/register.html" class="btn-primary">Register</a>
                </c:otherwise>
            </c:choose>
        </div>
    </nav>

    <!-- Hero Search Section -->
    <c:if test="${empty isSellerView or not isSellerView}">
        <div class="hero">
            <h2>Find Everything You Need at ANUJA MART</h2>
            <p>High quality products, verified sellers, instant order confirmation</p>
            <form action="${pageContext.request.contextPath}/products" method="get" class="search-box">
                <input type="text" name="search" placeholder="Search by product name or keyword..." value="<c:out value='${searchQuery}'/>">
                <c:if test="${not empty selectedCategory and selectedCategory ne 'All'}">
                    <input type="hidden" name="category" value="<c:out value='${selectedCategory}'/>">
                </c:if>
                <button type="submit">Search 🔍</button>
            </form>
        </div>
    </c:if>

    <div class="container">

        <!-- Alerts -->
        <c:if test="${param.message eq 'product_added'}">
            <div class="alert alert-success">✓ Product has been added successfully!</div>
        </c:if>
        <c:if test="${param.message eq 'product_updated'}">
            <div class="alert alert-success">✓ Product has been updated successfully!</div>
        </c:if>
        <c:if test="${param.message eq 'deleted'}">
            <div class="alert alert-success">✓ Product listing removed successfully!</div>
        </c:if>
        <c:if test="${param.message eq 'added_to_cart'}">
            <div class="alert alert-success">✓ Item added to cart! <a href="${pageContext.request.contextPath}/cart" style="color:#155724;font-weight:bold;">View Cart 🛒</a></div>
        </c:if>
        <c:if test="${not empty param.error}">
            <div class="alert alert-danger"><c:out value="${param.error}"/></div>
        </c:if>

        <!-- Category Filter Pills -->
        <c:if test="${empty isSellerView or not isSellerView}">
            <div class="categories-bar">
                <a href="${pageContext.request.contextPath}/products<c:if test='${not empty searchQuery}'>?search=${searchQuery}</c:if>"
                   class="category-pill <c:if test='${empty selectedCategory or selectedCategory eq \"All\"}'>active</c:if>">
                    All Categories
                </a>
                <c:forEach var="cat" items="${categories}">
                    <a href="${pageContext.request.contextPath}/products?category=${cat}<c:if test='${not empty searchQuery}'>&search=${searchQuery}</c:if>"
                       class="category-pill <c:if test='${selectedCategory eq cat}'>active</c:if>">
                        <c:out value="${cat}"/>
                    </a>
                </c:forEach>
            </div>
        </c:if>

        <!-- Header row -->
        <div class="view-header">
            <h2 class="view-title">
                <c:choose>
                    <c:when test="${isSellerView}">Seller Product Listings</c:when>
                    <c:when test="${not empty searchQuery}">Search results for "<c:out value='${searchQuery}'/>"</c:when>
                    <c:when test="${not empty selectedCategory and selectedCategory ne 'All'}"><c:out value="${selectedCategory}"/> Products</c:when>
                    <c:otherwise>Featured Products</c:otherwise>
                </c:choose>
            </h2>

            <c:if test="${isSellerView}">
                <a href="${pageContext.request.contextPath}/add-product.html" class="btn-primary">+ Add New Product</a>
            </c:if>
        </div>

        <!-- Products Grid -->
        <c:choose>
            <c:when test="${empty products}">
                <div class="empty-state">
                    <h3>No products found</h3>
                    <p>Try adjusting your search criteria or category filter.</p>
                    <c:if test="${isSellerView}">
                        <br>
                        <a href="${pageContext.request.contextPath}/add-product.html" class="btn-primary">+ Add Your First Product</a>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <div class="product-grid">
                    <c:forEach var="p" items="${products}">
                        <div class="product-card">
                            <img src="${p.imageUrl}" alt="<c:out value='${p.name}'/>" class="product-img" onerror="this.src='https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500';">
                            <div class="product-info">
                                <div class="product-category"><c:out value="${p.category}"/></div>
                                <div class="product-name"><c:out value="${p.name}"/></div>
                                <div class="product-desc"><c:out value="${p.description}"/></div>

                                <div class="product-bottom">
                                    <div>
                                        <div class="product-price">₹<fmt:formatNumber value="${p.price}" pattern="#,##0.00"/></div>
                                        <div>
                                            <c:choose>
                                                <c:when test="${p.quantity > 0}">
                                                    <span class="stock-badge stock-in">${p.quantity} in stock</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="stock-badge stock-out">Out of stock</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>

                                    <div>
                                        <c:choose>
                                            <c:when test="${isSellerView}">
                                                <div style="display: flex; gap: 8px;">
                                                    <a href="${pageContext.request.contextPath}/edit-product?id=${p.id}" class="btn-edit">Edit</a>
                                                    <a href="${pageContext.request.contextPath}/delete-product?id=${p.id}"
                                                       onclick="return confirm('Are you sure you want to delete this product?');"
                                                       class="btn-delete">Delete</a>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${p.quantity > 0}">
                                                        <form action="${pageContext.request.contextPath}/add-to-cart" method="post" style="display: inline;">
                                                            <input type="hidden" name="productId" value="${p.id}">
                                                            <input type="hidden" name="quantity" value="1">
                                                            <button type="submit" class="btn-add-cart">Add to Cart 🛒</button>
                                                        </form>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button class="btn-add-cart" disabled style="background: #ccc; cursor: not-allowed;">Unavailable</button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>

    </div>

</body>
</html>
