
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Chi Tiết Đơn Hàng - #${order.id}</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Arial', sans-serif;
        }
        .order-detail-section {
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
        .order-table th {
            background-color: #007bff;
            color: white;
        }
        .order-table td {
            vertical-align: middle;
        }
        .no-items {
            text-align: center;
            color: #6c757d;
            padding: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <section class="order-detail-section">
        <div class="container">
            <h2 class="text-center mb-4" style="color: #007bff;">Chi Tiết Đơn Hàng - #${order.id}</h2>
            <c:if test="${order != null}">
                <div class="order-card">
                    <div class="card-body">
                        <p><strong>Mã Đơn Hàng:</strong> ${order.orderNumber}</p>
                        <p><strong>Thời Gian Tạo:</strong> <fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm:ss" /></p>
                        <p><strong>Địa Chỉ Giao Hàng:</strong> ${order.shippingAddress}</p>
                        <p><strong>Phương Thức Thanh Toán:</strong> ${order.paymentMethod}</p>
                        <p><strong>Trạng Thái Thanh Toán:</strong> ${order.paymentStatus}</p>
                        <p><strong>Phí Vận Chuyển:</strong> $${order.shippingFee}</p>
                        <p><strong>Tổng Sản Phẩm:</strong> $${order.subtotal}</p>
                        <p><strong>Tổng Cộng:</strong> $${order.total}</p>
                        <p><strong>Trạng Thái:</strong> <span class="badge bg-${order.status == 'PENDING' ? 'warning' : order.status == 'COMPLETED' ? 'success' : 'secondary'}">${order.status}</span></p>
                        <p><strong>Cập Nhật Lần Cuối:</strong> <fmt:formatDate value="${order.updatedAt}" pattern="dd/MM/yyyy HH:mm:ss" /></p>
                    </div>
                </div>
            </c:if>
            <h3 class="mt-4">Danh Sách Sản Phẩm</h3>
            <c:choose>
                <c:when test="${not empty orderItems}">
                    <table class="table table-bordered order-table">
                        <thead>
                            <tr>
                                <th>Tên Sản Phẩm</th>
                                <th>Giá</th>
                                <th>Số Lượng</th>
                                <th>Tổng</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${orderItems}">
                                <tr>
                                    <td>${item.productName}</td>
                                    <td>$${item.price}</td>
                                    <td>${item.quantity}</td>
                                    <td>$${item.price * item.quantity}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="no-items">
                        <h5>Không có sản phẩm trong đơn hàng này.</h5>
                    </div>
                </c:otherwise>
            </c:choose>
            <div class="text-end mt-3">
                <a href="${pageContext.request.contextPath}/orderList" class="btn btn-secondary">Quay Lại</a>
            </div>
        </div>
    </section>

    <jsp:include page="footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>