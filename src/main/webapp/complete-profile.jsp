<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String fullName = (String) session.getAttribute("google_user_name");
    String email = (String) session.getAttribute("google_user_email");
    String avatarUrl = (String) session.getAttribute("google_user_avatar");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Complete Profile</title>
    <jsp:include page="common-css.jsp"/>
</head>
<body>

<section class="login_box_area section-margin">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-6">
                <div class="login_form_inner">
                    <h3>Complete Your Profile</h3>
                    <form class="row login_form" method="post"
                          action="${pageContext.request.contextPath}/register-google" id="completeProfileForm">
                        <div class="col-md-12 form-group text-center mb-3">
                            <img src="<%= avatarUrl %>" alt="Avatar" style="max-width: 100px; border-radius: 50%;">
                        </div>
                        <div class="col-md-12 form-group">
                            <input type="text" class="form-control" value="<%= fullName %>" readonly>
                        </div>
                        <div class="col-md-12 form-group">
                            <input type="email" class="form-control" value="<%= email %>" readonly>
                        </div>
                        <div class="col-md-12 form-group">
                            <input type="text" class="form-control" name="phone" placeholder="Phone number"
                                   onfocus="this.placeholder=''" onblur="this.placeholder='Phone number'" required>
                        </div>
                        <div class="col-md-12 form-group d-flex align-items-center">
                            <label class="mr-3 mb-0">Gender:</label>

                            <div class="form-check form-check-inline mb-0">
                                <input class="form-check-input" type="radio" name="gender" id="male" value="1" required>
                                <label class="form-check-label" for="male">Male</label>
                            </div>

                            <div class="form-check form-check-inline mb-0 ml-3">
                                <input class="form-check-input" type="radio" name="gender" id="female" value="0"
                                       required>
                                <label class="form-check-label" for="female">Female</label>
                            </div>
                        </div>

                        <div class="col-md-12 form-group mt-3">
                            <button type="submit" class="button button-login w-100">Finish</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="common-scripts.jsp"/>
</body>
</html>
