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
    let emailUri; //https://localhost:8443/student/student_home/{email}
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

    function getEmail(email) {
        emailUri = email;
        $scope.submitted = false;
        $scope.updatePassword = function () {
            $scope.submitted = true;
            if ($scope.editPasswordForm.$valid) {
                if ($scope.editPassword.passwordNew === $scope.editPassword.passwordReNew) {
                    UpdatePasswordService.updatePasswordService($http, $location, $scope, emailUri);
                } else {
                    $scope.editPasswordForm.$valid = false;
                    $scope.editPasswordForm.$error = true;
                    $scope.passwordErrorMessage = 'password did not match!.';
                }
            }
        };
    }
}

