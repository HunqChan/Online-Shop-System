<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Order</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="container mt-5 mb-5">
    <h3 class="mb-4">Edit Order</h3>
    <form method="post" action="orderEdit">
        <input type="hidden" name="orderId" value="${order.id}"/>

        <div class="row mb-3">
            <div class="col-md-6">
                <label class="form-label">Order ID</label>
                <input type="text" class="form-control" value="#ORD${order.id}" readonly>
            </div>
            <div class="col-md-6">
                <label class="form-label">Name</label>
                <input type="text" class="form-control" value="${user.fullName}" readonly>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-md-6">
                <label class="form-label">Phone</label>
                <input type="text" class="form-control" value="${user.phoneNumber}" readonly>
            </div>
            <div class="col-md-6">
                <label class="form-label">Shipping Address</label>
                <input type="text" class="form-control" name="shippingAddress" value="${order.shippingAddress}">
            </div>
        </div>
        <div class="row mb-4">
            <div class="col-md-6">
                <label class="form-label">Order Status</label>
                <select class="form-select" name="status">
                    <option ${order.status == 'Processing' ? 'selected' : ''}>Processing</option>
                    <option ${order.status == 'Completed' ? 'selected' : ''}>Completed</option>
                </select>
            </div>
            <div class="col-md-6">
                <label class="form-label">Order Date</label>
                <input type="text" class="form-control" value="${order.createdAt}" readonly>
            </div>
        </div>

        <h5 class="mb-3">Products</h5>
        <table class="table table-bordered">
            <thead class="table-light">
            <tr>
                <th>Product</th>
                <th style="width:100px;">Quantity</th>
                <th style="width:120px;">Unit Price</th>
                <th style="width:120px;">Total</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="grandTotal" value="0"/>
            <c:forEach var="item" items="${items}">
                <c:set var="lineTotal" value="${item.quantity * item.price}"/>
                <c:set var="grandTotal" value="${grandTotal + lineTotal}"/>
                <tr>
                    <td>${item.productName}</td>
                    <td>
                        <input type="number" class="form-control" name="quantity" value="${item.quantity}">
                        <input type="hidden" name="itemId" value="${item.id}">
                    </td>
                    <td>
                        <input type="text" class="form-control" name="price" value="${item.price}">
                    </td>
                    <td>${lineTotal}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="text-end mb-4">
            <strong>Total Amount:</strong> ${grandTotal} VND
        </div>

        <div class="mb-4">
            <label class="form-label">Order Note</label>
            <textarea class="form-control" name="note" rows="2">${order.note}</textarea>
        </div>

        <h5 class="mb-3">Shipping Information</h5>
        <div class="row mb-3">
            <div class="col-md-4">
                <label class="form-label">Shipping Method</label>
                <select class="form-select" name="shippingMethod">
                    <option ${order.shippingMethod == 'GHN' ? 'selected' : ''}>GHN</option>
                    <option ${order.shippingMethod == 'GHTK' ? 'selected' : ''}>GHTK</option>
                </select>
            </div>
            <div class="col-md-4">
                <label class="form-label">Tracking Number</label>
                <input type="text" class="form-control" name="trackingNumber" value="${order.trackingNumber}">
            </div>
            <div class="col-md-4">
                <label class="form-label">Shipping Status</label>
                <select class="form-select" name="shippingStatus">
                    <option ${order.shippingStatus == 'Not picked up' ? 'selected' : ''}>Not picked up</option>
                    <option ${order.shippingStatus == 'Picked up' ? 'selected' : ''}>Picked up</option>
                </select>
            </div>
        </div>

        <div class="text-center">
            <button type="submit" class="btn btn-success">Save Changes</button>
            <a href="orderList" class="btn btn-secondary">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>