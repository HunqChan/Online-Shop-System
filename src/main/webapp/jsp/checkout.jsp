<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script>
        function toggleBankCodeField() {
            var paymentMethod = document.getElementById("paymentMethod").value;
            var bankCodeField = document.getElementById("bankCodeField");
            bankCodeField.style.display = paymentMethod === "VNPAY" ? "block" : "none";
        }
    </script>
</head>
<body onload="toggleBankCodeField()">
    <jsp:include page="header.jsp" />
    <section class="checkout-section">
        <div class="container">
            <h2>Checkout</h2>
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card p-4">
                        <div class="card-body">
                            <p class="card-text">Subtotal: $${subtotal}</p>
                            <p class="card-text">Shipping Fee: $${shippingFee}</p>
                            <h3 class="card-title">Total: $${total}</h3>
                            <form action="${pageContext.request.contextPath}/order" method="post">
                                <div class="form-group mb-3">
                                    <label for="shippingAddress" class="form-label">Shipping Address:</label>
                                    <input type="text" class="form-control" id="shippingAddress" name="shippingAddress" required>
                                </div>
                                <div class="form-group mb-3">
                                    <label for="paymentMethod" class="form-label">Payment Method:</label>
                                    <select class="form-select" id="paymentMethod" name="paymentMethod" onchange="toggleBankCodeField()" required>
                                        <option value="COD">Cash on Delivery (COD)</option>
                                        <option value="VNPAY">VNPAY QR Code</option>
                                        <option value="CARD">Thẻ ngân hàng</option>
                                    </select>
                                </div>
                                
                                <button type="submit" class="btn btn-primary">Place Order</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <jsp:include page="footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>