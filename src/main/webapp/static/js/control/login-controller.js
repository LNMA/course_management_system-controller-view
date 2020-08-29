/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('LoginController', LoginCtrl);
LoginCtrl.$inject = ['$scope', '$http', '$window', '$sce', 'LoginAccountsSubmitService'];

function LoginCtrl($scope, $http, $window, $sce, LoginAccountsSubmitService) {
    $scope.loading = false;
    $scope.submitted = false;
    $scope.loginAccountsSubmit = function () {
        $scope.submitted = true;
        $scope.isError = false;
        if ($scope.loginForm.$valid) {
            $scope.loading = true;
            LoginAccountsSubmitService.loginSubmitForm($scope.account)
                .then(function successCallback(response) {
                    $scope.loading = false;
                    $scope.account = null;
                    $scope.submitted = false;
                    $window.location.href = "https://localhost:8443" + response.data;
                }, function errorCallback(response) {
                    $scope.loading = false;
                    let errorData = response.data;
                    if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                        $scope.errorRender = $sce.trustAsHtml(errorData);
                    } else {
                        if (response.status === 406) {
                            $scope.loginForm.email.$invalid = true;
                            $scope.isError = true;
                            $scope.submitErrorMessage = response.data;
                        } else {
                            $scope.loginForm.$invalid = true;
                            $scope.isError = true;
                            $scope.submitErrorMessage = response.data;
                        }
                    }
                });
        }
    };

}
