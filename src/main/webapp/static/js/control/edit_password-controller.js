/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('EditPasswordController', EditPasswordCtrl);
EditPasswordCtrl.$inject = ['$scope', '$http', '$location', 'UpdatePasswordService'];

function EditPasswordCtrl($scope, $http, $location, UpdatePasswordService) {
    $scope.submitted = false;
    $scope.updatePassword = function () {
        $scope.submitted = true;
        if ($scope.editPasswordForm.$valid) {
            if ($scope.editPassword.passwordNew === $scope.editPassword.passwordReNew) {
                UpdatePasswordService.updatePasswordService($http, $location, $scope);
            } else {
                $scope.editPasswordForm.$valid = false;
                $scope.editPasswordForm.$error = true;
                $scope.passwordErrorMessage = 'password did not match!.';
            }
        }
    };
}

