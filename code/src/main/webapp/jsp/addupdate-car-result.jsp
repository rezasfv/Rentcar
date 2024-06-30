<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Added/Updated Car Result</title>
</head>

<body>
<h1>Added/Updated Car Result</h1>

<c:if test="${not empty createdCar}">
    <h2>Car Created or Updated Successfully!</h2>
    <ul>
        <li>License Plate: ${createdCar.licensePlate}</li>
        <li>Model Name: ${createdCar.modelName}</li>
        <li>Brand Name: ${createdCar.brandName}</li>
        <li>Category: ${createdCar.category}</li>
        <li>Capacity: ${createdCar.capacity}</li>
        <li>Rental Rate: ${createdCar.rentalRate}</li>
        <li>Current Status: ${createdCar.currentStatus}</li>
    </ul>
</c:if>

<c:if test="${empty createdCar}">
    <h2>Error Occurred!</h2>
    <p>Unable to create the car.</p>
</c:if>

</body>
</html>
