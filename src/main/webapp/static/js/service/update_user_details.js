/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('UpdateUserDetailService', [function () {
    this.updateForenameService = function updateForenameService($http, $location, $scope, emailUri) {
        return $http({
            method: 'PATCH',
            port: 8443,
            url: 'https://localhost:8443/user/update/' + emailUri + '/forename-update', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                forename: $scope.editForename
            }

        }).then(
            function successCallback(response) {
                $scope.submitted = false;
                $scope.editForename = null;
                $scope.forename = response.data;
                $scope.isEditForenameSuccess = true;
                $scope.forenameSuccessMessage = 'forename change successfully';

            }, function errorCallback(response) {
                $scope.editForenameForm.$valid = false;
                $scope.editForenameForm.$error = true;
                $scope.forenameErrorMessage = response.data;
            });
    }

    this.updateSurnameService = function updateSurnameService($http, $location, $scope, emailUri) {
        return $http({
            method: 'PATCH',
            port: 8443,
            url: 'https://localhost:8443/user/update/' + emailUri + '/surname-update',//FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                surname: $scope.editSurname
            }

        }).then(
            function successCallback(response) {
                $scope.submitted = false;
                $scope.editSurname = null;
                $scope.surname = response.data;
                $scope.isEditSurnameSuccess = true;
                $scope.surnameSuccessMessage = 'surname change successfully';

            }, function errorCallback(response) {
                $scope.editSurnameForm.$valid = false;
                $scope.editSurnameForm.$error = true;
                $scope.surnameErrorMessage = response.data;
            });
    }

    this.updateGenderService = function updateGenderService($http, $location, $scope, emailUri) {
        return $http({
            method: 'PATCH',
            port: 8443,
            url: 'https://localhost:8443/user/update/' + emailUri + '/gender-update',//FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                gender: $scope.editGender
            }

        }).then(
            function successCallback(response) {
                $scope.submitted = false;
                $scope.editGender = null;
                $scope.gender = response.data;
                $scope.isEditGenderSuccess = true;
                $scope.genderSuccessMessage = 'gender change successfully';

            }, function errorCallback(response) {
                $scope.editGenderForm.$valid = false;
                $scope.editGenderForm.$error = true;
                $scope.genderErrorMessage = response.data;
            });
    }

    this.updateBirthdayService = function updateBirthdayService($http, $location, $scope, emailUri) {
        return $http({
            method: 'PATCH',
            port: 8443,
            url: 'https://localhost:8443/user/update/' + emailUri + '/birthday-update',//FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                birthday: $scope.editBirthday
            }

        }).then(
            function successCallback(response) {
                $scope.submitted = false;
                $scope.editBirthday = null;
                $scope.birthday = response.data;
                $scope.isEditBirthdaySuccess = true;
                $scope.birthdaySuccessMessage = 'birthday change successfully';

            }, function errorCallback(response) {
                $scope.editBirthdayForm.$valid = false;
                $scope.editBirthdayForm.$error = true;
                $scope.birthdayErrorMessage = response.data;
            });
    }

    this.updatePhoneService = function updatePhoneService($http, $location, $scope, emailUri) {
        return $http({
            method: 'PATCH',
            port: 8443,
            url: 'https://localhost:8443/user/update/' + emailUri + '/phone-update',//FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                phone: $scope.editPhone
            }
        }).then(
            function successCallback(response) {
                $scope.submitted = false;
                $scope.editPhone = null;
                $scope.phone = response.data;
                $scope.isEditPhoneSuccess = true;
                $scope.phoneSuccessMessage = 'phone change successfully';

            }, function errorCallback(response) {
                $scope.editPhoneForm.$valid = false;
                $scope.editPhoneForm.$error = true;
                $scope.phoneErrorMessage = response.data;
            });
    }

    this.updateFullAddressService = function updateFullAddressService($http, $location, $scope, emailUri) {
        return $http({
            method: 'PATCH',
            port: 8443,
            url: 'https://localhost:8443/user/update/' + emailUri + '/full_address-update',//FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                country: $scope.editfullAddress.country,
                state: $scope.editfullAddress.state,
                address: $scope.editfullAddress.address
            }
        }).then(
            function successCallback(response) {
                $scope.submitted = false;
                $scope.editfullAddress = null;
                $scope.country = response.data.country;
                $scope.state = response.data.state;
                $scope.address = response.data.address;
                console.log(response.data);
                $scope.isEditFullAddressSuccess = true;
                $scope.fullAddressSuccessMessage = 'full address change successfully';

            }, function errorCallback(response) {
                $scope.editAddressForm.$valid = false;
                $scope.editAddressForm.$error = true;
                $scope.fullAddressErrorMessage = response.data;
            });
    }
}]);