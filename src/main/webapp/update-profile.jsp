<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Profile</title>
    <jsp:include page="common-css.jsp"/>
    <script>
        window.contextPath = "<%= request.getContextPath() %>";
    </script>
</head>
<body data-context-path="${pageContext.request.contextPath}">
<jsp:include page="header.jsp"/>

<main class="site-main">
    <section class="section-margin">
        <div class="container">
            <h2 class="mb-4">Update Profile</h2>

            <% if (request.getAttribute("message") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("message") %>
            </div>
            <% } %>
            <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger"><%= request.getAttribute("error") %>
            </div>
            <% } %>
            <% if (request.getAttribute("validationErrors") != null) { %>
            <div class="alert alert-danger">
                <ul>
                    <% for (String err : (List<String>) request.getAttribute("validationErrors")) { %>
                    <li><%= err %></li>
                    <% } %>
                </ul>
            </div>
            <% } %>

            <div class="row">
                <!-- Avatar Upload -->
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body text-center">
                            <img id="avatarPreview"
                                 src="<%= user.getAvatarUrl() != null ? user.getAvatarUrl() : "img/default-avatar.png" %>"
                                 alt="Avatar"
                                 class="rounded-circle mb-3" style="width: 150px; height: 150px; object-fit: cover;">

                            <form action="${pageContext.request.contextPath}/upload-avatar" method="post"
                                  enctype="multipart/form-data">
                                <input type="file"
                                       name="avatar"
                                       accept="image/png, image/jpeg, image/jpg, image/gif"
                                       class="form-control-file"
                                       onchange="if(this.files.length > 0) this.form.submit();">
                            </form>
                        </div>
                    </div>
                </div>

                <!-- User Info Form -->
                <div class="col-md-8">
                    <form action="update-profile" method="post">
                        <div class="form-row">
                            <!-- Full Name -->
                            <div class="form-group col-md-6">
                                <label for="fullName">Full Name</label>
                                <input type="text" class="form-control" id="fullName" name="full_name"
                                       value="<%= user.getFullName() %>" required>
                            </div>

                            <!-- Email -->
                            <div class="form-group col-md-6">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" name="email"
                                       value="<%= user.getEmail() %>" required>
                            </div>
                        </div>

                        <div class="form-row">
                            <!-- Phone Number -->
                            <div class="form-group col-md-6">
                                <label for="phoneNumber">Phone</label>
                                <input type="text" class="form-control" id="phoneNumber" name="phone_number"
                                       value="<%= user.getPhoneNumber() != null ? user.getPhoneNumber() : "" %>">
                            </div>

                            <!-- Gender -->
                            <div class="form-group col-md-6">
                                <label>Gender</label><br>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="gender" id="male" value="true"
                                        <%= Boolean.TRUE.equals(user.getGender()) ? "checked" : "" %>>
                                    <label class="form-check-label" for="male">Male</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="gender" id="female" value="false"
                                        <%= Boolean.FALSE.equals(user.getGender()) ? "checked" : "" %>>
                                    <label class="form-check-label" for="female">Female</label>
                                </div>
                            </div>
                        </div>

                        <!-- Address Selection -->
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <label for="province">Province</label>
                                <select name="province_id" id="province" class="form-control">
                                    <option value="">Select province</option>
                                </select>
                                <input type="hidden" name="province_name" id="provinceName"
                                       value="<%= user.getProvinceName() != null ? user.getProvinceName() : "" %>">
                            </div>

                            <div class="form-group col-md-4">
                                <label for="district">District</label>
                                <select name="district_id" id="district" class="form-control">
                                    <option value="">Select district</option>
                                </select>
                                <input type="hidden" name="district_name" id="districtName"
                                       value="<%= user.getDistrictName() != null ? user.getDistrictName() : "" %>">
                            </div>

                            <div class="form-group col-md-4">
                                <label for="ward">Ward</label>
                                <select name="ward_code" id="ward" class="form-control">
                                    <option value="">Select ward</option>
                                </select>
                                <input type="hidden" name="ward_name" id="wardName"
                                       value="<%= user.getWardName() != null ? user.getWardName() : "" %>">
                            </div>
                        </div>

                        <!-- Detail Address -->
                        <div class="form-group">
                            <label for="detailAddress">Detail Address</label>
                            <input type="text" class="form-control" id="detailAddress" name="detail_address"
                                   value="<%= user.getDetailAddress() != null ? user.getDetailAddress() : "" %>">
                        </div>

                        <button type="submit" class="btn btn-primary">Update Profile</button>
                    </form>
                </div>
            </div>
        </div>
    </section>
</main>

<jsp:include page="footer.jsp"/>
<jsp:include page="common-scripts.jsp"/>
<script src="js/update-profile.js"></script>
</body>
</html>
