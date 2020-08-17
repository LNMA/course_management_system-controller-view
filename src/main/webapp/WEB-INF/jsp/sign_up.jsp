<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <style type="text/css">
        @import url(<c:url value="/static/css/register.css"/>);
        @import url(<c:url value="/static/lib/bootstrap-4.5.1/css/bootstrap.min.css"/>);
    </style>
    <script type="application/javascript" src="<c:url value="/static/lib/jQuery-3.5.1/jquery-3.5.1.min.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/lib/popper-2.4.3/popper.min.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/lib/bootstrap-4.5.1/js/bootstrap.min.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/lib/angularJS-1.8.0/angular.min.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/lib/angularJS-1.8.0/angular-messages.min.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/js/app.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/js/control/student_sign_up.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/js/service/country_state.js"/>"></script>
    <script type="application/javascript" src="<c:url value="/static/js/service/register_student_submit.js"/>"></script>
    <link href="<c:url value="/static/images/favicon.ico"/>" rel="icon" type="image/x-icon">
    <title>Sign Up`by Louay Amr`</title>
</head>
<body>
<header>
    <div class="jumbotron mb-0 h2 text-center">
        Course Management System
    </div>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: #3e3c4e;">
        <p class="text-light text-capitalize h3">Sign Up</p>
    </nav>
