/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('UserDetailModalController', UserDetailModalCtrl);
UserDetailModalCtrl.$inject = ['$scope', '$http', '$location', 'CountryStateService', 'UpdateUserDetailService'];

function UserDetailModalCtrl($scope, $http, $location, CountryStateService, UpdateUserDetailService) {
    let emailUri;
    $http({
        method: 'GET',
        port: 8443,
        url: "https://localhost:8443/session_id", //FIXME
        headers: {'content-type': 'application/json'},
        contentType: "application/json; charset=utf-8",
        async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
        cache: false,    //This will force requested pages not to be cached by the browser
        processData: false, //To avoid making query String instead of JSON
        timeout: 5000,
    }).then(
        function successCallback(response) {
            getEmail(response.data);
        }, function errorCallback(response) {
            $scope.submitted = false;
            $scope.isPageError = true;
            let errorData = response.data;
            if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                $scope.errorRender = $sce.trustAsHtml(errorData);
            } else {
                $scope.pageErrorMessage = errorData;
            }
        });
    $scope.countryList = CountryStateService.allCountry();
    $scope.stateList = CountryStateService.allState();
    $scope.submitted = false;

    function getEmail(email) {
        emailUri = email;
        $scope.updateForename = function () {
            $scope.submitted = true;
            if ($scope.editForenameForm.$valid) {
                UpdateUserDetailService.updateForenameService($http, $location, $scope, emailUri);
            }
        };
        $scope.updateSurname = function () {
            $scope.submitted = true;
            if ($scope.editSurnameForm.$valid) {
                UpdateUserDetailService.updateSurnameService($http, $location, $scope, emailUri);
            }
        };
        $scope.updateGender = function () {
            $scope.submitted = true;
            if ($scope.editGenderForm.$valid) {
                UpdateUserDetailService.updateGenderService($http, $location, $scope, emailUri);
            }
        };
        $scope.updateBirthday = function () {
            $scope.submitted = true;
            if ($scope.editBirthdayForm.$valid) {
                UpdateUserDetailService.updateBirthdayService($http, $location, $scope, emailUri);
            }
        };
        $scope.updatePhone = function () {
            $scope.submitted = true;
            if ($scope.editPhoneForm.$valid) {
                UpdateUserDetailService.updatePhoneService($http, $location, $scope, emailUri);
            }
        };
        $scope.updateFullAddress = function () {
            $scope.submitted = true;
            if ($scope.editAddressForm.$valid) {
                UpdateUserDetailService.updateFullAddressService($http, $location, $scope, emailUri);
            }
        };
    }
}


