<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Arial', sans-serif;
        }
        .order-list-section {
            padding: 40px 0;
        }
        .order-card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            transition: transform 0.2s;
        }
        .order-card:hover {
            transform: translateY(-5px);
        }
        .order-card .card-body {
            padding: 20px;
        }
        .order-card .btn-custom {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 8px 20px;
            border-radius: 5px;
            text-decoration: none;
        }
        .order-card .btn-custom:hover {
            background-color: #0056b3;
        }
        .order-table th {
            background-color: #007bff;
            color: white;
        }
        .order-table td {
            vertical-align: middle;
        }
        .no-orders {
            text-align: center;
            color: #6c757d;
            padding: 50px;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <section class="order-list-section">
        <div class="container">
            <h2 class="text-center mb-4" style="color: #007bff;">Order List</h2>
            <c:choose>
                <c:when test="${not empty orderList}">
                    <c:forEach var="order" items="${orderList}">
                        <div class="order-card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-2">
                                        <strong>ID:</strong> ${order.id}
                                    </div>
                                    <div class="col-md-3">
                                        <strong>Order Number:</strong> ${order.orderNumber}
                                    </div>
                                    <div class="col-md-2">
                                        <strong>Total:</strong> $${order.total}
                                    </div>
                                    <div class="col-md-2">
                                        <strong>Status:</strong> <span class="badge bg-${order.status == 'PENDING' ? 'warning' : order.status == 'COMPLETED' ? 'success' : 'secondary'}">${order.status}</span>
                                    </div>
                                    <div class="col-md-3 text-end">
                                        <a href="${pageContext.request.contextPath}/orderEdit?id=${order.id}" class="btn btn-custom">Edit</a>
                                    </div>
                                    <div class="col-md-3 text-end">
                                        <a href="${pageContext.request.contextPath}/orderDetail?id=${order.id}" class="btn btn-custom">View Detail</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="no-orders">
                        <h4>No orders found.</h4>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </section>

    <jsp:include page="footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>