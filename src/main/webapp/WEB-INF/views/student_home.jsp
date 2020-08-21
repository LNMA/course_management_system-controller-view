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
    <script type="application/javascript" src="<c:url value="/static/lib/bootstrap-4.5.1/js/bootstrap.min.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/lib/angularJS-1.8.0/angular.min.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/lib/angularJS-1.8.0/angular-messages.min.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/js/support/dropdown_on_hover.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/js/support/modal_forms.js"/>"></script>
    <link href="<c:url value="/static/images/favicon.ico"/>" rel="icon" type="image/x-icon">
    <title>Student Home `by Louay Amr`</title>
<body>
<header>
    <nav class="navbar navbar-dark navbar-expand-md navbar-expand-lg navbar-expand-xl shadow mb-0"
         style="background-color: #3e3c4e ;">
        <div class="container-fluid">
            <div class="row row-cols-1">
                <div class="col col-md-1 align-self-center">
                    <a class="navbar navbar-brand font-weight-bolder h4" href="">Course ms</a>
                </div>
                <button class="navbar-toggler nav-item" type="button" data-toggle="collapse"
                        data-target="#navbarUserAccount" aria-controls="navbarUserAccount" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse col col-md-10" id="navbarUserAccount">
                    <ul class="nav-item navbar-nav container-fluid justify-content-end">
                        <li class="col col-md-1">
                            <a class="nav-link" href="">
                                <span class="font-weight-bold " style="font-size: small;">Home</span>
                            </a>
                        </li>
                        <li class="col col-md-1">
                            <a class="nav-link" href="">
                                <span class="font-weight-bold" style="font-size: small;">Courses</span>
                            </a>
                        </li>
                        <li class="col col-md-7">
                            <div class="">
                                <form action="" method="get">
                                    <div class="input-group">
                                        <input class="form-control border border-warning badge-pill" type="search"
                                               placeholder="Search" name="keySearch">
                                        <div class="input-group-append">
                                            <button class="btn btn-warning badge-pill" type="submit">&telrec;</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </li>
                        <li class="col col-md-1">
                            <div class="row">
                                <a class="nav-link" href="">
                                    <span class="font-weight-bolder" style="font-size: small;">My Course</span>
                                </a>
                            </div>
                        </li>
                        <li class="col col-md-1">
                            <div class="dropdown ">
                                <a class="dropdown-toggle nav-link" href="#" id="inboxDropdown" role="button"
                                   data-toggle="dropdown"
                                   aria-haspopup="true" aria-expanded="false">
                                    <img src="<c:url value="/static/images/notifications_none-white-48dp.svg"/>" class="rounded-circle"
                                         width="36" height="36">
                                    <span class="badge badge-primary badge-pill"></span>
                                </a>
                                <div class="dropdown-menu" aria-labelledby="inboxDropdown">
                                    <a class="dropdown-item" href="">
                                        <img src="<c:url value="/static/images/chat-black-48dp.svg"/>" width="24" height="24">
                                        Feedback <span class="badge badge-primary badge-pill"></span>
                                    </a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="">
                                        <img src="<c:url value="/static/images/attach_file-black-48dp.svg"/>" width="24" height="24">
                                        Material <span class="badge badge-primary badge-pill"></span>
                                    </a>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="nav-item navbar-nav col col-md-1">
                    <div class="dropdown row justify-content-center">
                        <a class="dropdown-toggle nav-link" href="#" id="accountUserDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <img src="<c:url value="/static/images/account_circle-white-48dp.svg"/>" class="rounded-circle" width="50"
                                 height="50"/>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="accountUserDropdown">
                            <button class="dropdown-item" id="dropdownProfile" data-toggle="modal"
                                    data-target="#profileModal">Profile
                            </button>
                            <a class="dropdown-item" href="">Logout</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>
