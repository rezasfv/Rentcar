<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><h1>Admin</h1></title>
    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v6.5.2/css/all.css">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/updatecar.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/header.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/footer.css" type="text/css" rel="stylesheet">
</head>
<!-- Include the header -->
<c:import url="header.jsp" />
<body>
<h1>Admin</h1>
<div class="row">
        <div id="add-car" class="col">
            <!-- Form for creating a car -->
            <form action="CreateCarServlet>" method="post">
                <label>Licence Plate: <input type="text" name="licence_plate" required></label><br>
                <label>Rental Rate: <input type="text" name="rentalrate" required></label><br>
                <label>Capacity: <input type="text" name="capacity" required></label><br>
                <label>Model Name: <input type="text" name="modelname" required></label><br>
                <label>Category: <input type="text" name="category"></label><br>
                <label>Current Status: <input type="text" name="currentstatus"></label><br>
                <label>Brand Name: <input type="text" name="brandname" required></label><br>


            </form>
        </div>
        <div id="remove-car" class="col">
            <form action="DeleteCarServlet" method="post">
                <label>Licence Plate: <input type="text" name="licence_plate" required></label><br>
                <label>Rental Rate: <input type="text" name="rentalrate" required></label><br>
                <label>Capacity: <input type="text" name="capacity" required></label><br>
                <label>Model Name: <input type="text" name="modelname" required></label><br>
                <label>Category: <input type="text" name="category"></label><br>
                <label>Current Status: <input type="text" name="currentstatus"></label><br>
                <label>Brand Name: <input type="text" name="brandname" required></label><br>
            </form>
        </div>
    <div class="row">
        <div class="col">
            <input type="hidden" name="action" value="add">
            <button type="submit">Add Car</button>
        </div>
        <div class="col">
            <input type="hidden" name="action" value="remove">
            <button type="submit">Remove Car</button>
        </div>
    </div>
    
    
</div>

<!-- Display all cars -->
<h2>All Cars:</h2>
<c:forEach var="car" items="${cars}">
    <p>Licence Plate: ${car.licencePlate}, Model Name: ${car.modelName}, Brand Name: ${car.brandName}, Category: ${car.category}, Capacity: ${car.capacity}, Rental Rate: ${car.rentalRate}, Current Status: ${car.currentStatus}</p>
</c:forEach>

<!-- Include the footer -->
<c:import url="footer.jsp" />
<script src="${pageContext.request.contextPath}/js/updatecar.js"></script>
</body>

</html>
