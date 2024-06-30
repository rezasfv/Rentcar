<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Check Availability</title>
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
        <% 
        response.setHeader("cache-control","no-store"); 
        if(session.getAttribute("user1")==null){ 
            response.sendRedirect(request.getContextPath()+"/login");
        }
        %>

        <div class="container d-flex flex-column my-5">
            <h1 class="mb-4">Edit Customer Form</h1>
            <form class ="needs-validation" novalidate method="POST" action="<c:url value="/check-availability"/>">
            <div class="mb-3">
                <label for="licensePlate" class="form-label">Licence Plate:</label>
            <input type="text" id="licensePlate" name="licensePlate" class="form-control" value="<c:out value="${car1.licensePlate == null ? licencePlate : car1.licensePlate}"/>" readonly="true" required="true">
        </div>

        <% if (request.getAttribute("message") != null) { %>
            <p class="text-danger"><%= request.getAttribute("message") %></p>
            <% } %>

            <div class="mb-3">
                <label for="formDate" class="form-label">From:</label>
                <input type="date" id="formDate" name="formDate" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="toDate" class="form-label">To:</label>
                <input type="date" id="toDate" name="toDate" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary me-2">Check Availability</button>
            <button type="reset" class="btn btn-secondary">Reset the Form</button>
        </form>
    </div>
</body>

<!-- Include the footer -->
<c:import url="footer.jsp" />

<!-- Form validation -->
<script src="${pageContext.request.contextPath}/js/form-check-availability-validation.js"></script>
<!-- Other -->
<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script> -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script> -->
<!-- Bootstrap CSS-->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>