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
        <c:if test="${param.message eq 'review_success' or param.message eq 'review_added'}">
            <div class="alert alert-success">✓ Review submitted successfully.</div>
        </c:if>
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
                        <div class="product-card" id="prod-${p.id}">
                            <img src="${p.imageUrl}" alt="<c:out value='${p.name}'/>" class="product-img" onerror="this.src='https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500';">
                            <div class="product-info">
                                <div class="product-category"><c:out value="${p.category}"/></div>
                                <div class="product-name"><c:out value="${p.name}"/></div>
                                <div class="product-desc"><c:out value="${p.description}"/></div>

                                <!-- Reviews Action -->
                                <div style="margin-bottom: 12px;">
                                    <button type="button" onclick="openReviewModal(${p.id}, '<c:out value="${p.name}"/>')" style="background:none; border:none; color:#6c3fc5; cursor:pointer; font-size:13px; font-weight:600; padding:0; display:flex; align-items:center; gap:5px;">
                                        <c:choose>
                                            <c:when test="${p.reviewCount > 0}">
                                                <span style="color:#f39c12; font-size:14px;">★</span>
                                                <span style="font-weight:700; color:#333;">${p.avgRating}/5</span>
                                                <span style="color:#777; font-size:12px;">(${p.reviewCount} ${p.reviewCount == 1 ? 'review' : 'reviews'})</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color:#777; font-size:12px;">⭐ View Reviews & Rating</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </button>
                                </div>

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

    <!-- Review Modal Dialog -->
    <div id="reviewModal" style="display:none; position:fixed; z-index:9999; left:0; top:0; width:100%; height:100%; background:rgba(0,0,0,0.5); align-items:center; justify-content:center;">
        <div style="background:white; border-radius:16px; width:90%; max-width:550px; max-height:85vh; overflow-y:auto; padding:25px; box-shadow:0 10px 30px rgba(0,0,0,0.2); position:relative;">
            <button onclick="closeReviewModal()" style="position:absolute; right:18px; top:15px; border:none; background:none; font-size:22px; cursor:pointer; color:#777;">✕</button>
            <h3 id="modalProductTitle" style="color:#2e1e4a; margin-bottom:10px; font-size:19px;">Product Reviews</h3>
            <div id="modalSummary" style="font-size:14px; color:#6c3fc5; font-weight:600; margin-bottom:15px;">Loading reviews...</div>

            <!-- Existing Reviews List -->
            <div id="reviewsList" style="max-height:220px; overflow-y:auto; margin-bottom:20px; border:1px solid #f0eaf7; border-radius:8px; padding:12px; background:#faf7fd;"></div>

            <!-- Submit Review Form Section -->
            <div id="reviewFormSection" style="border-top:1px solid #eee; padding-top:15px;">
                <div id="reviewFormContainer" style="display:none;">
                    <h4 style="margin-bottom:10px; color:#333;" id="reviewFormTitle">Write a Customer Review</h4>
                    <form action="${pageContext.request.contextPath}/reviews" method="post" id="reviewForm">
                        <input type="hidden" name="productId" id="reviewProductId">
                        <input type="hidden" name="source" value="products">
                        <div style="margin-bottom:10px;">
                            <label style="display:block; font-size:13px; font-weight:600; margin-bottom:4px;">Rating (1 to 5 Stars) *</label>
                            <select name="rating" id="reviewRatingSelect" required style="width:100%; padding:8px; border-radius:6px; border:1px solid #ccc;">
                                <option value="5">⭐⭐⭐⭐⭐ (5 - Excellent)</option>
                                <option value="4">⭐⭐⭐⭐ (4 - Very Good)</option>
                                <option value="3">⭐⭐⭐ (3 - Good)</option>
                                <option value="2">⭐⭐ (2 - Fair)</option>
                                <option value="1">⭐ (1 - Poor)</option>
                            </select>
                        </div>
                        <div style="margin-bottom:12px;">
                            <label style="display:block; font-size:13px; font-weight:600; margin-bottom:4px;">Your Comments *</label>
                            <textarea name="comment" id="reviewCommentText" rows="3" required placeholder="Share your experience with this item..." style="width:100%; padding:8px; border-radius:6px; border:1px solid #ccc; box-sizing:border-box;"></textarea>
                        </div>
                        <button type="submit" id="reviewSubmitBtn" style="width:100%; background:#6c3fc5; color:white; padding:10px; border:none; border-radius:8px; font-weight:600; cursor:pointer;">Submit Review</button>
                    </form>
                </div>
                <div id="reviewNotice" style="display:none; padding:12px; border-radius:8px; background:#fbf9fe; border:1px solid #ede4fb; font-size:13px; color:#55496b; text-align:center;"></div>
            </div>
        </div>
    </div>

    <!-- AI Chatbot Floating Widget -->
    <div id="aiChatWidget" style="position:fixed; bottom:25px; right:25px; z-index:9998; font-family:'Segoe UI', Arial, sans-serif;">
        <!-- Launcher Button -->
        <button id="aiLauncherBtn" onclick="toggleAiChat()" style="background:#6c3fc5; color:white; border:none; border-radius:50px; padding:14px 22px; font-size:15px; font-weight:600; cursor:pointer; box-shadow:0 6px 20px rgba(108,63,197,0.35); display:flex; align-items:center; gap:8px;">
            <span>🤖</span> AI Assistant
        </button>

        <!-- Chat Window -->
        <div id="aiChatWindow" style="display:none; width:360px; height:480px; background:white; border-radius:18px; box-shadow:0 10px 40px rgba(0,0,0,0.2); overflow:hidden; flex-direction:column; border:1px solid #e5d8f6;">
            <!-- Header -->
            <div style="background:#6c3fc5; color:white; padding:14px 18px; display:flex; justify-content:space-between; align-items:center;">
                <div style="display:flex; align-items:center; gap:8px;">
                    <span style="font-size:20px;">🤖</span>
                    <div>
                        <div style="font-weight:700; font-size:14px;">ANUJA AI Assistant</div>
                        <div style="font-size:11px; opacity:0.85;">● Online 24/7</div>
                    </div>
                </div>
                <button onclick="toggleAiChat()" style="background:none; border:none; color:white; font-size:18px; cursor:pointer;">✕</button>
            </div>

            <!-- Messages Log -->
            <div id="aiMessages" style="flex:1; padding:15px; overflow-y:auto; font-size:13px; background:#fbf9fe; display:flex; flex-direction:column; gap:10px;">
                <div style="background:#f0eaf7; color:#2e1e4a; padding:10px 14px; border-radius:12px; align-self:flex-start; max-width:85%;">
                    👋 Hello! I am your <strong>ANUJA MART AI Assistant</strong>. How can I assist you with your shopping today?
                </div>
            </div>

            <!-- Suggested Quick Prompts -->
            <div style="padding:6px 12px; background:#fff; border-top:1px solid #f2eefa; display:flex; gap:6px; overflow-x:auto;">
                <button onclick="sendQuickPrompt('What are your top categories?')" style="background:#f0eaf7; border:1px solid #6c3fc5; color:#6c3fc5; padding:4px 8px; border-radius:12px; font-size:11px; cursor:pointer; white-space:nowrap;">🏷️ Categories</button>
                <button onclick="sendQuickPrompt('How do I track my order?')" style="background:#f0eaf7; border:1px solid #6c3fc5; color:#6c3fc5; padding:4px 8px; border-radius:12px; font-size:11px; cursor:pointer; white-space:nowrap;">📦 Track Order</button>
                <button onclick="sendQuickPrompt('How can I sell on ANUJA MART?')" style="background:#f0eaf7; border:1px solid #6c3fc5; color:#6c3fc5; padding:4px 8px; border-radius:12px; font-size:11px; cursor:pointer; white-space:nowrap;">💼 Sell</button>
            </div>

            <!-- Input Bar -->
            <div style="padding:10px 12px; background:white; border-top:1px solid #eee; display:flex; gap:8px;">
                <input type="text" id="aiInput" placeholder="Ask about products, orders..." onkeypress="handleAiKeyPress(event)" style="flex:1; padding:9px 12px; border:1px solid #ddd; border-radius:20px; font-size:13px; outline:none;">
                <button onclick="sendAiMessage()" style="background:#6c3fc5; color:white; border:none; border-radius:50%; width:36px; height:36px; cursor:pointer; display:flex; align-items:center; justify-content:center;">➤</button>
            </div>
        </div>
    </div>

    <script>
        // Review Modal Logic
        function openReviewModal(productId, productName) {
            document.getElementById('modalProductTitle').textContent = 'Reviews: ' + productName;
            document.getElementById('reviewProductId').value = productId;
            document.getElementById('reviewModal').style.display = 'flex';
            document.getElementById('modalSummary').textContent = 'Loading reviews...';
            document.getElementById('reviewsList').innerHTML = '';
            document.getElementById('reviewFormContainer').style.display = 'none';
            document.getElementById('reviewNotice').style.display = 'none';

            fetch('${pageContext.request.contextPath}/reviews?productId=' + productId)
                .then(r => r.json())
                .then(data => {
                    const avg = data.avgRating || 0;
                    const count = data.count || 0;
                    document.getElementById('modalSummary').innerHTML = 'Average Rating: <strong>⭐ ' + avg + ' / 5.0</strong> (' + count + ' reviews)';

                    if (!data.reviews || data.reviews.length === 0) {
                        document.getElementById('reviewsList').innerHTML = '<div style="color:#777; text-align:center; padding:15px; font-size:13px;">No reviews yet. Be the first to review this product!</div>';
                    } else {
                        let html = '';
                        data.reviews.forEach(rev => {
                            let stars = '⭐'.repeat(rev.rating);
                            html += '<div style="border-bottom:1px solid #eedefc; padding:8px 0; font-size:13px;">' +
                                    '<div><strong>' + escapeHtml(rev.buyerName) + '</strong> <span style="color:#f39c12; margin-left:6px;">' + stars + '</span></div>' +
                                    '<div style="color:#555; margin-top:3px;">' + escapeHtml(rev.comment) + '</div>' +
                                    '</div>';
                        });
                        document.getElementById('reviewsList').innerHTML = html;
                    }

                    // Check eligibility to review
                    const formContainer = document.getElementById('reviewFormContainer');
                    const notice = document.getElementById('reviewNotice');
                    if (data.canReview) {
                        formContainer.style.display = 'block';
                        notice.style.display = 'none';
                        if (data.userReview) {
                            document.getElementById('reviewFormTitle').textContent = 'Update Your Review';
                            document.getElementById('reviewRatingSelect').value = data.userReview.rating;
                            document.getElementById('reviewCommentText').value = data.userReview.comment;
                            document.getElementById('reviewSubmitBtn').textContent = 'Update Review';
                        } else {
                            document.getElementById('reviewFormTitle').textContent = 'Write a Customer Review';
                            document.getElementById('reviewRatingSelect').value = '5';
                            document.getElementById('reviewCommentText').value = '';
                            document.getElementById('reviewSubmitBtn').textContent = 'Submit Review';
                        }
                    } else if (!data.isLoggedIn) {
                        formContainer.style.display = 'none';
                        notice.style.display = 'block';
                        notice.innerHTML = '💡 Please <a href="${pageContext.request.contextPath}/login.html" style="color:#6c3fc5; font-weight:600;">login as a buyer</a> who purchased this product to leave a review.';
                    } else {
                        formContainer.style.display = 'none';
                        notice.style.display = 'block';
                        notice.innerHTML = '🔒 Only verified customers who have purchased this product can leave a rating and review.';
                    }
                })
                .catch(err => {
                    document.getElementById('modalSummary').textContent = 'Reviews & Ratings';
                    document.getElementById('reviewsList').innerHTML = '<div style="color:#777; padding:10px;">Unable to load reviews right now.</div>';
                });
        }

        function closeReviewModal() {
            document.getElementById('reviewModal').style.display = 'none';
        }

        function escapeHtml(str) {
            if (!str) return '';
            return str.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
        }

        // AI Chatbot Logic
        function toggleAiChat() {
            const win = document.getElementById('aiChatWindow');
            const btn = document.getElementById('aiLauncherBtn');
            if (win.style.display === 'none' || win.style.display === '') {
                win.style.display = 'flex';
                btn.style.display = 'none';
                document.getElementById('aiInput').focus();
            } else {
                win.style.display = 'none';
                btn.style.display = 'flex';
            }
        }

        function handleAiKeyPress(e) {
            if (e.key === 'Enter') {
                sendAiMessage();
            }
        }

        function sendQuickPrompt(promptText) {
            document.getElementById('aiInput').value = promptText;
            sendAiMessage();
        }

        function sendAiMessage() {
            const input = document.getElementById('aiInput');
            const text = input.value.trim();
            if (!text) return;

            const messagesDiv = document.getElementById('aiMessages');

            // Render User Bubble
            const userBubble = document.createElement('div');
            userBubble.style.cssText = 'background:#6c3fc5; color:white; padding:9px 13px; border-radius:12px; align-self:flex-end; max-width:85%; word-break:break-word;';
            userBubble.textContent = text;
            messagesDiv.appendChild(userBubble);

            input.value = '';
            messagesDiv.scrollTop = messagesDiv.scrollHeight;

            // Render Typing Placeholder
            const typingBubble = document.createElement('div');
            typingBubble.style.cssText = 'background:#f0eaf7; color:#777; padding:8px 12px; border-radius:12px; align-self:flex-start; font-style:italic;';
            typingBubble.textContent = 'Thinking...';
            messagesDiv.appendChild(typingBubble);
            messagesDiv.scrollTop = messagesDiv.scrollHeight;

            fetch('${pageContext.request.contextPath}/api/chat', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
                body: 'message=' + encodeURIComponent(text)
            })
            .then(res => res.json())
            .then(data => {
                typingBubble.remove();
                const botBubble = document.createElement('div');
                botBubble.style.cssText = 'background:#f0eaf7; color:#2e1e4a; padding:10px 14px; border-radius:12px; align-self:flex-start; max-width:85%; white-space:pre-wrap; line-height:1.4;';
                botBubble.textContent = data.reply || 'I am here to assist with any questions about ANUJA MART!';
                messagesDiv.appendChild(botBubble);
                messagesDiv.scrollTop = messagesDiv.scrollHeight;
            })
            .catch(err => {
                typingBubble.remove();
                const errBubble = document.createElement('div');
                errBubble.style.cssText = 'background:#f0eaf7; color:#2e1e4a; padding:10px 14px; border-radius:12px; align-self:flex-start; max-width:85%;';
                errBubble.textContent = 'I am currently operating in store assistance mode. You can search products, track orders, or explore categories!';
                messagesDiv.appendChild(errBubble);
                messagesDiv.scrollTop = messagesDiv.scrollHeight;
            });
        }
    </script>
</body>
</html>