</header>
<main class="container-fluid">
    <div class="row row-cols-1">
        <aside class="col-md-2 mt-3">
            <div class="form-row">
                <img src="" class="rounded-circle" width="134" height="134"/>
                <p class="mt-5 ml-1 font-weight-bold h5">
                    Louay Amr
                </p>
            </div>
            <hr>
            <div class="form-row">
                <a class="btn btn-toolbar btn-link" href="">
                    <img src="../client/img/send-black-48dp.svg" width="24" height="24">
                    <p class="ml-3">Messages Sent</p>
                </a>
            </div>
            <div class="form-row">
                <a class="btn btn-toolbar btn-link" href="../client/request-user-sent.jsp">
                    <img src="../client/img/how_to_reg-black-48dp.svg" width="24" height="24">
                    <p class="ml-3">Request Sent</p>
                </a>
            </div>
            <div class="form-row">
                <a class="btn btn-toolbar btn-link" href="../group/group-control.jsp">
                    <img src="../client/img/group-black-48dp.svg" width="24" height="24">
                    <p class="ml-3">My Group</p>
                </a>
            </div>
            <hr>
            <div class="form-text text-muted font-weight-bold ml-2">
                <p>Explore</p>
            </div>
            <div class="form-row">
                <a class="btn btn-toolbar btn-link" href="../client/gallery.jsp">
                    <img src="../client/img/photo_library-black-48dp.svg" width="24" height="24">
                    <p class="ml-3">My Photo Album</p>
                </a>
            </div>
            <div class="form-row">
                <a class="btn btn-toolbar btn-link" href="../client/request-user-receive.jsp">
                    <img src="../client/img/person_add-black-48dp.svg" width="24" height="24">
                    <p class="ml-3">Request Receive</p>
                </a>
            </div>
            <div class="form-row">
                <a class="btn btn-toolbar btn-link" href="../client/message-receive.jsp">
                    <img class="mt-2" src="../client/img/email-black-48dp.svg" width="24" height="24">
                    <p class="ml-3 mt-2">Message Receive</p>
                </a>
            </div>
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
                                            <input class="form-control" id="username" name="username"
                                                   type="text" value="${account.getUsername()}" readonly disabled>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="form-group col col-md-10">
                                            <label for="accountPermission" class="font-weight-bold">
                                                Account Permission:
                                            </label>
                                            <input class="form-control" id="accountPermission"
                                                   name="accountPermission" type="text"
                                                   value="${account.getAccountPermission()}" readonly disabled>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="form-group col col-md-10">
                                            <label for="dateCreate" class="font-weight-bold">
                                                Date Create:
                                            </label>
                                            <input class="form-control" id="dateCreate" name="dateCreate"
                                                   type="text" value="${account.getDateCreate()}" readonly disabled>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="form-group col col-md-10">
                                            <label for="password" class="font-weight-bold">
                                                Password:
                                                <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                        data-target="#passwordModal">
                                                    <img src="<c:url value="/static/images/edit-black-48dp.svg"/>" width="16" height="16">
                                                </button>
                                            </label>
                                            <input class="form-control" id="password" name="password"
                                                   type="password" value="${account.getPassword()}" readonly disabled>
                                        </div>
                                    </div>
                                </div>

                                <div class="align-self-center col col-md-5">
                                    <div class="row justify-content-center ">
                                        <img src="data:image;base64,${account.getBase64()}" class="rounded-circle"
                                             width="256" height="256">
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#ImageModal">
                                            <img src="<c:url value="/static/images/add_photo_alternate-black-48dp.svg"/>" width="28"
                                                 height="28">
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="container-fluid">
                            <div class="form-row">
                                <div class="form-group col col-md-6">
                                    <label for="fname" class="font-weight-bold">
                                        Forename:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#firstNameModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>" width="16" height="16">
                                        </button>
                                    </label>
                                    <input class="form-control" id="fname" value="${account.getFirstName()}"
                                           type="text" readonly disabled>
                                </div>
                                <div class="form-group col col-md-6">
                                    <label for="telephone" class="font-weight-bold">
                                        Phone:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#telephoneModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>" width="16" height="16">
                                        </button>
                                    </label>
                                    <input class="form-control" id="telephone" value="${account.getTelephone()}"
                                           type="text" readonly disabled>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col col-md-6">
                                    <label for="lname" class="font-weight-bold">
                                        Surname:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#lastNameModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>" width="16" height="16">
                                        </button>
                                    </label>
                                    <input class="form-control" id="lname" value="${account.getLastName()}"
                                           type="text" readonly disabled>
                                </div>
                                <div class="form-group col col-md-6">
                                    <label for="country" class="font-weight-bold">
                                        Country:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#addressModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>" width="16" height="16">
                                        </button>
                                    </label>
                                    <input class="form-control" id="country" value="${account.getCountry()}"
                                           type="text" readonly disabled>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col col-md-6">
                                    <label for="gender" class="font-weight-bold">
                                        Gender:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#genderModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>" width="16" height="16">
                                        </button>
                                    </label>
                                    <input class="form-control" id="gender" value="${account.getGender()}"
                                           type="text" readonly disabled>
                                </div>
                                <div class="form-group col col-md-6">
                                    <label for="state" class="font-weight-bold">
                                        State:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#addressModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>" width="16" height="16">
                                        </button>
                                    </label>
                                    <input class="form-control" id="state" value="${account.getState()}"
                                           type="text" readonly disabled>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col col-md-6">
                                    <label for="birthday" class="font-weight-bold">
                                        BirthDay:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#birthdayModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>" width="16" height="16">
                                        </button>
                                    </label>
                                    <input class="form-control" id="birthday" value="${account.getBirthday()}"
                                           type="text" readonly disabled>
                                </div>
                                <div class="form-group col col-md-6">
                                    <label for="address" class="font-weight-bold">
                                        Address:
                                        <button class="btn" data-toggle="modal" data-dismiss="modal"
                                                data-target="#addressModal">
                                            <img src="<c:url value="/static/images/edit-black-48dp.svg"/>" width="16" height="16">
                                        </button>
                                    </label>
                                    <input class="form-control" id="address" value="${account.getAddress()}"
                                           type="text" readonly disabled>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col col-md-6">
                                    <label for="age" class="font-weight-bold">
                                        Age:
                                    </label>
                                    <input class="form-control" id="age" value="${account.getAge()}"
                                           type="text" readonly disabled>
                                </div>
                                <div class="form-group col col-md-6">
                                    <label for="lLogin" class="font-weight-bold">
                                        Last Login:
                                    </label>
                                    <input class="form-control" id="lLogin" value="${account.lLogin()}"
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
        <!-- password modal -->
        <div id="changePasswordModal"></div>
        <!-- Image modal -->
        <div id="changeUserPictureModal"></div>
        <!-- fname modal -->
        <div id="changeFNameModal"></div>
        <!-- lname modal -->
        <div id="changeLNameModal"></div>
        <!-- gender modal -->
        <div id="changeGenderModal"></div>
        <!-- birthday modal -->
        <div id="changeBirthdayModal"></div>
        <!-- telephone modal -->
        <div id="changeTelephoneModal"></div>
        <!-- email modal -->
        <div id="changeEmailModal"></div>
        <!-- address modal -->
        <div id="changeAddressModal"></div>
    </div>
</nav>
</body>
</html>

