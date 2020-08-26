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
UserDetailModalCtrl.$inject = ['$scope', '$http', '$location', 'GetStudentHomeInfoService', 'CountryStateService',
    'UpdateUserDetailService'];

function UserDetailModalCtrl($scope, $http, $location, CountryStateService, UpdateUserDetailService) {
    $scope.countryList = CountryStateService.allCountry();
    $scope.stateList = CountryStateService.allState();
    $scope.submitted = false;

    $scope.updateForename = function () {
        $scope.submitted = true;
        if ($scope.editForenameForm.$valid) {
            UpdateUserDetailService.updateForenameService($http, $location, $scope);
        }
    };
    $scope.updateSurname = function () {
        $scope.submitted = true;
        if ($scope.editSurnameForm.$valid) {
            UpdateUserDetailService.updateSurnameService($http, $location, $scope);
        }
    };
    $scope.updateGender = function () {
        $scope.submitted = true;
        if ($scope.editGenderForm.$valid) {
            UpdateUserDetailService.updateGenderService($http, $location, $scope);
        }
    };
    $scope.updateBirthday = function () {
        $scope.submitted = true;
        if ($scope.editBirthdayForm.$valid) {
            UpdateUserDetailService.updateBirthdayService($http, $location, $scope);
        }
    };
    $scope.updatePhone = function () {
        $scope.submitted = true;
        if ($scope.editPhoneForm.$valid) {
            UpdateUserDetailService.updatePhoneService($http, $location, $scope);
        }
    };
    $scope.updateFullAddress = function () {
        $scope.submitted = true;
        if ($scope.editAddressForm.$valid) {
            UpdateUserDetailService.updateFullAddressService($http, $location, $scope);
        }
    };
    $scope.updateHeadline = function () {
        $scope.submitted = true;
        if ($scope.editHeadlineForm.$valid) {
            UpdateUserDetailService.updateHeadlineService($http, $location, $scope);
        }
    };
}


