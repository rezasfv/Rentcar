<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="header">
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <div class="header-brand">
                <a href="${pageContext.request.contextPath}">
                    <!-- Image Logo -->
                    <img src="${pageContext.request.contextPath}/img/logo.png" alt="Company Logo" class="img-brand company-logo">
                </a>
                    <h1 class="navbar-brand">Rent Car</h1>
            </div>
            <div class="navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}"><i class="fa-solid fa-house"></i> Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/search-car"><i class="fa-solid fa-magnifying-glass"></i> Search</a>
                    </li>

                    <c:choose>
                        <c:when test="${user1 == null }">
                            <!-- Display login and register buttons if user is not logged in -->
                            <li class="nav-item">
                                <a href="${pageContext.request.contextPath}/login" class="nav-link"><i class="fa-solid fa-user"></i> Login</a>
                            </li>
                            <li class="nav-item">
                                <a href="${pageContext.request.contextPath}/create-customer" class="nav-link"><i class="fa-solid fa-address-card"></i> Register</a>
                            </li>
                            </c:when>
                            <c:otherwise>
                                <!-- Display logout button or user profile link if user is logged in -->
                            <li class="nav-item">
                                <a href="${pageContext.request.contextPath}/dashboard" class="nav-link"><i class="fa-solid fa-gauge"></i> Dashboard</a>
                            </li>
                            <li class="nav-item">
                                <a href="${pageContext.request.contextPath}/logout" class="nav-link"><i class="fa-solid fa-right-from-bracket"></i> Logout</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    <!-- Add more links as needed -->
                </ul>
            </div>
        </div>
    </nav>
</header>