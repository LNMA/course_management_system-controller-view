/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/

//password modal
$(document).on('click', '#dropdownProfile', function () {
    'use strict';
    $('#changePasswordModal').empty();
    $('#changePasswordModal').append(`<div class="modal fade" id="passwordModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Change password</h4>
                    <button type="button" class="close" data-dismiss="modal">
                        &times;
                    </button>
                </div>
                <form name="passwordForm" action="../UpdateUserProfile" method="post" onsubmit="return passwordValidateForm()">
                <input type="text" value="p1pass2c" name="fieldCode" readonly hidden>
                <div class="modal-body">
                    <div class="container-fluid">
                    <div id="passwordAlert"></div>
                        <div class="row row-cols-md-2">
                            <div class="col-md-4">
                                <label class="font-weight-bold col-md-12" for="oldPasswordModal">Old Password: </label>
                                <label class="font-weight-bold col-md-12 mt-3" for="newPasswordModal">New Password: </label>
                                <label class="font-weight-bold col-md-12 mt-3" for="re-newPasswordModal">re-New Password: </label>
                            </div>
                            <div class="col-md-6">
                                <input id="oldPasswordModal" name="passwordOld" type="password" class="form-control col-md-12" placeholder="type old password" maxlength="30">
                                <input id="newPasswordModal" name="passwordNew" type="password" class="form-control col-md-12 mt-2" placeholder="type old password" maxlength="30">
                                <input id="re-newPasswordModal" name="passwordReNew" type="password" class="form-control col-md-12 mt-2" placeholder="re-type old password" maxlength="30">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">
                        Save
                    </button>
                    <button type="button" class="btn btn-danger"
                            data-dismiss="modal">Close
                    </button>
                </div>
                </form>
            </div>
        </div>
    </div>`);
});
function passwordValidateForm() {
    'use strict';
    let oldPassword = document.forms['passwordForm']['passwordOld'].value;
    let newPassword = document.forms['passwordForm']['passwordNew'].value;
    let reNewPassword = document.forms['passwordForm']['passwordReNew'].value;

    if ((newPassword === '') || (oldPassword === '') || (reNewPassword === '') ) {
        document.getElementById('passwordAlert').innerHTML = '   <div class=\"container\">\n' +
            '        <div class=\"alert alert-danger alert-dismissible\" data-dismiss=\"alert\" id=\"myAlert\">\n' +
            '            <button type=\"button\" class=\"close\">&times;</button>\n' +
            '            <strong>Error!</strong> All required field must be filled.\n' +
            '        </div>\n' +
            '    </div>';
        return false;
    }

    if (/\s/.test(oldPassword) || /\s/.test(newPassword) || /\s/.test(reNewPassword)) {
        document.getElementById('passwordAlert').innerHTML = '   <div class=\"container\">\n' +
            '        <div class=\"alert alert-danger alert-dismissible\" data-dismiss=\"alert\" id=\"myAlert\">\n' +
            '            <button type=\"button\" class=\"close\">&times;</button>\n' +
            '            <strong>Error!</strong> Password must not contain whitespace.\n' +
            '        </div>\n' +
            '    </div>';
        return false;
    }

    if (oldPassword.length < 8 || newPassword.length < 8 || reNewPassword.length < 8) {
        document.getElementById('passwordAlert').innerHTML = '   <div class=\"container\">\n' +
            '        <div class=\"alert alert-danger alert-dismissible\" data-dismiss=\"alert\" id=\"myAlert\">\n' +
            '            <button type=\"button\" class=\"close\">&times;</button>\n' +
            '            <strong>Error!</strong> Password must be at least 8 character.\n' +
            '        </div>\n' +
            '    </div>';
        return false;
    }

    if (newPassword !== reNewPassword) {
        document.getElementById('passwordAlert').innerHTML = '   <div class=\"container\">\n' +
            '        <div class=\"alert alert-danger alert-dismissible\" data-dismiss=\"alert\" id=\"myAlert\">\n' +
            '            <button type=\"button\" class=\"close\">&times;</button>\n' +
            '            <strong>Error!</strong> new password did not match.\n' +
            '        </div>\n' +
            '    </div>';
        return false;
    }
}

