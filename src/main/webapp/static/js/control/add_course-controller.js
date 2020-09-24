/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('AddCourseController', AddCourseCtrl);
AddCourseCtrl.$inject = ['$scope', '$http', '$location', '$sce'];

function AddCourseCtrl($scope, $http, $location, $sce) {
    $scope.addCourse = function (date, time) {
        let dateTime = new Date();
        dateTime.setUTCFullYear(date.getFullYear());
        dateTime.setUTCMonth(date.getMonth());
        dateTime.setUTCDate(date.getDate());
        let timeZoneOffset = dateTime.getTimezoneOffset() / 60;
        dateTime.setUTCHours(time.getHours() - timeZoneOffset);
        dateTime.setUTCMinutes(time.getMinutes());

        $scope.submitted = true;
        if ($scope.addCourseForm.$valid) {
            $http({
                method: 'POST',
                port: 8443,
                url: 'https://localhost:8443/course/add_course', //FIXME
                headers: {'content-type': 'application/json'},
                contentType: "application/json; charset=utf-8",
                async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
                cache: false,    //This will force requested pages not to be cached by the browser
                processData: false, //To avoid making query String instead of JSON
                timeout: 4000,
                data: {
                    courseName: $scope.course.name,
                    endDate: dateTime
                }
            }).then(
                function successCallback(response) {
                    $scope.submitted = false;
                    $scope.course = null;
                    $scope.addCourseForm = null;
                    $scope.isAddCourseSuccess = true;
                    $scope.addCourseSuccessMessage = response.data;

                }, function errorCallback(response) {
                    $scope.addCourseForm.$valid = false;
                    $scope.addCourseForm.$error = true;
                    $scope.addCourseErrorMessage = response.data;
                    $scope.isPageError = true;
                    let errorData = response.data;
                    if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                        $scope.errorRender = $sce.trustAsHtml(errorData);
                    } else {
                        $scope.pageErrorMessage = errorData;
                    }
                });
        }
    }
}