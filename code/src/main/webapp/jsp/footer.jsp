<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<footer class="footer">
    <div class="footer-container">
        <div class="footer-message">
            <p class="footer__text">&copy; 2024 MyWebApp. All rights reserved.</p>
        </div>
            <!-- Site map in the footer -->
        <div class="footer-sitemap">
            <p class="footer__map__text">Site Map:</p>
            <div class="footer__item">
                <ul>
                    <li><a href="${pageContext.request.contextPath}" class="nav-link">Home</a></li> 
                    <li><a href="${pageContext.request.contextPath}/search-car" class="nav-link">Search</a></li>
                    <c:choose>
                        <c:when test="${user1 == null }">
                            <!-- Display login and register buttons if user is not logged in -->
                            <li class="nav-item">
                                <a href="${pageContext.request.contextPath}/login" class="nav-link">Login</a>
                            </li>
                            <li class="nav-item">
                                <a href="${pageContext.request.contextPath}/create-customer" class="nav-link">Register</a>
                            </li>
                            </c:when>
                            <c:otherwise>
                                <!-- Display logout button or user profile link if user is logged in -->
                            <li class="nav-item">
                                <a href="${pageContext.request.contextPath}/dashboard" class="nav-link">Dashboard</a>
                            </li>
                            <li class="nav-item">
                                <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
        <p class="footer__text">Ahmad Sadin, Elnaz Dolati, Francesco Chemello, Gabriella Ingridy De Souza Farias, Luca Pellegrini, Seyedreza Safavi</p>
    </div>
</footer>