</header>
<main>
    <div ng-app="app" ng-controller="StudentRegisterController">
        <div class="mainBody">

            <div class="container" id="message" ng-model="submitSuccessMessage">
                <div ng-show="isSuccess">
                    <div class="alert alert-success alert-dismissible fade show">
                        <strong>Success!</strong> {{submitSuccessMessage}}, Go to<a href="https://localhost:8443/login">login</a> page.
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </div>

            <div class="container" id="passwordErrormessage" ng-model="submitErrorMessage">
                <div ng-show="isError">
                    <div class="alert alert-danger alert-dismissible fade show">
                        <strong>Error!</strong> {{submitErrorMessage}}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </div>

            <div id="errorMessage" ng-model="errorMessage" class="container" ng-hide="studentForm.$valid" role="alert">
                <div ng-messages="studentForm.$error || studentForm.$invalid"
                     ng-show="submitted && (studentForm.$invalid || studentForm.$error)">
                    <div class="alert alert-danger alert-dismissible fade show">
                        <strong>Error!</strong>
                        <span ng-show="studentForm.email.$error.required">
                            Email Required!
                        </span>
                        <span ng-show="studentForm.email.$invalid">
                            Email Invalid!
                        </span>
                        <span ng-show="studentForm.password.$error.required">
                            Password Required!
                        </span>
                        <span ng-show="studentForm.password.$invalid">
                            Password Invalid!
                        </span>
                        <span ng-show="studentForm.rePassword.$error.required">
                            re-Password Required!
                        </span>
                        <span ng-show="studentForm.rePassword.$invalid">
                            re-Password Invalid!
                        </span>
                        <span ng-show="studentForm.forename.$error.required">
                            Forename Required!
                        </span>
                        <span ng-show="studentForm.forename.$invalid">
                            Forename Invalid!
                        </span>
                        <span ng-show="studentForm.surname.$error.required">
                            Surname Required!
                        </span>
                        <span ng-show="studentForm.surname.$invalid">
                            Surname Invalid!
                        </span>
                        <span ng-show="studentForm.gender.$error.required">
                            Gender Required!
                        </span>
                        <span ng-show="studentForm.gender.$invalid">
                            Gender Invalid!
                        </span>
                        <span ng-show="studentForm.birthday.$error.required">
                            Birthday Required!
                        </span>
                        <span ng-show="studentForm.birthday.$invalid">
                            Birthday Invalid!
                        </span>
                        <span ng-show="studentForm.phone.$error.required">
                            Phone Required!
                        </span>
                        <span ng-show="studentForm.phone.$invalid">
                            Phone Invalid!
                        </span>
                        <span ng-show="studentForm.country.$error.required">
                            Country Required!
                        </span>
                        <span ng-show="studentForm.state.$invalid">
                            State Invalid!
                        </span>
                        <span ng-show="studentForm.address.$error.required">
                            Address Required!
                        </span>
                        <span ng-show="studentForm.address.$invalid">
                            Address Invalid!
                        </span>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </div>

            <div class="formSignUp">

                <div class="internalForm">

                    <form ng-submit="saveStudent()" name="studentForm" method="post" novalidate>

                        <div class="formHead mb-0 pt-4">Sign Up to chatting system</div>
                        <small class="form-text text-muted text-left mb-3">* field required</small>

                        <div class="form-row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="email">*Email</label>
                                    <input class="form-control w-75" type="email" ng-model="student.email"
                                           ng-minlength="5" ng-maxlength="200" name="email"
                                           ng-class="{'is-invalid': submitted &&
                                           (studentForm.email.$error.required || studentForm.email.$invalid),
                                           'is-valid': studentForm.email.$valid}"
                                           id="email" placeholder="Type your email" required/>
                                </div>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="inputPassword">*Password</label>
                                <input class="form-control w-75" type="password" ng-model="student.password"
                                       ng-minlength="8" ng-maxlength="100" required name="password"
                                       id="inputPassword" placeholder="Type your password"
                                       ng-class="{'is-invalid': submitted &&
                                       (studentForm.password.$error.required || studentForm.password.$invalid),
                                       'is-valid': studentForm.password.$valid}"/>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="reInputPassword">*re-Password</label>
                                <input class="form-control w-75" type="password" ng-model="student.rePassword"
                                       ng-minlength="8" ng-maxlength="100" required name="rePassword"
                                       id="reInputPassword" placeholder="re-type your password"
                                       ng-class="{'is-invalid': submitted &&
                                       (studentForm.rePassword.$error.required || studentForm.rePassword.$invalid),
                                       'is-valid': studentForm.rePassword.$valid}"/>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="inputForename">*Forename</label>
                                <input class="form-control w-75" type="text" ng-model="student.forename"
                                       ng-minlength="2" ng-maxlength="50" required name="forename"
                                       id="inputForename" placeholder="Type your first name"
                                       ng-class="{'is-invalid': submitted &&
                                       (studentForm.forename.$error.required || studentForm.forename.$invalid),
                                       'is-valid': studentForm.forename.$valid}"/>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="inputSurname">*Surname</label>
                                <input class="form-control w-75" type="text" ng-model="student.surname"
                                       ng-minlength="2" ng-maxlength="50" required name="surname"
                                       id="inputSurname" placeholder="Type your last name"
                                       ng-class="{'is-invalid': submitted &&
                                       (studentForm.surname.$error.required || studentForm.surname.$invalid),
                                       'is-valid': studentForm.surname.$valid}"/>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="inputGender">*Gender</label>
                                <br>
                                <select class="custom-select w-75" ng-model="student.gender" id="inputGender"
                                        name="gender" required
                                        ng-class="{'is-invalid': submitted &&
                                        (studentForm.gender.$error.required || studentForm.gender.$invalid),
                                        'is-valid': studentForm.gender.$valid}">
                                    <option selected disabled value="">Choose...</option>
                                    <option value="MALE">Male</option>
                                    <option value="FEMALE">Female</option>
                                </select>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="inputBirthday">*Birthday</label>
                                <br>
                                <input class="form-control w-75" type="date" ng-model="student.birthday"
                                       id="inputBirthday" name="birthday" required
                                       ng-class="{'is-invalid': submitted &&
                                       (studentForm.birthday.$error.required ||studentForm.birthday.$invalid),
                                       'is-valid': studentForm.birthday.$valid}"/>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="inputPhone">*Phone</label>
                                <input class="form-control w-75" type="text" ng-model="student.phone"
                                       ng-minlength="10" ng-maxlength="20" required ng-pattern="/^\d+$/"
                                       id="inputPhone" placeholder="Type your phone number" name="phone"
                                       ng-class="{'is-invalid': submitted &&
                                       (studentForm.phone.$error.required || studentForm.phone.$invalid),
                                       'is-valid': studentForm.phone.$valid}"/>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="inputCountry">*Country</label>
                                <br>
                                <select class="custom-select w-75" name="country" ng-model="student.country"
                                        id="inputCountry" required
                                        ng-class="{'is-invalid': submitted &&
                                        (studentForm.country.$error.required || studentForm.country.$invalid),
                                        'is-valid': studentForm.country.$valid}">
                                    <option selected disabled value="">Select Country...</option>
                                    <option ng-repeat="countries in countryList" value="{{countries.name}}">
                                        {{countries.name}}
                                    </option>
                                </select>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="inputState">State</label>
                                <br>
                                <select class="custom-select w-75" ng-model="student.state" name="state"
                                        id="inputState" required
                                        ng-class="{'is-invalid': submitted &&
                                        (studentForm.state.$error.required || studentForm.state.$invalid),
                                        'is-valid': studentForm.state.$valid}">
                                    <option selected disabled value="">Select State...</option>
                                    <option ng-repeat="states in stateList[student.country]" value='{{states.name}}'>
                                        {{states.name}}
                                    </option>
                                </select>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="inputAddress">Address</label>
                                <input class="form-control w-75" type="text" ng-model="student.address"
                                       ng-minlength="2" ng-maxlength="200" required name="address"
                                       id="inputAddress" placeholder="Type your Address"
                                       ng-class="{'is-invalid': submitted &&
                                       (studentForm.address.$error.required || studentForm.address.$invalid),
                                       'is-valid': studentForm.address.$valid}"/>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group col-md-6"></div>
                            <div class="form-group col-md-6">
                                <input class="btn btn-success w-50 p-2" type="submit" value="Sign Up "
                                       style="margin: 5% 20% 3%" ng-disabled=""/>
                            </div>
                        </div>

                    </form>

                </div>
            </div>
        </div>

    </div>
</main>

<footer>
    <nav class="navbar navbar-dark position-relative mb-0" style="background-color: #d3c7cd; height: 9em; width: 100%">
        <p>Louay Amr © 2020</p>
    </nav>
</footer>

</body>
</html>
