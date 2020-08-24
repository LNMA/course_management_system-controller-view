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
        @import url(<c:url value="/static/lib/bootstrap-4.5.1/css/bootstrap.min.css"/>);
    </style>
    <script type="application/javascript" src="<c:url value="/static/lib/jQuery-3.5.1/jquery-3.5.1.min.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/lib/popper-2.4.3/popper.min.js"/>"></script>
    <script type="application/javascript"
            src="<c:url value="/static/lib/bootstrap-4.5.1/js/bootstrap.min.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/lib/angularJS-1.8.0/angular.min.js"/>"></script>
    <script type="application/javascript"
            src="<c:url value="/static/lib/angularJS-1.8.0/angular-messages.min.js"/>"></script>
    <script type="application/javascript"
            src="<c:url value="/static/lib/angularJS-1.8.0/angular-sanitize.min.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/js/app.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/js/control/student_home.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/js/service/get_student_home_info.js"/>"></script>
    <link href="<c:url value="/static/images/favicon.ico"/>" rel="icon" type="image/x-icon">
    <title>Student Home `by Louay Amr`</title>
<body ng-app="app" ng-controller="StudentHomeController">
<header>
    <div ng-include="'https://localhost:8443/static/html/student-navbar.html'"></div>
</header>
<main class="container-fluid">
    <div class="row row-cols-1">
        <aside class="col col-md-2 mt-3 justify-content-center pb-4">
            <div class="form-row">
                <img src="data:image/*;base64,{{picture}}"
                     class="rounded-circle" width="134" height="134" alt="My Picture"/>
                <p class="mt-5 ml-1 font-weight-bold h5">
                    {{forename}} {{surname}}
                </p>
            </div>
            <p class="text-muted font-weight-bold">{{headline}}</p>
            <p class="font-weight-bold">Interest: {{interests}}</p>
            <button class="btn btn-outline-secondary btn-sm w-100 dropdownProfile" data-toggle="modal"
                    data-target="#profileModal">
                Edit Profile
            </button>
        </aside>
    </div>
</main>
<footer>
    <nav class="navbar navbar-dark position-relative mt-0" style="background-color: #d3c7cd; height: 16em; width: 100%">
        <p>Louay Amr © 2020</p>
    </nav>
