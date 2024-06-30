<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Page</title>
    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v6.5.2/css/all.css">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/search.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/header.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/footer.css" type="text/css" rel="stylesheet">
</head>
<!-- Include the header -->
<c:import url="header.jsp" />
<body>

<%
    response.setHeader("cache-control","no-store");
    if(session.getAttribute("carsHTML")==null){
        response.sendRedirect("home.jsp");
    }
%>
    <div class="container">
        <div class="row">
            <div class="col-md-12 my-3" id="research-filters">
                <div class='row'>
                    <div class="col-sm-3">
                        <label for="brandname">Brand Name:</label>
                        <select class="form-control" name="brandname" id="brandname">
                            <option value="all">All</option>
                        </select>
                    </div>
                    <div class="col-sm-3">
                        <label for="currentstatus">Current Status:</label>
                        <select class="form-control" name="currentstatus" id="currentstatus">
                            <option value="available">Available</option>
                            <option value="rented">Rented</option>
                            <option value="all">All</option>
                        </select>
                    </div>
                    <div class="col-sm-3">
                        <label for="category">Category:</label>
                        <select class="form-control" name="category" id="category">
                            <option value="all">All</option>
                        </select>
                    </div>
                    <div class="col-sm-3">
                        <label for="modelname">Model Name:</label>
                        <input type="text" class="form-control" id="modelname" name="modelname">
                    </div>
                    <div id="slider-start"></div>
                    <div class="col-6">
                        <label for="minrentalrate">Min Rental Rate:</label>
                        <input type="range" class="custom-range" value="1" min="1" max="200" step="5" id="minrentalrate">
                    </div>
                    <div class="col-6">
                        <label for="maxrentalrate">Max Rental Rate:</label>
                        <input type="range" class="custom-range" value="200" min="1" max="200" step="5" id="maxrentalrate">
                    </div>
                    <div class="col-6">
                        <label for="mincapacity">Min Capacity:</label>
                        <input type="range" class="custom-range" value="1" min="1" max="9" step="1" id="mincapacity">
                    </div>
                    <div class="col-6">
                        <label for="maxcapacity">Max Capacity:</label>
                        <input type="range" class="custom-range" value="9" min="1" max="9" step="1" id="maxcapacity">
                    </div>
                </div>
                <div class="col align-self-end">
                    <button class="btn btn-primary" id="filter">Filter</button>
                </div>
            </div>
            <hr>
            <div class="col-md-12  my-3 row" id="results">
                Car section, if you see this message an error occurred!
            </div>
        </div>
    </div>
</body>
<!-- Include the footer -->
<c:import url="footer.jsp" />
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/search.js"></script>
</html>
