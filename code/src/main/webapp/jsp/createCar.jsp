<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Car</title>
</head>
<body>
<h1>Create Car</h1>

<!-- Form for creating a car -->
<form action="<c:url value="/create-car"/>" method="post">
    <label>Licence Plate: <input type="text" name="licence_plate" required></label><br>
    <label>Rental Rate: <input type="text" name="rentalrate" required></label><br>
    <label>Capacity: <input type="text" name="capacity" required></label><br>
    <label>Model Name: <input type="text" name="modelname" required></label><br>
    <label>Category: <input type="text" name="category"></label><br>
    <label>Current Status: <input type="text" name="currentstatus"></label><br>
    <label>Brand Name: <input type="text" name="brandname" required></label><br>

    <input type="submit" value="Create">
</form>

<hr>

<h2>Message:</h2>
<p>${message}</p>

<!-- Display all cars -->
<h2>All Cars:</h2>
<c:forEach var="car" items="${cars}">
    <p>Licence Plate: ${car.licencePlate}, Model Name: ${car.modelName}, Brand Name: ${car.brandName}, Category: ${car.category}, Capacity: ${car.capacity}, Rental Rate: ${car.rentalRate}, Current Status: ${car.currentStatus}</p>
</c:forEach>

</body>
</html>
