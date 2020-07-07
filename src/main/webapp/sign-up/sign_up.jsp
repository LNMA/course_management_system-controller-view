<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <style>
        @import url(../libr/bootstrap-4.5.0/css/bootstrap.min.css);
        @import url(student-sign_up-css.css);
        @import url(../libr/bootstrap-formHelper-2.3.0/css/bootstrap-formhelpers.min.css);
    </style>
    <script src="../libr/jQuery-3.5.1/jquery-3.5.1.min.js"></script>
    <script src="../libr/popper-2.4.3/popper.min.js"></script>
    <script src="../libr/bootstrap-4.5.0/js/bootstrap.bundle.min.js"></script>
    <script src="../libr/bootstrap-formHelper-2.3.0/js/bootstrap-formhelpers.min.js"></script>
    <script src="../libr/angularJS-1.8.0/angular.min.js"></script>
    <script src="student-sign_up-js.js"></script>
    <title>Sign Up in Chatting`by Louay Amr`</title>
</head>
<body ng-app="signUpPage">
<header>
    <div class="jumbotron mb-0 h2 text-center">
        Course Management System
    </div>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: #3e3c4e;">
        <p class="text-light text-capitalize h3">Sign Up</p>
    </nav>
</header>

<main>
    <div class="mainBody">

        <div id="topAlert">
            <div class="container">
                <div class="alert alert-success" id="myAlert">
                    <strong>Success!</strong> You are sign up successfully, now go and <a href="../signin/login.jsp">login.</a>
                    <button type="button" class="close alert-dismissible" data-dismiss="alert">&times;</button>
                </div>
            </div>


            <div class="container">
                <div class="alert alert-danger" id="myAlert">
                    <strong>Error!</strong> Its seem something wrong, try again.
                    <button type="button" class="close alert-dismissible" data-dismiss="alert">&times;</button>
                </div>
            </div>
        </div>

        <div class="formSignUp">

            <div class="internalForm">

                <form ng-controller="registerForm" name="signUpForm" onsubmit="return signUpValidateForm()">

                    <div class="formHead mb-0 pt-4">Sign Up to chatting system</div>
                    <small class="form-text text-muted text-left mb-3">* field required</small>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputUsername">*Username</label>
                            <input class="form-control w-75" type="text" maxlength="30" name="username"
                                   id="inputUsername"
                                   placeholder="Type your username"/>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputPassword">*Password</label>
                            <input class="form-control w-75" type="password" maxlength="30" name="password"
                                   id="inputPassword"
                                   placeholder="Type your password"/>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputFirstName">*First Name</label>
                            <input class="form-control w-75" type="text" maxlength="50" name="firstName"
                                   id="inputFirstName"
                                   placeholder="Type your first name"/>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputLastName">*Last Name</label>
                            <input class="form-control w-75" type="text" maxlength="50" name="lastName"
                                   id="inputLastName"
                                   placeholder="Type your last name"/>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputGender">*Gender</label>
                            <br>
                            <select class="custom-select w-75" name="gender" id="inputGender">
                                <option selected disabled value="">Choose...</option>
                                <option value="male">Male</option>
                                <option value="female">Female</option>
                            </select>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputBirthday">*Birthday</label>
                            <br>
                            <input class="form-control w-75" type="date" name="birthday" id="inputBirthday"/>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputTelephone">*Telephone</label>
                            <input class="form-control w-75" type="text" maxlength="11" name="telephone"
                                   id="inputTelephone"
                                   placeholder="Type your telephone number"/>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputEmail">*Email</label>
                            <input class="form-control w-75" type="email" maxlength="65" name="email" id="inputEmail"
                                   placeholder="Type your email"/>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputCountry">*Country</label>
                            <br>
                            <select class="custom-select bfh-countries w-75" data-country="JO" id="inputCountry"
                                    name="country"></select>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputState">State</label>
                            <br>
                            <select class="custom-select bfh-states w-75" data-country="inputCountry" data-state="AM"
                                    id="inputState" name="state"></select>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-9">
                            <label for="inputAddress">Address</label>
                            <input class="form-control w-75" type="text" maxlength="150" name="address"
                                   id="inputAddress"
                                   placeholder="Type your Address"/>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6"></div>
                        <div class="form-group col-md-6">
                            <input class="btn btn-success w-50 p-2" style="margin: 3% 25% 3%" type="submit"
                                   value="Sign Up"/>
                        </div>
                    </div>

                </form>

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

