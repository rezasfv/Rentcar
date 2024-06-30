<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Rent Car</title>
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
    <div class="company-name">
        <h1>Welcome to Rent Car website!</h1>
    </div>
    <hr>
    <div class="maincontent">
        <div class="main-top-elem">
            <h2 class="top_rented_text">Top Rented cars</h2>
            <div class="col-md-12  my-3 row" id="top_rented">
                Just a second...
            </div>
        </div>
        <hr>
        <div class="text-center m-3">
            <a href="search-car" class="btn btn-primary btn-lg btn-block p-2 mx-auto" id="search-btn">
                <h2>Search your desired car!</h2>
            </a>
        </div>
    </div>
</body>
<!-- Include the footer -->
<c:import url="footer.jsp" />
<!-- Script top rented cars -->
<script src="${pageContext.request.contextPath}/js/toprentedcar.js"></script>
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</html>