//image modal
$(document).on('click', '#dropdownProfile', function () {
    'use strict';
    $('#changeUserPictureModal').empty();
    $('#changeUserPictureModal').append(`<div class="modal fade" id="ImageModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Change User Picture</h4>
                    <button type="button" class="close" data-dismiss="modal">
                        &times;
                    </button>
                </div>
                <form action="../UpdateUserProfile" method="post" enctype="multipart/form-data">
                    <input type="text" value="p0img1c" name="fieldCode" readonly hidden>
                    <div class="modal-body">
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" id="inputFileModal" name="filename" accept="image/*">
                                <label class="custom-file-label" for="inputFileModal">Choose file...</label>
                            </div>
                            <script>
                            // Add the following code if you want the name of the file appear on select
                            $(".custom-file-input").on("change", function() {
                            let fileName = $(this).val().split("\\\\").pop();
                            $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
                            });
                            </script>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">
                            Sava
                        </button>
                        <button type="button" class="btn btn-danger"
                                data-dismiss="modal">Close
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>`);
});

//first name modal
$(document).on('click', '#dropdownProfile', function () {
    'use strict';
    $('#changeFNameModal').empty();
    $('#changeFNameModal').append(`<div class="modal fade" id="firstNameModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Change FirstName</h4>
                    <button type="button" class="close" data-dismiss="modal">
                        &times;
                    </button>
                </div>
                <form name="fNameForm" action="../UpdateUserProfile" method="post" onsubmit="return fNameValidateForm()">
                <input type="text" value="p2fName3c" name="fieldCode" readonly hidden>
                <div class="modal-body">
                    <div class="container-fluid">
                    <div id="fNameAlert"></div>
                        <div class="row row-cols-md-2">
                            <div class="col-md-4">
                                <label class="font-weight-bold col-md-12" for="fnameModal">First Name: </label>                  
                            </div>
                            <div class="col-md-6">
                                <input id="fnameModal" name="firstName" type="text" class="form-control col-md-12" placeholder="type new first name" maxlength="50">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">
                        Save
                    </button>
                    <button type="button" class="btn btn-danger"
                            data-dismiss="modal">Close
                    </button>
                </div>
                </form>
            </div>
        </div>
    </div>`);
});
function fNameValidateForm() {
    'use strict';
    let fName = document.forms['fNameForm']['firstName'].value;


    if ((fName === '')) {
        document.getElementById('fNameAlert').innerHTML = '   <div class=\"container\">\n' +
            '        <div class=\"alert alert-danger alert-dismissible\" data-dismiss=\"alert\" id=\"myAlert\">\n' +
            '            <button type=\"button\" class=\"close\">&times;</button>\n' +
            '            <strong>Error!</strong> All required field must be filled.\n' +
            '        </div>\n' +
            '    </div>';
        return false;
    }
}

//last name modal
$(document).on('click', '#dropdownProfile', function () {
    'use strict';
    $('#changeLNameModal').empty();
    $('#changeLNameModal').append(`<div class="modal fade" id="lastNameModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Change FirstName</h4>
                    <button type="button" class="close" data-dismiss="modal">
                        &times;
                    </button>
                </div>
                <form name="lNameForm" action="../UpdateUserProfile" method="post" onsubmit="return lNameValidateForm()">
                 <input type="text" value="p2lName4c" name="fieldCode" readonly hidden>
                <div class="modal-body">
                    <div class="container-fluid">
                    <div id="lNameAlert"></div>
                        <div class="row row-cols-md-2">
                            <div class="col-md-4">
                                <label class="font-weight-bold col-md-12" for="lnameModal">Last Name: </label>                  
                            </div>
                            <div class="col-md-6">
                                <input id="lnameModal" name="lastName" type="text" class="form-control col-md-12" placeholder="type new last name" maxlength="50">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">
                        Save
                    </button>
                    <button type="button" class="btn btn-danger"
                            data-dismiss="modal">Close
                    </button>
                </div>
                </form>
            </div>
        </div>
    </div>`);
});
function lNameValidateForm() {
    'use strict';
    let lName = document.forms['lNameForm']['lastName'].value;
    if ((lName === '')) {
        document.getElementById('lNameAlert').innerHTML = '   <div class=\"container\">\n' +
            '        <div class=\"alert alert-danger alert-dismissible\" data-dismiss=\"alert\" id=\"myAlert\">\n' +
            '            <button type=\"button\" class=\"close\">&times;</button>\n' +
            '            <strong>Error!</strong> All required field must be filled.\n' +
            '        </div>\n' +
            '    </div>';
        return false;
    }
}

