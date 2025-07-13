<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%@ include file="common-css.jsp" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Update Profile</title>
</head>
<body data-context-path="<%= request.getContextPath() %>">
<jsp:include page="header.jsp"/>
<main class="site-main">
    <section class="section-margin--small mt-5 mb-5">
        <div class="container">
            <div class="row">
                <!-- Avatar upload -->
                <div class="col-md-12 text-center mb-5">
                    <form action="upload-avatar" method="post" enctype="multipart/form-data" id="avatarForm">
                        <img src="<%= user.getAvatarUrl() %>" alt="Avatar" class="rounded-circle mb-3" width="120"
                             height="120">
                        <div class="d-flex justify-content-center">
                            <input type="file" name="avatar" class="form-control-file"
                                   onchange="document.getElementById('avatarForm').submit()">
                        </div>
                    </form>
                </div>

                <!-- Profile update form -->
                <div class="col-lg-12">
                    <form action="update-profile" method="post" class="row">
                        <!-- Left -->
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="fullName">Full Name</label>
                                <input type="text" class="form-control" name="fullName"
                                       value="<%= user.getFullName() %>">
                            </div>
                            <div class="form-group">
                                <label for="phoneNumber">Phone Number</label>
                                <input type="text" class="form-control" name="phoneNumber"
                                       value="<%= user.getPhoneNumber() %>">
                            </div>
                            <div class="form-group">
                                <label for="province">Province</label>
                                <select id="province" name="provinceId" class="form-control"
                                        data-selected="<%= user.getProvinceId() != null ? user.getProvinceId() : "" %>"></select>
                            </div>
                            <div class="form-group">
                                <label for="district">District</label>
                                <select id="district" name="districtId" class="form-control"
                                        data-selected="<%= user.getDistrictId() != null ? user.getDistrictId() : "" %>"></select>
                            </div>
                            <div class="form-group">
                                <label for="ward">Ward</label>
                                <select id="ward" name="wardCode" class="form-control"
                                        data-selected="<%= user.getWardCode() != null ? user.getWardCode() : "" %>"></select>
                            </div>
                            <input type="hidden" name="provinceName" id="provinceName"
                                   value="<%= user.getProvinceName() %>">
                            <input type="hidden" name="districtName" id="districtName"
                                   value="<%= user.getDistrictName() %>">
                            <input type="hidden" name="wardName" id="wardName" value="<%= user.getWardName() %>">

                        </div>

                        <!-- Right -->
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Email</label>
                                <input type="email" class="form-control" value="<%= user.getEmail() %>" readonly>
                            </div>
                            <div class="form-group">
                                <label for="gender">Gender</label><br>
                                <input type="radio" name="gender" id="male" value="true"
                                    <%= user.getGender() != null && user.getGender() ? "checked" : "" %>>
                                <label for="male">Male</label>

                                <input type="radio" name="gender" id="female" value="false"
                                    <%= user.getGender() != null && !user.getGender() ? "checked" : "" %>>
                                <label for="female">Female</label>
                            </div>
                            <div class="form-group">
                                <label for="detailAddress">Detail Address</label>
                                <input type="text" class="form-control" name="detailAddress"
                                       value="<%= user.getDetailAddress() %>">
                            </div>
                            <div class="form-group">
                                <a href="change-password" class="btn btn-link">Change Password</a>
                            </div>
                        </div>

                        <!-- Submit -->
                        <div class="col-12 text-center mt-4">
                            <button type="submit" class="button button-login w-25">Update Profile</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</main>
<jsp:include page="footer.jsp"/>
<jsp:include page="common-scripts.jsp"/>
<script>
    window.contextPath = '<%= request.getContextPath() %>';
</script>
<script src="js/update-profile.js"></script>

</body>
</html>
