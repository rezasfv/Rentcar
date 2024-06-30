<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
     <title>My Website</title>
     <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
     <!-- Include CSS files, meta tags, etc. -->
</head>
<body>
     <header>
            <!-- Navigation bar content -->
            <nav class="nav p-2 bg-dark">
                <a class="nav-link" aria-current="page" href=${pageContext.request.contextPath}+"/">Home</a>

                <a class="nav-link" href=${pageContext.request.contextPath}+"/login">Login</a>
                <a class="nav-link" href=${pageContext.request.contextPath}+"/register" >Register</a>
            </nav>
     </header>

     <main>
            <div class="container py-5">
                <h3 class='text-success'>success</h3>
            </div>
     </main>

     <footer class="bg-dark d-flex justify-content-center py-5 text-white">
         <!-- Footer content -->
         &copy; 2024 My Website
     </footer>
     <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
 </body>
 </html>