//gender modal
$(document).on('click', '#dropdownProfile', function () {
    'use strict';
    $('#changeGenderModal').empty();
    $('#changeGenderModal').append(`<div class="modal fade" id="genderModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Change Gender</h4>
                    <button type="button" class="close" data-dismiss="modal">
                        &times;
                    </button>
                </div>
                <form name="genderForm" action="../UpdateUserProfile" method="post" onsubmit="return genderValidateForm()">
                <input type="text" value="p2gender5c" name="fieldCode" readonly hidden>
                <div class="modal-body">
                    <div class="container-fluid">
                    <div id="genderAlert"></div>
                        <div class="row row-cols-md-2">
                            <div class="col-md-4">
                                <label class="font-weight-bold col-md-12" for="inputGender">Gender: </label>                  
                            </div>
                            <div class="col-md-6">
                                <select class="custom-select w-75" name="gender" id="inputGender">
                                    <option selected disabled value="">Choose...</option>
                                    <option value="male">Male</option>
                                    <option value="female">Female</option>
                                </select>   
                            </div>
                            <br>
                            <br>                        
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">
                        Save
                    </button>
                    <button type="button" class="btn btn-danger"
                            data-dismiss="modal">Close
                    </button>
                </div>
                </form>
            </div>
        </div>
    </div>`);
});
function genderValidateForm() {
    'use strict';
    let gender = document.forms['genderForm']['gender'].value;
    if ((gender === '')) {
        document.getElementById('genderAlert').innerHTML = '   <div class=\"container\">\n' +
            '        <div class=\"alert alert-danger alert-dismissible\" data-dismiss=\"alert\" id=\"myAlert\">\n' +
            '            <button type=\"button\" class=\"close\">&times;</button>\n' +
            '            <strong>Error!</strong> All required field must be filled.\n' +
            '        </div>\n' +
            '    </div>';
        return false;
    }
}

//birthday modal
$(document).on('click', '#dropdownProfile', function () {
    'use strict';
    $('#changeBirthdayModal').empty();
    $('#changeBirthdayModal').append(`<div class="modal fade" id="birthdayModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Change Birthday</h4>
                    <button type="button" class="close" data-dismiss="modal">
                        &times;
                    </button>
                </div>
                <form name="birthdayForm" action="../UpdateUserProfile" method="post" onsubmit="return birthdayValidateForm()">
                <input type="text" value="p2birth6c" name="fieldCode" readonly hidden>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div id="birthdayAlert"></div>
                        <div class="row row-cols-md-2">
                            <div class="col-md-4">
                                <label class="font-weight-bold col-md-12" for="inputBirthday">Birthday: </label>                  
                            </div>
                            <div class="col-md-6">
                                <input class="form-control w-75" type="date" name="birthday" id="inputBirthday"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">
                        Save
                    </button>
                    <button type="button" class="btn btn-danger"
                            data-dismiss="modal">Close
                    </button>
                </div>
                </form>
            </div>
        </div>
    </div>`);
});
function birthdayValidateForm() {
    'use strict';
    let birthday = document.forms['birthdayForm']['birthday'].value;

    if (birthday === '') {
        document.getElementById('birthdayAlert').innerHTML = '   <div class=\"container\">\n' +
            '        <div class=\"alert alert-danger alert-dismissible\" data-dismiss=\"alert\" id=\"myAlert\">\n' +
            '            <button type=\"button\" class=\"close\">&times;</button>\n' +
            '            <strong>Error!</strong> All required field must be filled.\n' +
            '        </div>\n' +
            '    </div>';
        return false;
    }
}

