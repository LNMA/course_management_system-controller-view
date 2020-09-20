/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('InstructorRegisterController', RegisterCtrl);
RegisterCtrl.$inject = ['$scope', '$http', '$sce', 'CountryStateService', 'RegisterInstructorSubmitService'];

function RegisterCtrl($scope, $http, $sce, CountryStateService, RegisterInstructorSubmitService) {
    $scope.loading = false;
    $scope.submitted = false;
    $scope.countryList = CountryStateService.allCountry();
    $scope.stateList = CountryStateService.allState();

    $scope.saveInstructor = function () {
        $scope.submitted = true;
        if ($scope.instructorRegisterForm.$valid) {
            if ($scope.instructor.password === $scope.instructor.rePassword) {
                $scope.loading = true;
                RegisterInstructorSubmitService.instructorSubmitForm($http, $scope, $sce);
            } else {
                $scope.loading = false;
                $scope.instructorRegisterForm.password.$invalid = true;
                $scope.instructorRegisterForm.rePassword.$invalid = true;
                $scope.isError = true;
                $scope.submitErrorMessage = 'Password did not match!';
            }
        }
    }
}
