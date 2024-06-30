<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Customer Form</title>
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
<div class="container mt-4">

    <div class="mainbox">
        <h2>Edit Customer Form</h2>
        <div class="maincontent">
            <div id="main-content">


    <form class ="needs-validation" novalidate method="POST" action="<c:url value="/edit-customer"/>">
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" id="email" name="email" value="${user1.email}" readonly readonly>
        </div>
        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" class="form-control" id="firstName" name="firstName" value="${user1.firstName}" required>
            <div class="valid-feedback">
                Looks good!
            </div>
            <div class="invalid-feedback">
                Missing the name!
            </div>
        </div>
        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" class="form-control" id="lastName" name="lastName" value="${user1.lastName}" required>
            <div class="valid-feedback">
                Looks good!
            </div>
            <div class="invalid-feedback">
                Missing the surname!
            </div>
        </div>
        <div class="form-group">
            <label for="dateOfBirth">Date of Birth:</label>
            <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth" value="${user1.dateOfBirth}" readonly required>
        </div>
        <div class="form-group">
            <label for="addressCustomer">Address:</label>
            <input type="text" class="form-control" id="addressCustomer" name="addressCustomer" value="${user1.addressCostumer}" required>
            <div class="valid-feedback">
                Looks good!
            </div>
            <div class="invalid-feedback">
                Missing the address!
            </div>
        </div>
        <div class="form-group">
            <label for="phone">Phone:</label>
            <input type="text" class="form-control" id="phone" name="phone" value="${user1.phone}" required>
            <div class="valid-feedback">
                Looks good!
            </div>
            <div class="invalid-feedback">
                Missing the phone number!
            </div>
        </div>
        <div class="form-group">
            <label for="nationality">Nationality:</label>
            <input type="text" class="form-control" id="nationality" name="nationality" value="${user1.nationality}" required>
            <div class="valid-feedback">
                Looks good!
            </div>
            <div class="invalid-feedback">
                Missing the nationality!
            </div>
        </div>

        <div class="form-group">
            <label for="nationality">License Number::</label>
            <input type="text" class="form-control" id="licenseNumber" name="licenseNumber" value="${user1.licenseNumber}" readonly >
        </div>
        <button type="submit" class="btn btn-primary">Edit</button>
        <button type="reset" class="btn btn-secondary">Reset the form</button>
    </form>
</div>
</div>
</div>
</div>
</body>

<!-- Include the footer -->
<c:import url="footer.jsp" />
<!-- Form validation -->
<script src="${pageContext.request.contextPath}/js/form-validation.js"></script>
<!-- Bootstrap JS -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

</html>