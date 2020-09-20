/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('StudentHomeController', StudentHomeCtrl);
StudentHomeCtrl.$inject = ['$scope', '$http', '$location', '$sce', '$window', 'GetStudentHomeInfoService',
    'UpdateStudentDetailsService'];

function StudentHomeCtrl($scope, $http, $location, $sce, $window, GetStudentHomeInfoService,
                         UpdateStudentDetailsService) {
    let emailUri = $location.absUrl().toString().split('/')[5]; //https://localhost:8443/student/student_home/{email}
    if ($location.absUrl().toString().split('/')[4] !== 'student_home') {
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
                emailUri = response.data;
                GetStudentHomeInfoService.getStudentInfo($http, $location, $scope, $sce, emailUri);
                $scope.sessionId = emailUri;
                $scope.updatePictureUrl = 'https://localhost:8443/user/update/' + emailUri + '/profile_picture-update'; //FIXME
                $scope.joinToCourseUrl = 'https://localhost:8443/student/student_home/' + emailUri + '/to_my_course/';//FIXME
                $scope.submitted = false;
                $scope.updateInterests = function () {
                    $scope.submitted = true;
                    if ($scope.editInterestsForm.$valid) {
                        UpdateStudentDetailsService.updateInterestsService($http, $location, $scope, emailUri);
                    }
                };

                $scope.updateHeadline = function () {
                    $scope.submitted = true;
                    if ($scope.editHeadlineForm.$valid) {
                        UpdateStudentDetailsService.updateHeadlineService($http, $location, $scope, emailUri);
                    }
                };
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
    } else {
        GetStudentHomeInfoService.getStudentInfo($http, $location, $scope, $sce, emailUri);
        $scope.sessionId = $location.absUrl().toString().split('/')[5];
        $scope.updatePictureUrl = 'https://localhost:8443/user/update/' + emailUri + '/profile_picture-update'; //FIXME
        $scope.joinToCourseUrl = 'https://localhost:8443/student/student_home/' + emailUri + '/to_my_course/';//FIXME
        $scope.submitted = false;
        $scope.updateInterests = function () {
            $scope.submitted = true;
            if ($scope.editInterestsForm.$valid) {
                UpdateStudentDetailsService.updateInterestsService($http, $location, $scope, emailUri);
            }
        };

        $scope.updateHeadline = function () {
            $scope.submitted = true;
            if ($scope.editHeadlineForm.$valid) {
                UpdateStudentDetailsService.updateHeadlineService($http, $location, $scope, emailUri);
            }
        };
    }
}

