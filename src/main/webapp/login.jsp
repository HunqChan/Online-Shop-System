<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Aroma Shop - Login</title>
    <link rel="icon" href="img/Fevicon.png" type="image/png">
    <jsp:include page="common-css.jsp"/>
</head>
<body>

<!--================Login Box Area =================-->
<section class="login_box_area section-margin">
    <div class="container">
        <div class="row">
            <div class="col-lg-6">
                <div class="login_box_img">
                    <div class="hover">
                        <h4>New to our website?</h4>
                        <p>There are advances being made in science and technology everyday, and a good example of this
                            is the</p>
                        <a class="button button-account" href="/register">Create an Account</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="login_form_inner">
                    <h3>Log in to enter</h3>
                    <form class="row login_form" action="${pageContext.request.contextPath}/login" method="post"
                          id="loginForm">
                        <div class="col-md-12 form-group">
                            <input type="email" class="form-control" name="email" placeholder="Email"
                                   onfocus="this.placeholder = ''" onblur="this.placeholder = 'Email'" required>
                        </div>
                        <div class="col-md-12 form-group">
                            <input type="password" class="form-control" name="password" placeholder="Password"
                                   onfocus="this.placeholder = ''" onblur="this.placeholder = 'Password'" required>
                        </div>
                        <div class="col-md-12 form-group">
                            <button type="submit" class="button button-login w-100">Log In</button>
                        </div>
                        <div class="col-md-12 form-group">
                            <a href="${pageContext.request.contextPath}/login-google"
                               class="btn btn-outline-danger w-100">
                                <img src="https://developers.google.com/identity/images/g-logo.png" alt="Google Logo">
                                Sign in with Google
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<!--================End Login Box Area =================-->

<jsp:include page="common-scripts.jsp"/>
</body>
</html>
