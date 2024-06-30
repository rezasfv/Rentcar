<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Dashboard</title>
    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v6.5.2/css/all.css">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/dashboard.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/header.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/footer.css" type="text/css" rel="stylesheet">
</head>

<!-- Include the header -->
<c:import url="header.jsp" />

<body>
<div class="mainbox">
    <div class="tabs">
        <button class="tab-link active" onclick="openTab('personal-info-tab')">Personal Information</button>
        <button class="tab-link" onclick="openTab('license-info-tab')">License Information</button>
    </div>
    <div id="personal-info-tab" class="tab-content">
        <div class="section">
            <h2>Personal Information</h2>
            <ul class="list-group">
                <li class="list-group-item"><b>Email:</b> <c:out value="${user1.email}"/></li>
                <li class="list-group-item"><b>First Name:</b> <c:out value="${user1.firstName}"/></li>
                <li class="list-group-item"><b>Last Name:</b> <c:out value="${user1.lastName}"/></li>
                <li class="list-group-item"><b>Date of Birth:</b> <c:out value="${user1.dateOfBirth}"/></li>
                <li class="list-group-item"><b>Address:</b> <c:out value="${user1.addressCostumer}"/></li>
                <li class="list-group-item"><b>Phone:</b> <c:out value="${user1.phone}"/></li>
                <li class="list-group-item"><b>Nationality:</b> <c:out value="${user1.nationality}"/></li>
                <li class="list-group-item"><b>License Number:</b> <c:out value="${user1.licenseNumber}"/></li>
            </ul>
            <a href="update-customer-form.jsp" class="btn btn-primary mt-3">Edit your Information</a>
        </div>
    </div>
    <div id="license-info-tab" class="tab-content" style="display: none;">
        <div class="section">
            <h2>License Information</h2>
            <ul class="list-group">
                <li class="list-group-item"><b>License Number:</b> <c:out value="${user1.licenseNumber}"/></li>
                <li class="list-group-item"><b>Type Access:</b> <c:out value="${license.typeAccess}"/></li>
                <li class="list-group-item"><b>Expiration Date:</b> <c:out value="${license.expirationDate}"/></li>
                <li class="list-group-item"><b>Issuing Date:</b> <c:out value="${license.issuingDate}"/></li>
            </ul>
            <a href="update-license-form.jsp" class="btn btn-primary mt-3">Edit your License Information</a>
        </div>
    </div>
    <div class="section maincontent">
        <h2>Reserved cars</h2>
        <div id="main-content">
            <div class="row">
                <c:forEach var="car" varStatus="status" items="${reservationsHTML}">
                    <div class="col-md-3 mb-3">
                        <div class="card">
                            <div class="card-body">
                                <p class="card-text"><c:out value="${car}" escapeXml="false"/></p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
<!-- Include the footer -->
<c:import url="footer.jsp" />
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
</html>
