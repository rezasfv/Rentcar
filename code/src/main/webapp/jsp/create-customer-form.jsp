<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register Customer Form</title>
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
            <h2>Registration</h2>
            <div class="maincontent">
                <div id="main-content">
                    <form class ="needs-validation" novalidate method="POST" action="<c:url value="/create-customer"/>">
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" class="form-control" id="email" name="email" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                            <div class="invalid-feedback">
                                It must be an e-mail!
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="firstName">First Name:</label>
                            <input type="text" class="form-control" id="firstName" name="firstName" required>
                            <div class="invalid-feedback">
                                Missing the name!
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last Name:</label>
                            <input type="text" class="form-control" id="lastName" name="lastName" required>
                            <div class="invalid-feedback">
                                Missing the surname!
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="dateOfBirth">Date of Birth:</label>
                            <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                            <div class="invalid-feedback">
                                You must be at least 18 years old!
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addressCustomer">Address:</label>
                            <input type="text" class="form-control" id="addressCustomer" name="addressCustomer" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                            <div class="invalid-feedback">
                                Missing the address!
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone:</label>
                            <input type="tel" class="form-control" id="phone" name="phone" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                            <div class="invalid-feedback">
                                Missing the phone number!
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="nationality">Nationality:</label>
                            <input type="text" class="form-control" id="nationality" name="nationality" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                            <div class="invalid-feedback">
                                Missing the nationality!
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="passwordCustomer">Password:</label>
                            <input type="password" class="form-control" id="passwordCustomer" name="passwordCustomer" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                            <div class="invalid-feedback">
                                The password should be minimum 8 characters, at least one uppercase letter, one lowercase letter, one number and one special character!
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="licenseNumber">License Number:</label>
                            <input type="text" class="form-control" id="licenseNumber" name="licenseNumber" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                            <div class="invalid-feedback">
                                License number incorrect, it's 10 characters long, only letters and numbers!
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary">Register me</button>
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