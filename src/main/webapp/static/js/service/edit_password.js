/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('UpdatePasswordService', [function () {
    this.updatePasswordService = function updatePasswordService($http, $location, $scope, emailUri) {
        return $http({
            method: 'POST',
            port: 8443,
            url: 'https://localhost:8443/student/student_home/' + emailUri + '/password-update', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                oldPassword: $scope.editPassword.passwordOld,
                newPassword: $scope.editPassword.passwordNew,
                reNewPassword: $scope.editPassword.passwordReNew
            }
        }).then(
            function successCallback(response) {
                $scope.submitted = false;
                $scope.editPassword = null;
                $scope.password = response.data;
                $scope.isEditPasswordSuccess = true;
                $scope.passwordSuccessMessage = 'password change successfully';

            }, function errorCallback(response) {
                $scope.editPasswordForm.$valid = false;
                $scope.editPasswordForm.$error = true;
                $scope.passwordErrorMessage = response.data;
            });
    }
}]);