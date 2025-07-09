<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lỗi xử lý</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fefefe;
            color: #333;
            padding: 40px;
        }
        .error-box {
            border: 2px solid #e74c3c;
            background-color: #fdecea;
            padding: 20px;
            max-width: 600px;
            margin: auto;
            border-radius: 10px;
        }
        h1 {
            color: #c0392b;
        }
        a {
            display: inline-block;
            margin-top: 15px;
            text-decoration: none;
            color: #2980b9;
        }
    </style>
</head>
<body>
    <div class="error-box">
        <h1>Đã xảy ra lỗi</h1>
        <p>${errorMessage}</p>
        <a href="<%= request.getContextPath() %>/">Quay lại trang chủ</a>
    </div>
</body>
</html>
