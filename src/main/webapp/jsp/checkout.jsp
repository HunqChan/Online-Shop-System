<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Thông tin giao hàng</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; font-family: 'Arial', sans-serif; }
        .checkout-container {
            max-width: 700px;
            margin: 50px auto;
            padding: 20px;
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .section-title { color: #333; font-weight: 600; margin-bottom: 20px; }
    </style>
</head>
<body>
<jsp:include page="header.jsp" />
<div class="checkout-container">
    <h2 class="section-title text-center">Thông Tin Giao Hàng</h2>
    <form action="jsp/payment.jsp" method="post" id="shippingForm">
        <div class="form-group mb-3">
            <label for="provinceId">Tỉnh/Thành phố:</label>
            <select class="form-select" id="provinceId" onchange="loadDistricts()">
                <option value="">Chọn Tỉnh/Thành</option>
            </select>
        </div>
        <div class="form-group mb-3">
            <label for="toDistrictId">Quận/Huyện:</label>
            <select class="form-select" id="toDistrictId" onchange="loadWards()">
                <option value="">Chọn Quận/Huyện</option>
            </select>
        </div>
        <div class="form-group mb-3">
            <label for="toWardCode">Phường/Xã:</label>
            <select class="form-select" id="toWardCode">
                <option value="">Chọn Phường/Xã</option>
            </select>
        </div>
        <div class="form-group mb-3">
            <label for="shippingAddress">Địa chỉ giao hàng:</label>
            <input type="text" class="form-control" id="shippingAddress" name="shippingAddress" required>
        </div>

        <input type="hidden" id="subtotal" name="subtotal" value="100000">
        <input type="hidden" id="shippingFee" name="shippingFee">
        <input type="hidden" id="total" name="total">

        <button type="button" class="btn btn-primary w-100" onclick="calculateShippingFee()">Tính Phí Vận Chuyển & Tiếp Tục</button>
    </form>
</div>
<jsp:include page="footer.jsp" />
<script>
    const fromDistrictId = 1482;
    const fromWardCode = "21012";

    window.onload = () => loadProvinces();

    function loadProvinces() {
        fetch('ghn?type=province')
            .then(res => res.json())
            .then(data => {
                const select = document.getElementById('provinceId');
                data.data.forEach(province => {
                    const option = document.createElement('option');
                    option.value = province.ProvinceID;
                    option.text = province.ProvinceName;
                    select.appendChild(option);
                });
            });
    }

    function loadDistricts() {
        const provinceId = document.getElementById('provinceId').value;
        fetch('ghn?type=district', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ province_id: parseInt(provinceId) })
        })
        .then(res => res.json())
        .then(data => {
            const select = document.getElementById('toDistrictId');
            select.innerHTML = '<option value="">Chọn Quận/Huyện</option>';
            data.data.forEach(district => {
                const option = document.createElement('option');
                option.value = district.DistrictID;
                option.text = district.DistrictName;
                select.appendChild(option);
            });
        });
    }

    function loadWards() {
        const districtId = document.getElementById('toDistrictId').value;
        fetch('ghn?type=ward', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ district_id: parseInt(districtId) })
        })
        .then(res => res.json())
        .then(data => {
            const select = document.getElementById('toWardCode');
            select.innerHTML = '<option value="">Chọn Phường/Xã</option>';
            data.data.forEach(ward => {
                const option = document.createElement('option');
                option.value = ward.WardCode;
                option.text = ward.WardName;
                select.appendChild(option);
            });
        });
    }

    function calculateShippingFee() {
        const toDistrictId = document.getElementById('toDistrictId').value;
        const toWardCode = document.getElementById('toWardCode').value;

        fetch('order/calculateShippingFee', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                from_district_id: fromDistrictId,
                from_ward_code: fromWardCode,
                to_district_id: parseInt(toDistrictId),
                to_ward_code: toWardCode,
                service_id: 53321,
                weight: 2000,
                length: 50,
                width: 30,
                height: 20,
                insurance_value: 100000,
                cod_value: 0
            })
        })
        .then(res => res.json())
        .then(data => {
            if (data.data && data.data.total) {
                const shippingFee = data.data.total;
                const subtotal = 100000;
                const total = subtotal + shippingFee;

                document.getElementById('shippingFee').value = shippingFee;
                document.getElementById('total').value = total;

                document.getElementById('shippingForm').submit();
            } else {
                alert("Không tính được phí vận chuyển.");
            }
        })
        .catch(err => {
            console.error("Lỗi tính phí vận chuyển:", err);
            alert("Lỗi hệ thống.");
        });
    }
</script>
</body>
</html>
