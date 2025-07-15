<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Phương thức thanh toán</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .checkout-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .section-title { font-weight: bold; font-size: 22px; margin-bottom: 20px; }
    </style>
</head>
<body>
<jsp:include page="header.jsp" />
<div class="checkout-container">
    <h2 class="section-title text-center">Chọn Phương Thức Thanh Toán</h2>
    <div class="summary mb-4">
        <p><strong>Địa chỉ giao hàng:</strong> ${param.shippingAddress}</p>
        <p><strong>Phí vận chuyển:</strong> ${param.shippingFee} VND</p>
        <p><strong>Tổng cộng:</strong> ${param.total} VND</p>
    </div>
    <form action="<%=request.getContextPath()%>/payment" method="post">
        <input type="hidden" name="shippingAddress" value="${param.shippingAddress}">
        <input type="hidden" name="subtotal" value="${param.subtotal}">
        <input type="hidden" name="shippingFee" value="${param.shippingFee}">
        <input type="hidden" name="total" value="${param.total}">

        <div class="form-group mb-3">
            <label>Phương thức thanh toán:</label>
            <select class="form-select" name="paymentMethod" required>
                <option value="VNPAY">VNPAY</option>
                <option value="CARD">COD</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary w-100">Đặt Hàng</button>
    </form>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>
