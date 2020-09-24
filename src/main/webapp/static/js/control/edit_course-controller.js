/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('EditCourseController', EditCourseCtrl);
EditCourseCtrl.$inject = ['$scope', '$location', '$http', '$sce'];

function EditCourseCtrl($scope, $location, $http, $sce) {
    let courseId = $location.absUrl().toString().split('/')[4];
    $scope.editCourseImgUrl = 'https://localhost:8443/course/' + courseId + '/update_course/course_picture';
    $scope.editCourseNameSub = function () {
        $scope.submitted = true;
        if ($scope.editCourseNameForm.$valid) {
            $http({
                method: 'PUT',
                port: 8443,
                url: 'https://localhost:8443/course/' + courseId + '/update_course/course_name', //FIXME
                headers: {'content-type': 'application/json'},
                contentType: "application/json; charset=utf-8",
                async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
                cache: true,    //This will force requested pages not to be cached by the browser
                processData: false, //To avoid making query String instead of JSON
                timeout: 4000,
                data: {
                    courseName: $scope.editCourseName,
                }
            }).then(
                function successCallback(response) {
                    $scope.submitted = false;
                    $scope.editCourseName = null;
                    $scope.editCourseNameForm = null;
                    $scope.isEditcourseNameSuccess = true;
                    $scope.editCourseNameSuccessMessage = 'course name updated successfully.';
                    $scope.courseName = response.data;

                }, function errorCallback(response) {
                    $scope.editCourseNameForm.$valid = false;
                    $scope.editCourseNameForm.$error = true;
                    $scope.editCourseNameErrorMessage = response.data;
                    let errorData = response.data;
                    if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                        $scope.errorRender = $sce.trustAsHtml(errorData);
                    } else {
                        $scope.editCourseNameErrorMessage = errorData;
                    }
                });
        }
    }

    $scope.editEndDate = function (date, time) {
        let dateTime = new Date();
        dateTime.setUTCFullYear(date.getFullYear());
        dateTime.setUTCMonth(date.getMonth());
        dateTime.setUTCDate(date.getDate());
        let timeZoneOffset = dateTime.getTimezoneOffset() / 60;
        dateTime.setUTCHours(time.getHours() - timeZoneOffset);
        dateTime.setUTCMinutes(time.getMinutes());

        $scope.submitted = true;
        if ($scope.editEndDateForm.$valid) {
            $http({
                method: 'PUT',
                port: 8443,
                url: 'https://localhost:8443/course/' + courseId + '/update_course/course_end_date', //FIXME
                headers: {'content-type': 'application/json'},
                contentType: "application/json; charset=utf-8",
                async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
                cache: false,    //This will force requested pages not to be cached by the browser
                processData: false, //To avoid making query String instead of JSON
                timeout: 4000,
                data: {
                    endDate: dateTime
                }
            }).then(
                function successCallback(response) {
                    $scope.submitted = false;
                    $scope.course = null;
                    $scope.editEndDateForm = null;
                    $scope.isEditEndDateSuccess = true;
                    $scope.editEndDateSuccessMessage = 'End date updated successfully.';
                    $scope.endDateString = response.data;

                }, function errorCallback(response) {
                    $scope.editEndDateForm.$valid = false;
                    $scope.editEndDateForm.$error = true;
                    $scope.editEndDateErrorMessage = response.data;
                    let errorData = response.data;
                    if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                        $scope.errorRender = $sce.trustAsHtml(errorData);
                    } else {
                        $scope.editEndDateErrorMessage = errorData;
                    }
                });
        }
    }

}

