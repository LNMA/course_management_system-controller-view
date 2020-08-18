<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <style type="text/css">
        @import url(<c:url value="/static/css/small_form.css"/>);
        @import url(<c:url value="/static/css/background-large_form.css"/>);
        @import url(<c:url value="/static/lib/bootstrap-4.5.1/css/bootstrap.min.css"/>);
    </style>
    <script type="application/javascript" src="<c:url value="/static/lib/jQuery-3.5.1/jquery-3.5.1.min.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/lib/popper-2.4.3/popper.min.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/lib/bootstrap-4.5.1/js/bootstrap.min.js"/>"></script>
    <link href="<c:url value="/static/images/favicon.ico"/>" rel="icon" type="image/x-icon">
    <title>Verify `by Louay Amr`</title>
<body>
<header>
    <div class="jumbotron mb-0 h2 text-center">
        Course Management System
    </div>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: #3e3c4e;">
        <p class="text-light text-capitalize h3">Account Verify</p>
    </nav>
</header>
<main>
    <div>
        <div class="mainBody container-fluid" style="padding-bottom: 7em; padding-top: 7em;">
            <div class="row justify-content-center align-content-center">
                <div class="col col-md-6">
                    <div class="formSignIn p-4">
                        <h2 class="mb-0 h3 text-left formHead font-weight-bold text-primary">
                            Congratulations...</h2>
                        <hr class="mx-1">
                        <p>Account now is activated successfully, Please now and
                            <a href="https://localhost:8443/login">Login</a>.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<footer>
    <nav class="navbar navbar-dark position-relative mt-0" style="background-color: #d3c7cd; height: 16em; width: 100%">
        <p>Louay Amr © 2020</p>
    </nav>
</footer>
</body>
</html>
