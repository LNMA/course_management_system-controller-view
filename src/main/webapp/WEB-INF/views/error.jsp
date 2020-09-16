<%@ page import="org.springframework.http.HttpStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    Throwable throwable = null;
    if (request.getAttribute("javax.servlet.error.exception") != null){
        throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
    }

    Integer statusCode = 0;
    String statusErrorCode = "Unknown";
    if (request.getAttribute("javax.servlet.error.status_code") != null){
        statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        statusErrorCode = HttpStatus.valueOf(statusCode).name();
    }

    String servletName = "Unknown";
    if (request.getAttribute("javax.servlet.error.servlet_name") != null){
        servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
    }

    String requestUri = "Unknown";
    if (request.getAttribute("javax.servlet.error.request_uri") != null) {
        requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
    }

    String exceptionMessage = "There is no detail!.";
    String throwableClassName = "Not Throwable Exception!.";
    if (throwable != null && throwable.getClass() != null && throwable.getMessage() != null) {
        throwableClassName = throwable.getClass().getName();
        exceptionMessage = throwable.getMessage();
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1,maximum-scale=1.0, user-scalable=0, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <style>
        @import url(<c:url value="/static/lib/bootstrap-4.5.1/css/bootstrap.min.css"/>);
    </style>
    <script type="application/javascript"
            src="<%=request.getContextPath()%>/static/lib/jQuery-3.5.1/jquery-3.5.1.min.js"></script>
    <script type="application/javascript"
            src="<%=request.getContextPath()%>/static/lib/popper-1.16.1/popper.min.js"></script>
    <script type="application/javascript"
            src="<%=request.getContextPath()%>/static/lib/bootstrap-4.5.1/js/bootstrap.min.js"></script>
    <link href="<%=request.getContextPath()%>/static/images/favicon.ico" rel="icon" type="image/x-icon">
    <title>Error! `by Louay Amr`</title>
</head>
<body class="container-fluid">
<div class="row pt-5">
    <div class="col col-md-4 justify-content-center align-self-center">
        <img src="<%=request.getContextPath()%>/static/images/broken_robot.png" alt="error" height="420"
             width="420">
    </div>
    <div class="col col-md-8 justify-content-center align-self-center">
        <p class="font-weight-bold text-left text-warning h2">Course ms</p>
        <p class="font-weight-bold h5">Sorry but Exception is occurred!</p>
        <p class="font-weight-bolder h2 mt-3"><%= statusCode %> - <%= statusErrorCode %>
        </p>
        <p class="mt-4">
            <label class="font-weight-bold">
                <%= throwableClassName %>
                <label class="text-muted">. That's an error.</label>
            </label>
        </p>
        <p class="mt-5">
            <label class="font-weight-bold "> URI: <%= requestUri %>,
                Servlet Name: <%= servletName %>, Message: <%= exceptionMessage %>
                <label class="text-muted">. That's all we know.</label>
            </label>
        </p>
    </div>
</div>
</body>
</html>