</footer>
<nav>
    <div id="viewProfileModal">
        <div class="modal fade" id="profileModal">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Profile</h4>
                        <button type="button" class="close" data-dismiss="modal">
                            &times;
                        </button>
                    </div>

                    <div class="modal-body">

                        <div class="container-fluid">
                            <div class="row row-cols-1">
                                <div class="col col-md-7">
                                    <div class="form-row">
                                        <div class="form-group col col-md-10">
                                            <label for="username" class="font-weight-bold">
                                                Username:
                                            </label>
                                            <input class="form-control" id="username" name="Studentemail"
                                                   type="text" value="{{email}}" readonly disabled>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="form-group col col-md-10">
                                            <label for="accountPermission" class="font-weight-bold">
                                                Account Permission:
                                            </label>
                                            <input class="form-control" id="accountPermission"
                                                   name="accountRole" type="text"
                                                   value="{{userRole}}" readonly disabled>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="form-group col col-md-10">
                                            <label for="dateCreate" class="font-weight-bold">
                                                Date Create:
                                            </label>
                                            <input class="form-control" id="dateCreate" name="dateCreate"
                                                   type="text" value="{{joinDate}}" readonly disabled>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="form-group col col-md-10">
                                            <label for="password" class="font-weight-bold">
                                                Password:
                                                <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                        data-target="#passwordModal">
                                                    <img src="<c:url value="/static/images/edit-black-48dp.svg"/>"
                                                         width="16" height="16" alt="edit">
                                                </button>
                                            </label>
                                            <input class="form-control" id="password" name="password"
                                                   type="password" value="{{password}}" readonly disabled>
                                        </div>
                                    </div>
                                </div>

                                <div class="align-self-center col col-md-5">
                                    <div class="row justify-content-center ">
                                        <img src="data:image/*;base64,{{picture}}" class="rounded-circle"
                                             width="256" height="256" alt="My Picture">
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#ImageModal">
                                            <img src="<c:url value="/static/images/add_photo_alternate-black-48dp.svg"/>"
                                                 width="28" height="28" alt="add image">
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="container-fluid">
                            <div class="form-row">
                                <div class="form-group col col-md-6">
                                    <label for="forename" class="font-weight-bold">
                                        Forename:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#forenameModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>"
                                                 width="16" height="16" alt="edit">
                                        </button>
                                    </label>
                                    <input class="form-control" id="forename" value="{{forename}}"
                                           type="text" readonly disabled>
                                </div>
                                <div class="form-group col col-md-6">
                                    <label for="telephone" class="font-weight-bold">
                                        Phone:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#telephoneModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>"
                                                 width="16" height="16" alt="edit">
                                        </button>
                                    </label>
                                    <input class="form-control" id="telephone" value="{{phone}}"
                                           type="text" readonly disabled>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col col-md-6">
                                    <label for="lname" class="font-weight-bold">
                                        Surname:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#surnameModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>"
                                                 width="16" height="16" alt="edit">
                                        </button>
                                    </label>
                                    <input class="form-control" id="lname" value="{{surname}}"
                                           type="text" readonly disabled>
                                </div>
                                <div class="form-group col col-md-6">
                                    <label for="country" class="font-weight-bold">
                                        Country:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#addressModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>"
                                                 width="16" height="16" alt="edit">
                                        </button>
                                    </label>
                                    <input class="form-control" id="country" value="{{country}}"
                                           type="text" readonly disabled>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col col-md-6">
                                    <label for="gender" class="font-weight-bold">
                                        Gender:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#genderModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>"
                                                 width="16" height="16" alt="edit">
                                        </button>
                                    </label>
                                    <input class="form-control" id="gender" value="{{gender}}"
                                           type="text" readonly disabled>
                                </div>
                                <div class="form-group col col-md-6">
                                    <label for="state" class="font-weight-bold">
                                        State:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#addressModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>"
                                                 width="16" height="16" alt="edit">
                                        </button>
                                    </label>
                                    <input class="form-control" id="state" value="{{state}}"
                                           type="text" readonly disabled>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col col-md-6">
                                    <label for="birthday" class="font-weight-bold">
                                        BirthDay:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#birthdayModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>"
                                                 width="16" height="16" alt="edit">
                                        </button>
                                    </label>
                                    <input class="form-control" id="birthday" value="{{birthday}}"
                                           type="text" readonly disabled>
                                </div>
                                <div class="form-group col col-md-6">
                                    <label for="address" class="font-weight-bold">
                                        Address:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#addressModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>"
                                                 width="16" height="16" alt="edit">
                                        </button>
                                    </label>
                                    <input class="form-control" id="address" value="{{address}}"
                                           type="text" readonly disabled>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col col-md-6">
                                    <label for="age" class="font-weight-bold">
                                        Age:
                                    </label>
                                    <input class="form-control" id="age" value="{{age}}"
                                           type="text" readonly disabled>
                                </div>
                                <div class="form-group col col-md-6">
                                    <label for="lLogin" class="font-weight-bold">
                                        Last Login:
                                    </label>
                                    <input class="form-control" id="lLogin" value=""
                                           type="text" readonly disabled>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col col-md-6">
                                    <label for="headline" class="font-weight-bold">
                                        Headline:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#addressModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>"
                                                 width="16" height="16" alt="edit">
                                        </button>
                                    </label>
                                    <input class="form-control" id="headline" value="{{headline}}"
                                           type="text" readonly disabled>
                                </div>
                                <div class="form-group col col-md-6">
                                    <label for="interest" class="font-weight-bold">
                                        Interests:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#addressModal" ng-click="ss">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>"
                                                 width="16" height="16" alt="edit">
                                        </button>
                                    </label>
                                    <input class="form-control" id="interest" value="{{interests}}"
                                           type="text" readonly disabled>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger"
                                data-dismiss="modal">Close
                        </button>
                    </div>

                </div>
            </div>
        </div>
        <div ng-include="'https://localhost:8443/static/html/edit_password-modal.html'"></div>
        <div ng-include="'https://localhost:8443/static/html/edit_student_details-modal.html'"></div>
    </div>
</nav>
</body>
</html>

