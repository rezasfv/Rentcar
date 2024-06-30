<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit License Form</title>
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
            <h2>Edit License Form</h2>
            <div class="maincontent">
                <div id="main-content">
                    <form class ="needs-validation" novalidate method="POST" action="<c:url value="/edit-license"/>">
                        <div class="form-group">
                            <label for="licenseNumber">License Number:</label>
                            <input type="text" class="form-control" id="licenseNumber" name="licenseNumber" value="${user1.licenseNumber}" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                            <div class="invalid-feedback">
                                License number incorrect, it's 10 characters long, only letters and numbers!
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="typeAccess">Type Access:</label>
                            <select class="form-control" id="typeAccess" name="typeAccess" required>
                                <option value="B" ${license.typeAccess == 'B' ? 'selected' : ''}>B</option>
                                <option value="B1" ${license.typeAccess == 'B1' ? 'selected' : ''}>B1</option>
                            </select>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                            <div class="invalid-feedback">
                                Wrong type!
                            </div>
                        </div>


                        <div class="form-group">
                            <label for="expirationDate">Expiration Date:</label>
                            <input type="date" class="form-control" id="expirationDate" name="expirationDate" value="${license.expirationDate}" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                            <div class="invalid-feedback">
                                Wrong data!
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="issuingDate">Issuing Date:</label>
                            <input type="date" class="form-control" id="issuingDate" name="issuingDate" value="${license.issuingDate}" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                            <div class="invalid-feedback">
                                Wrong data!
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary">Edit License</button>
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
<script src="${pageContext.request.contextPath}/js/form-license-validation.js"></script>
<!-- Bootstrap JS -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>