<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cart</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    <section class="cart-section">
        <div class="container">
            <h2>Shopping Cart</h2>
            <div class="product-grid">
                <c:if test="${not empty cartItems}">
                    <c:forEach var="cartItem" items="${cartItems}">
                        <div class="product-item card">
                            <div class="product-details card-body">
                                <h3 class="card-title">${cartItem.productName}</h3>
                                <p class="card-text">Price: $${cartItem.price}</p>
                                <form action="${pageContext.request.contextPath}/cart" method="post" class="d-flex align-items-center">
                                    <input type="hidden" name="cartItemId" value="${cartItem.id}">
                                    <label class="me-2">Quantity:</label>
                                    <input type="number" name="quantity" value="${cartItem.quantity}" min="1" class="form-control">
                                    <button type="submit" class="btn btn-primary ms-2">Update</button>
                                </form>
                                <p class="card-text mt-2">Subtotal: $${cartItem.price * cartItem.quantity}</p>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty cartItems}">
                    <p class="empty-cart">Your cart is empty.</p>
                </c:if>
            </div>
            <div class="cart-total">
                <h3>Total Cart: $${totalCart}</h3>
            </div>
            <div class="cart-actions">
                <a href="${pageContext.request.contextPath}/productList" class="btn btn-secondary">Back to Products</a>
                <c:if test="${not empty cartItems}">
                   
                    <form action="${pageContext.request.contextPath}/order" method="get" style="display:inline;">
                        <button type="submit" class="btn btn-primary">Checkout</button>
                    </form>
                </c:if>
            </div>
        </div>
    </section>
    <jsp:include page="footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>