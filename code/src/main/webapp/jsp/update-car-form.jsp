<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Update Car Form</title>
</head>

<body>
<h1>Update Car</h1>

<form method="POST" action="<c:url value="/update-car"/>">
    <label for="license_plate">License Plate:</label>
    <input type="text" id="license_plate" name="license_plate" required><br><br>

    <label for="rentalrate">Rental Rate:</label>
    <input type="number" id="rentalrate" name="rentalrate" step="0.01" required><br><br>

    <label for="capacity">Capacity:</label>
    <input type="number" id="capacity" name="capacity" required><br><br>

    <label for="modelname">Model Name:</label>
    <input type="text" id="modelname" name="modelname" required><br><br>

    <label for="category">Category:</label>
    <input type="text" id="category" name="category" required><br><br>

    <label for="currentstatus">Current Status:</label>
    <input type="text" id="currentstatus" name="currentstatus" required><br><br>

    <label for="brandname">Brand Name:</label>
    <input type="text" id="brandname" name="brandname" required><br><br>

    <button type="submit">Update Car</button><br/>
    <button type="reset">Reset Form</button>
</form>

</body>
</html>
