<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Cars</title>
    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v6.5.2/css/all.css">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/home.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/header.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/footer.css" type="text/css" rel="stylesheet">
</head>

<!-- Include the header -->
<c:import url="header.jsp" />

<body>
<div class="mainbox">
    <div class="section maincontent">
        <h2>All Cars</h2>
        <p>${message.message}</p>
        <ul class="list-group">
            <c:forEach var="car" items="${cars}">
                <li class="list-group-item">
                    <div class="card">
                        <div class="card-body">
                            <p class="card-text">
                                License Plate: ${car.licensePlate}<br>
                                Model Name: ${car.modelName}<br>
                                Brand Name: ${car.brandName}<br>
                                Capacity: ${car.capacity}<br>
                                Category: ${car.category}<br>
                                Current Status: ${car.currentStatus}<br>
                                Rental Rate: ${car.rentalRate}
                            </p>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
<!-- Include the footer -->
<c:import url="footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
</html>