//telephone modal
$(document).on('click', '#dropdownProfile', function () {
    'use strict';
    $('#changeTelephoneModal').empty();
    $('#changeTelephoneModal').append(`<div class="modal fade" id="telephoneModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Change Telephone</h4>
                    <button type="button" class="close" data-dismiss="modal">
                        &times;
                    </button>
                </div>
                <form name="telephoneForm" action="../UpdateUserProfile" method="post" onsubmit="return telephoneValidateForm()">
                <input type="text" value="p2telephone7c" name="fieldCode" readonly hidden>
                <div class="modal-body">
                    <div class="container-fluid">
                      <div id="telephoneAlert"></div>
                        <div class="row row-cols-md-2">
                            <div class="col-md-4">
                                <label class="font-weight-bold col-md-12" for="inputTelephone">Telephone: </label>                  
                            </div>
                            <div class="col-md-6">
                                <input class="form-control w-75" type="text" maxlength="11" name="telephone"
                                   id="inputTelephone"
                                   placeholder="Type your telephone number"/>                           
                             </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">
                        Save
                    </button>
                    <button type="button" class="btn btn-danger"
                            data-dismiss="modal">Close
                    </button>
                </div>
                </form>
            </div>
        </div>
    </div>`);
});
function telephoneValidateForm() {
    'use strict';
    let telephone = document.forms['telephoneForm']['telephone'].value;
    if ((telephone === '')) {
        document.getElementById('telephoneAlert').innerHTML = '   <div class=\"container\">\n' +
            '        <div class=\"alert alert-danger alert-dismissible\" data-dismiss=\"alert\" id=\"myAlert\">\n' +
            '            <button type=\"button\" class=\"close\">&times;</button>\n' +
            '            <strong>Error!</strong> All required field must be filled.\n' +
            '        </div>\n' +
            '    </div>';
        return false;
    }

    let telNum = parseFloat(telephone);
    if (isNaN(telNum)) {
        document.getElementById('topAlert').innerHTML = '   <div class=\"container\">\n' +
            '        <div class=\"alert alert-danger alert-dismissible\" data-dismiss=\"alert\" id=\"myAlert\">\n' +
            '            <button type=\"button\" class=\"close\">&times;</button>\n' +
            '            <strong>Error!</strong> Telephone number must be integer number.\n' +
            '        </div>\n' +
            '    </div>';
        return false;
    }

    if (/[^0-9]/.test(telephone)) {
        document.getElementById('topAlert').innerHTML = '   <div class=\"container\">\n' +
            '        <div class=\"alert alert-danger alert-dismissible\" data-dismiss=\"alert\" id=\"myAlert\">\n' +
            '            <button type=\"button\" class=\"close\">&times;</button>\n' +
            '            <strong>Error!</strong> Telephone number must be integer number.\n' +
            '        </div>\n' +
            '    </div>';
        return false;
    }
}

