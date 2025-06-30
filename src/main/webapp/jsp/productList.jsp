<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    <section class="product-section">
        <div class="container">
            <h2>Product List</h2>
            <div class="product-grid">
                <c:forEach var="product" items="${productList}">
                    <div class="product-item card">
                        <img src="${product.image != null ? product.image : product.imageUrl != null ? product.imageUrl : 'https://via.placeholder.com/250x200'}" alt="${product.name}" class="product-image card-img-top">
                        <div class="product-details card-body">
                            <h3 class="card-title">${product.name}</h3>
                            <p class="card-text">$${product.price}</p>
                            <a href="${pageContext.request.contextPath}/cart?productId=${product.id}" class="btn btn-primary">Add to Cart</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
    <jsp:include page="footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>