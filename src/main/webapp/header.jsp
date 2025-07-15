<!--================ Start Header Menu Area =================-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="header_area">
    <div class="main_menu">
        <nav class="navbar navbar-expand-lg navbar-light">
            <div class="container">
                <a class="navbar-brand logo_h" href="${pageContext.request.contextPath}/home">
                    <img src="${pageContext.request.contextPath}/assets/img/logo.png" alt="">
                </a>
                <button class="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="collapse navbar-collapse offset" id="navbarSupportedContent">
                    <ul class="nav navbar-nav menu_nav ml-auto mr-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/home">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/productList">Products</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/cart">Cart</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/orderList">Orders</a>
                        </li>
                        <c:choose>
                            <c:when test="${not empty sessionScope.user}">
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>

                    <ul class="nav-shop">
                        <li class="nav-item">
                            <button><i class="ti-search"></i></button>
                        </li>
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/cart">
                                <i class="ti-shopping-cart"></i>
                                <span class="nav-shop__circle">3</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</header>
<!--================ End Header Menu Area =================-->