//email modal
$(document).on('click', '#dropdownProfile', function () {
    'use strict';
    $('#changeEmailModal').empty();
    $('#changeEmailModal').append(`<div class="modal fade" id="emailModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Change Email</h4>
                    <button type="button" class="close" data-dismiss="modal">
                        &times;
                    </button>
                </div>
                <form name="emailForm" action="../UpdateUserProfile" method="post" onsubmit="return emailValidateForm()">
                <input type="text" value="p2email8c" name="fieldCode" readonly hidden>
                <div class="modal-body">
                    <div class="container-fluid">
                      <div id="emailAlert"></div>
                        <div class="row row-cols-md-2">
                            <div class="col-md-4">
                                <label class="font-weight-bold col-md-12" for="inputEmail">Email: </label>                  
                            </div>
                            <div class="col-md-6">
                                <input class="form-control w-75" type="email" maxlength="65" name="email" 
                                id="inputEmail" placeholder="Type your email"/>                          
                             </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">
                        Save
                    </button>
                    <button type="button" class="btn btn-danger"
                            data-dismiss="modal">Close
                    </button>
                </div>
                </form>
            </div>
        </div>
    </div>`);
});
function emailValidateForm() {
    'use strict';
    let email = document.forms['emailForm']['email'].value;

    if ((email === '')) {
        document.getElementById('emailAlert').innerHTML = '   <div class=\"container\">\n' +
            '        <div class=\"alert alert-danger alert-dismissible\" data-dismiss=\"alert\" id=\"myAlert\">\n' +
            '            <button type=\"button\" class=\"close\">&times;</button>\n' +
            '            <strong>Error!</strong> All required field must be filled.\n' +
            '        </div>\n' +
            '    </div>';
        return false;
    }

    if ((/\W+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/.test(email))) {
        document.getElementById('emailAlert').innerHTML = '   <div class=\"container\">\n' +
            '        <div class=\"alert alert-danger alert-dismissible\" data-dismiss=\"alert\" id=\"myAlert\">\n' +
            '            <button type=\"button\" class=\"close\">&times;</button>\n' +
            '            <strong>Error!</strong> Email form you entered is wrong.\n' +
            '        </div>\n' +
            '    </div>';
        return false;
    }
}

//address modal
$(document).on('click', '#dropdownProfile', function () {
    'use strict';
    $('#changeAddressModal').empty();
    $('#changeAddressModal').append(`<div class="modal fade" id="addressModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Change Email</h4>
                    <button type="button" class="close" data-dismiss="modal">
                        &times;
                    </button>
                </div>
                <form name="addressForm" action="../UpdateUserProfile" method="post" onsubmit="return addressValidateForm()">
                <input type="text" value="p2allAddress9c" name="fieldCode" readonly hidden>
                <div class="modal-body">
                    <div class="container-fluid">
                      <div id="addressAlert"></div>
                        <div class="row row-cols-md-2">
                            <div class="col-md-4">
                                <label class="font-weight-bold col-md-12" for="inputCountry">Country: </label>    
                                <label class="font-weight-bold col-md-12 mt-3" for="inputState">State: </label>                  
                                <label class="font-weight-bold col-md-12 mt-3" for="inputAddress">Address: </label>                                
                            </div>
                            <div class="col-md-6">
                                <select class="custom-select bfh-countries w-75" data-country="JO" id="inputCountry"
                                    name="country"></select>
                                 <select class="custom-select bfh-states w-75 mt-2" data-country="inputCountry" data-state="AM"
                                    id="inputState" name="state"></select>
                                 <input class="form-control mt-2" type="text" maxlength="150" name="address"
                                   id="inputAddress" placeholder="Type your Address"/>                                
                             </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">
                        Save
                    </button>
                    <button type="button" class="btn btn-danger"
                            data-dismiss="modal">Close
                    </button>
                </div>
                </form>
            </div>
        </div>
    </div>`);
});
function addressValidateForm() {
    'use strict';
    let country = document.forms['addressForm']['country'].value;
    let state = document.forms['addressForm']['state'].value;
    let address = document.forms['addressForm']['address'].value;

    if ((country === '') || (state === '') || (address === '')) {
        document.getElementById('addressAlert').innerHTML = '   <div class=\"container\">\n' +
            '        <div class=\"alert alert-danger alert-dismissible\" data-dismiss=\"alert\" id=\"myAlert\">\n' +
            '            <button type=\"button\" class=\"close\">&times;</button>\n' +
            '            <strong>Error!</strong> All required field must be filled.\n' +
            '        </div>\n' +
            '    </div>';
        return false;
    }
}
