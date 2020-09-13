/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.factory('GetUserStatusService', function () {
    return {
        isUserOnline: function isUserOnline($http, $location, $scope, $sce, sessionEmail, emailUri) {
            return $http({
                method: 'GET',
                port: 8443,
                url: 'https://localhost:8443/review/account/' + sessionEmail + '/' + emailUri + '/user_status', //FIXME
                headers: {'content-type': 'application/json'},
                contentType: "application/json; charset=utf-8",
                async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
                cache: true,    //This will force requested pages not to be cached by the browser
                processData: false, //To avoid making query String instead of JSON
                timeout: 4000
            }).then(
                function successCallback(response) {
                    $scope.isUserOnline = response.data;

                }, function errorCallback(response) {
                    $scope.isPageError = true;
                    let errorData = response.data;
                    if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                        $scope.errorRender = $sce.trustAsHtml(errorData);
                    } else {
                        $scope.pageErrorMessage = errorData;
                    }
                    $scope.submitted = false;

                });
        },

        isUserAtCourse: function isUserAtCourse($http, $location, $scope, $sce, sessionEmail, emailUri) {
            return $http({
                method: 'GET',
                port: 8443,
                url: 'https://localhost:8443/review/account/' + sessionEmail + '/' + emailUri + '/user_at_course', //FIXME
                headers: {'content-type': 'application/json'},
                contentType: "application/json; charset=utf-8",
                async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
                cache: true,    //This will force requested pages not to be cached by the browser
                processData: false, //To avoid making query String instead of JSON
                timeout: 4000
            }).then(
                function successCallback(response) {
                    $scope.isUserAtCourse = response.data;

                }, function errorCallback(response) {
                    $scope.isPageError = true;
                    let errorData = response.data;
                    if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                        $scope.errorRender = $sce.trustAsHtml(errorData);
                    } else {
                        $scope.pageErrorMessage = errorData;
                    }
                    $scope.submitted = false;

                });
        },
    };
});
