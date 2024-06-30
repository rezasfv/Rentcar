<%--
  Created by IntelliJ IDEA.
  User: rezas
  Date: 4/15/2024
  Time: 5:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create License Form</title>
    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v6.5.2/css/all.css">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/form.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/header.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/footer.css" type="text/css" rel="stylesheet">
</head>

<!-- Include the header -->
<c:import url="header.jsp" />

<body>
    <h1>Create License Form</h1>
    <form class ="needs-validation" novalidate method="POST" action="<c:url value="/create-license"/>">

        <label for="licenseNumber">licenseNumber:</label>
        <input type="text" id="licenseNumber" name="licenseNumber" required><br><br>

        <label for="typeAccess">typeAccess:</label>
        <input type="text" id="typeAccess" name="typeAccess" required><br><br>

        <label for="typeAccess">expirationDate:</label>
        <input type="text" id="expirationDate" name="expirationDate" required><br><br>

        <label for="typeAccess">issuingDate:</label>
        <input type="text" id="issuingDate" name="issuingDate" required><br><br>

        <button type="submit">Add License</button><br/>
        <button type="reset">Reset the form</button>
    </form>
</body>
<!-- Include the footer -->
<c:import url="footer.jsp" />
<!-- Form validation -->
<script src="${pageContext.request.contextPath}/js/form-licese-validation.js"></script>
 <!-- Bootstrap JS -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>
