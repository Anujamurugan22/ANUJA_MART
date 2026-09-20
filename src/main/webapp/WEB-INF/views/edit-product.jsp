<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ANUJA MART - Edit Product</title>
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
            background: #6c3fc5;
            color: white;
            padding: 20px 5%;
            font-size: 24px;
            font-weight: bold;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .container {
            max-width: 550px;
            margin: 40px auto;
            background: white;
            padding: 35px;
            border-radius: 16px;
            box-shadow: 0 4px 20px rgba(108, 63, 197, 0.1);
        }
        h1 {
            color: #6c3fc5;
            font-size: 24px;
            margin-bottom: 20px;
            text-align: center;
        }
        .form-group {
            margin-bottom: 16px;
        }
        label {
            display: block;
            margin-bottom: 6px;
            font-weight: 600;
            color: #403653;
            font-size: 14px;
        }
        input, select, textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd4e8;
            border-radius: 8px;
            font-size: 14px;
            outline: none;
        }
        input:focus, select:focus, textarea:focus {
            border-color: #6c3fc5;
        }
        button {
            width: 100%;
            padding: 14px;
            background: #6c3fc5;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            margin-top: 10px;
            transition: background 0.2s;
        }
        button:hover {
            background: #5831a3;
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #6c3fc5;
            text-decoration: none;
            font-weight: 500;
        }
        .alert {
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 16px;
            font-size: 14px;
            background: #f8d7da;
            color: #721c24;
        }
    </style>
</head>
<body>

<div class="header">
    <div>ANUJA MART</div>
    <a href="${pageContext.request.contextPath}/products?view=seller" style="color:white;font-size:14px;text-decoration:none;">← Back to Products</a>
</div>

<div class="container">
    <h1>Edit Product</h1>

    <c:if test="${not empty param.error}">
        <div class="alert"><c:out value="${param.error}"/></div>
    </c:if>

    <form action="${pageContext.request.contextPath}/edit-product" method="post">
        <input type="hidden" name="id" value="${product.id}">

        <div class="form-group">
            <label>Product Name</label>
            <input type="text" name="name" value="<c:out value='${product.name}'/>" required>
        </div>

        <div class="form-group">
            <label>Category</label>
            <select name="category" required>
                <option value="Electronics" <c:if test="${product.category eq 'Electronics'}">selected</c:if>>Electronics</option>
                <option value="Fashion" <c:if test="${product.category eq 'Fashion'}">selected</c:if>>Fashion</option>
                <option value="Beauty" <c:if test="${product.category eq 'Beauty'}">selected</c:if>>Beauty</option>
                <option value="Grocery" <c:if test="${product.category eq 'Grocery'}">selected</c:if>>Grocery</option>
                <option value="Home" <c:if test="${product.category eq 'Home'}">selected</c:if>>Home</option>
            </select>
        </div>

        <div class="form-group">
            <label>Price (₹)</label>
            <input type="number" name="price" step="0.01" value="${product.price}" required>
        </div>

        <div class="form-group">
            <label>Available Quantity</label>
            <input type="number" name="quantity" value="${product.quantity}" required>
        </div>

        <div class="form-group">
            <label>Image URL</label>
            <input type="url" name="imageUrl" value="<c:out value='${product.imageUrl}'/>">
        </div>

        <div class="form-group">
            <label>Description</label>
            <textarea name="description" rows="4" required><c:out value="${product.description}"/></textarea>
        </div>

        <button type="submit">Update Product</button>
    </form>

    <a href="${pageContext.request.contextPath}/products?view=seller" class="back-link">Cancel</a>
</div>

</body>
</html>
