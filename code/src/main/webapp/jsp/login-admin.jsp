<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Admin</title>
        <!-- Favicon -->
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="${pageContext.request.contextPath}/css/loginadmin.css" type="text/css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/header.css" type="text/css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/footer.css" type="text/css" rel="stylesheet">
    </head>
<!-- Include the header -->
<c:import url="header.jsp" />
<body>
<div>
    <div class="mainbox">
        <h2>Login Admin</h2>
        <div class="maincontent">
            <div id="main-content">
                <form method="post" >
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password:</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <div class="row">
                        <div class="col">
                            <input type="submit" class="btn btn-primary" value="Login">
                        </div>
                        <div class="col">
                            <input type="submit" class="btn btn-primary" value="Update car" href="${pageContext.request.contextPath}/add-remove-car">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>

<!-- Include the footer -->
<c:import url="footer.jsp" />

<!-- Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>