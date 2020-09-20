/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('StudentRegisterController', RegisterCtrl);
RegisterCtrl.$inject = ['$scope', '$http', '$sce', 'RegisterStudentSubmitService', 'CountryStateService'];

function RegisterCtrl($scope, $http, $sce, RegisterStudentSubmitService, CountryStateService) {
    $scope.loading = false;
    $scope.submitted = false;
    $scope.countryList = CountryStateService.allCountry();
    $scope.stateList = CountryStateService.allState();

    $scope.saveStudent = function () {
        $scope.submitted = true;
        $scope.isError = false;
        if ($scope.studentForm.$valid) {
            if ($scope.student.password === $scope.student.rePassword) {
                $scope.loading = true;
                RegisterStudentSubmitService.studentSubmitForm($scope.student)
                    .then(function successCallback(response) {
                        $scope.loading = false;
                        $scope.isSuccess = true;
                        $scope.submitSuccessMessage = response.data;
                        $scope.student = null;
                        $scope.submitted = false;
                    }, function errorCallback(response) {
                        $scope.loading = false;
                        let errorData = response.data;
                        if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                            $scope.errorRender = $sce.trustAsHtml(errorData);
                        } else {
                            if (response.status === 406) {
                                $scope.studentForm.email.$invalid = true;
                                $scope.isError = true;
                                $scope.submitErrorMessage = errorData;
                            } else {
                                $scope.isError = true;
                                $scope.studentForm.$invalid = true;
                                $scope.submitErrorMessage = errorData;
                            }
                        }
                    });
            } else {
                $scope.loading = false;
                $scope.studentForm.password.$invalid = true;
                $scope.studentForm.rePassword.$invalid = true;
                $scope.isError = true;
                $scope.submitErrorMessage = 'Password did not match!';
            }
        }


    };
}
