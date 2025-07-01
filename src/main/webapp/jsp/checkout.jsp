
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Thanh Toán</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Arial', sans-serif;
        }
        .checkout-container {
            max-width: 700px;
            margin: 50px auto;
            padding: 20px;
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .section-title {
            color: #333;
            font-weight: 600;
            margin-bottom: 20px;
        }
        .form-group label {
            font-weight: 500;
            color: #555;
        }
        .form-select, .form-control {
            border-radius: 5px;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
            padding: 10px 20px;
            font-weight: 500;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .summary-item {
            display: flex;
            justify-content: space-between;
            padding: 10px 0;
            border-bottom: 1px solid #eee;
        }
        .summary-item:last-child {
            border-bottom: none;
            font-weight: 600;
            color: #007bff;
        }
        #orderSection {
            display: none;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />
    <div class="checkout-container">
        <h2 class="section-title text-center">Thanh Toán Đơn Hàng</h2>
        <div class="row">
            <div class="col-12">
                <div id="calculateFeeSection">
                    <h4 class="section-title">Chọn Địa Điểm Giao Hàng</h4>
                    <div class="form-group mb-3">
                        <label for="toDistrictId" class="form-label">Quận/Huyện:</label>
                        <select class="form-select" id="toDistrictId" name="toDistrictId" onchange="loadWards()" required>
                            <option value="">Chọn Quận/Huyện</option>
                            <c:forEach var="district" items="${districts}">
                                <option value="${district.id}" ${district.id == toDistrictId ? 'selected' : ''}>${district.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group mb-3">
                        <label for="toWardCode" class="form-label">Phường/Xã:</label>
                        <select class="form-select" id="toWardCode" name="toWardCode" required>
                            <option value="">Chọn Phường/Xã</option>
                            <c:forEach var="ward" items="${wards}">
                                <option value="${ward.id}" ${ward.id == toWardCode ? 'selected' : ''}>${ward.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="button" id="calculateFeeBtn" class="btn btn-primary w-100" onclick="calculateShippingFee()">Tính Phí Vận Chuyển</button>
                </div>
                <div id="orderSection">
                    <h4 class="section-title">Thông Tin Đơn Hàng</h4>
                    <div class="summary-item">
                        <span>Tổng Sản Phẩm:</span>
                        <span>$<span id="subtotal">${subtotal}</span></span>
                    </div>
                    <div class="summary-item">
                        <span>Phí Vận Chuyển:</span>
                        <span>$<span id="shippingFee">${shippingFee}</span></span>
                    </div>
                    <div class="summary-item">
                        <span>Tổng Cộng:</span>
                        <span>$<span id="total">${total}</span></span>
                    </div>
                    <form action="${pageContext.request.contextPath}/order" method="post" class="mt-4">
                        <input type="hidden" name="toDistrictId" value="${toDistrictId}">
                        <input type="hidden" name="toWardCode" value="${toWardCode}">
                        <div class="form-group mb-3">
                            <label for="paymentMethod" class="form-label">Phương Thức Thanh Toán:</label>
                            <select class="form-select" id="paymentMethod" name="paymentMethod" required>
                                <option value="VNPAY">VNPAY QR Code</option>
                                <option value="CARD">Thẻ Ngân Hàng</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Đặt Hàng</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script>
        const toDistrictId = parseInt('<c:out value="${toDistrictId}" default="0"/>');
        const toWardCode = '<c:out value="${toWardCode}" default=""/>';
        const fromDistrictId = parseInt('<c:out value="${fromDistrictId}" default="0"/>');
        const fromWardCode = '<c:out value="${fromWardCode}" default=""/>';

        function loadWards() {
            const districtId = document.getElementById("toDistrictId").value;
            console.log("Loading wards for districtId:", districtId);
            if (!districtId) {
                document.getElementById("toWardCode").innerHTML = '<option value="">Chọn Phường/Xã</option>';
                document.getElementById("calculateFeeSection").style.display = "block";
                document.getElementById("orderSection").style.display = "none";
                return;
            }
            fetch('${pageContext.request.contextPath}/GetWardsGHN?districtId=' + districtId, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' }
            })
            .then(response => {
                console.log("Wards fetch response status:", response.status);
                return response.json();
            })
            .then(wards => {
                console.log("Wards data:", wards);
                const wardSelect = document.getElementById("toWardCode");
                wardSelect.innerHTML = '<option value="">Chọn Phường/Xã</option>';
                wards.forEach(w => {
                    const selected = (w.id === toWardCode) ? 'selected' : '';
                    wardSelect.innerHTML += `<option value="${w.id}" ${selected}>${w.name}</option>`;
                });
                document.getElementById("calculateFeeSection").style.display = "block";
                document.getElementById("orderSection").style.display = "none";
            })
            .catch(error => console.error('Error loading wards:', error));
        }

        function calculateShippingFee() {
            const districtId = document.getElementById("toDistrictId").value;
            const wardCode = document.getElementById("toWardCode").value;
            console.log("Calculating shipping fee with:", { districtId, wardCode, fromDistrictId, fromWardCode });

            if (!districtId || !wardCode) {
                console.log("Missing districtId or wardCode, setting fee to $0.00");
                document.getElementById("shippingFee").textContent = "$0.00";
                document.getElementById("orderSection").style.display = "none";
                alert("Vui lòng chọn Quận/Huyện và Phường/Xã.");
                return;
            }

            fetch('${pageContext.request.contextPath}/order/calculateShippingFee', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    fromDistrictId: fromDistrictId,
                    fromWardCode: fromWardCode,
                    toDistrictId: parseInt(districtId),
                    toWardCode: wardCode,
                    serviceId: 53321,
                    weight: 2000,
                    length: 50,
                    width: 30,
                    height: 20
                })
            })
            .then(response => {
                console.log("Shipping fee fetch response status:", response.status);
                if (!response.ok) throw new Error("Network response was not ok: " + response.statusText);
                return response.json();
            })
            .then(data => {
                console.log("Shipping fee response:", data);
                if (data.success) {
                    document.getElementById("shippingFee").textContent = "$" + data.shippingFee.toFixed(2);
                    document.getElementById("orderSection").style.display = "block";
                    updateTotal();
                } else {
                    console.error('Error calculating shipping fee:', data.message);
                    document.getElementById("shippingFee").textContent = "$0.00";
                    document.getElementById("orderSection").style.display = "none";
                    alert("Lỗi khi tính phí vận chuyển: " + data.message);
                }
            })
            .catch(error => {
                console.error('Error fetching shipping fee:', error);
                document.getElementById("shippingFee").textContent = "$0.00";
                document.getElementById("orderSection").style.display = "none";
                alert("Lỗi kết nối server khi tính phí vận chuyển: " + error.message);
            });
        }

        function updateTotal() {
            const subtotal = parseFloat(document.getElementById("subtotal").textContent.replace('$', '')) || 0;
            const shippingFee = parseFloat(document.getElementById("shippingFee").textContent.replace('$', '')) || 0;
            const total = subtotal + shippingFee;
            console.log("Updating total:", { subtotal, shippingFee, total });
            document.getElementById("total").textContent = "$" + total.toFixed(2);
        }

        window.onload = function () {
            if (toDistrictId) loadWards();
            document.getElementById("orderSection").style.display = "none";
            const calculateBtn = document.getElementById("calculateFeeBtn");
            if (calculateBtn) {
                console.log("Calculate button found and enabled.");
            } else {
                console.error("Calculate button not found!");
            }
            if (toDistrictId && toWardCode) calculateShippingFee();
        };
    </script>
</body>
</html>