<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Aroma Shop - Register</title>
    <link rel="icon" href="img/Fevicon.png" type="image/png">
    <jsp:include page="common-css.jsp"/>
</head>
<body>

<!--================Register Box Area =================-->
<section class="login_box_area section-margin">
    <div class="container">
        <div class="row d-flex align-items-stretch">
            <div class="col-lg-6 d-flex h-100">
                <div class="login_box_img w-100">
                    <div class="hover">
                        <h4>Already have an account?</h4>
                        <p>There are advances being made in science and technology everyday, and a good example of this
                            is the</p>
                        <a class="button button-account" href="${pageContext.request.contextPath}/login">Log In</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-6 d-flex h-100">
                <div class="login_form_inner w-100">
                    <h3>Create an account</h3>
                    <form class="row login_form" action="${pageContext.request.contextPath}/register" method="post"
                          id="registerForm">
                        <div class="col-md-12 form-group">
                            <input type="text" class="form-control" name="full_name" placeholder="Full Name"
                                   onfocus="this.placeholder = ''" onblur="this.placeholder = 'Full Name'" required>
                        </div>
                        <div class="col-md-12 form-group">
                            <input type="email" class="form-control" name="email" placeholder="Email"
                                   onfocus="this.placeholder = ''" onblur="this.placeholder = 'Email'" required>
                        </div>
                        <div class="col-md-6 form-group">
                            <input type="password" class="form-control" name="password" placeholder="Password"
                                   onfocus="this.placeholder = ''" onblur="this.placeholder = 'Password'" required>
                        </div>
                        <div class="col-md-6 form-group">
                            <input type="password" class="form-control" name="confirm_password"
                                   placeholder="Confirm Password"
                                   onfocus="this.placeholder = ''" onblur="this.placeholder = 'Confirm Password'"
                                   required>
                        </div>
                        <div class="col-md-12 form-group">
                            <input type="text" class="form-control" name="phone_number" placeholder="Phone Number"
                                   onfocus="this.placeholder = ''" onblur="this.placeholder = 'Phone Number'">
                        </div>
                        <div class="col-md-12 form-group d-flex align-items-center">
                            <label class="mr-3 mb-0">Gender:</label>
                            <div class="form-check form-check-inline mb-0">
                                <input class="form-check-input" type="radio" name="gender" id="male" value="1" required>
                                <label class="form-check-label" for="male">Male</label>
                            </div>
                            <div class="form-check form-check-inline mb-0">
                                <input class="form-check-input" type="radio" name="gender" id="female" value="0">
                                <label class="form-check-label" for="female">Female</label>
                            </div>
                        </div>

                        <!-- Nút Đăng ký -->
                        <div class="col-md-12 form-group">
                            <button type="submit" class="button button-register w-100 py-2">
                                Register
                            </button>
                        </div>

                        <!-- Nút Đăng ký bằng Google -->
                        <div class="col-md-12 form-group">
                            <a href="${pageContext.request.contextPath}/login-google"
                               class="btn btn-outline-danger w-100 d-flex align-items-center justify-content-center gap-2 py-2">
                                <img src="https://developers.google.com/identity/images/g-logo.png"
                                     alt="Google Logo" style="height: 20px; width: 20px;">
                                <span>Sign up with Google</span>
                            </a>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<!--================End Register Box Area =================-->

<jsp:include page="common-scripts.jsp"/>
</body>
</html>
