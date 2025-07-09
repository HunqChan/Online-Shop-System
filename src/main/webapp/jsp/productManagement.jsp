<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý sản phẩm</title>
    <!-- Tailwind CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <!-- Toastify CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f4f6f9;
        }
        .table-container {
            max-height: 400px;
            overflow-y: auto;
        }
        .table th, .table td {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .form-container input, .form-container textarea, .form-container select {
            transition: all 0.2s ease-in-out;
        }
        .form-container input:focus, .form-container textarea:focus, .form-container select:focus {
            border-color: #3b82f6;
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
        }
        .toastify {
            font-size: 14px;
        }
        .product-image {
            height: 64px;
            width: 64px;
            object-fit: cover;
            border-radius: 4px;
        }
    </style>
</head>
<body class="container mx-auto p-6">
    <h2 class="text-2xl font-bold mb-6 text-gray-800">Quản lý sản phẩm</h2>

    <!-- Hiển thị thông báo -->
    <c:if test="${not empty message}">
       
    </c:if>

    <!-- Form thêm/cập nhật sản phẩm -->
    <div class="form-container bg-white p-6 rounded-lg shadow-md mb-6">
        <h3 class="text-lg font-semibold mb-4">${empty product ? 'Thêm sản phẩm mới' : 'Cập nhật sản phẩm'}</h3>
        <form id="productForm" action="manager" method="post" onsubmit="return confirmSubmit()">
            <input type="hidden" name="action" value="${empty product ? 'add' : 'update'}">
            <c:if test="${not empty product}">
                <input type="hidden" name="id" value="${product.id}">
            </c:if>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700">Tên sản phẩm:</label>
                    <input type="text" name="name" value="${product.name}" required
                           class="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700">Giá:</label>
                    <input type="number" name="price" value="${product.price}" step="0.01" required
                           class="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700">Số lượng tồn kho:</label>
                    <input type="number" name="stock" value="${product.stock}" required
                           class="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700">Trạng thái:</label>
                    <select name="active" class="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                        <option value="true" ${product.active ? 'selected' : ''}>Hoạt động</option>
                        <option value="false" ${!product.active ? 'selected' : ''}>Không hoạt động</option>
                    </select>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700">Hình ảnh:</label>
                    <input type="file" id="imageUpload" accept="image/*"
                           class="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <input type="hidden" id="imageUrl" name="imageUrl" value="${product.imageUrl}">
                    <img id="imagePreview" src="${product.imageUrl != null && !product.imageUrl.isEmpty() ? product.imageUrl : 'https://via.placeholder.com/64'}" alt="Preview" class="product-image mt-2"/>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700">Trọng lượng (g):</label>
                    <input type="number" name="weight" value="${empty product ? '1000' : product.weight}"
                           class="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700">Chiều dài (cm):</label>
                    <input type="number" name="length" value="${empty product ? '50' : product.length}"
                           class="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700">Chiều rộng (cm):</label>
                    <input type="number" name="width" value="${empty product ? '30' : product.width}"
                           class="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700">Chiều cao (cm):</label>
                    <input type="number" name="height" value="${empty product ? '20' : product.height}"
                           class="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>
                <div class="md:col-span-2">
                    <label class="block text-sm font-medium text-gray-700">Mô tả:</label>
                    <textarea name="description" class="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-500">${product.description}</textarea>
                </div>
            </div>
            <div class="mt-4 flex space-x-4">
                <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600">${empty product ? 'Thêm sản phẩm' : 'Cập nhật sản phẩm'}</button>
                <button type="button" onclick="resetForm()" class="bg-gray-300 text-gray-700 px-4 py-2 rounded-md hover:bg-gray-400">Làm mới</button>
            </div>
        </form>
    </div>

    <!-- Danh sách sản phẩm -->
    <h3 class="text-lg font-semibold mb-4">Danh sách sản phẩm</h3>
    <div class="table-container bg-white rounded-lg shadow-md">
        <table class="table w-full">
            <thead>
                <tr class="bg-gray-100">
                    <th class="p-3">ID</th>
                    <th class="p-3">Tên</th>
                    <th class="p-3">Mô tả</th>
                    <th class="p-3">Giá</th>
                    <th class="p-3">Tồn kho</th>
                    <th class="p-3">Hình ảnh</th>
                    <th class="p-3">Trạng thái</th>
                    <th class="p-3">Trọng lượng</th>
                    <th class="p-3">Kích thước (LxWxH)</th>
                    <th class="p-3">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${products}">
                    <tr class="hover:bg-gray-50">
                        <td class="p-3">${p.id}</td>
                        <td class="p-3">${p.name}</td>
                        <td class="p-3">${p.description}</td>
                        <td class="p-3">${p.price}</td>
                        <td class="p-3">${p.stock}</td>
                        <td class="p-3">
                            <img src="${p.imageUrl != null && !p.imageUrl.isEmpty() ? p.imageUrl : 'https://via.placeholder.com/64'}" alt="Product Image" class="product-image"/>
                        </td>
                        <td class="p-3">${p.active ? 'Hoạt động' : 'Không hoạt động'}</td>
                        <td class="p-3">${p.weight}</td>
                        <td class="p-3">${p.length}x${p.width}x${p.height}</td>
                        <td class="p-3">
                            <a href="manager?action=edit&id=${p.id}" class="text-blue-500 hover:underline">Sửa</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Toastify JS CDN -->
    <script src="https://cdn.jsdelivr.net/npm/toastify-js"></script>
    
</body>
</html>