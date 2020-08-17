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
LoginCtrl.$inject = ['$scope', '$http', 'LoginAccountsSubmitService'];

function LoginCtrl($scope, $http, LoginAccountsSubmitService) {
    $scope.submitted = false;
    $scope.loginAccountsSubmit = function () {
        $scope.submitted = true;
        $scope.isError = false;
        if ($scope.loginForm.$valid) {
            console.log($scope.account.email, $scope.account.password, $scope.account.rememberMe);
            LoginAccountsSubmitService.loginSubmitForm($scope.account)
                .then(function successCallback(response) {
                    console.log(response.data);
                    $scope.account = null;
                    $scope.submitted = false;
                }, function errorCallback(response) {
                    if (response.status === 406) {
                        $scope.loginForm.email.$invalid = true;
                        $scope.isError = true;
                        $scope.submitErrorMessage = response.data;
                    } else {
                        $scope.loginForm.$invalid = true;
                        $scope.isError = true;
                        $scope.submitErrorMessage = response.data;
                    }
                });
        }
    };
}
