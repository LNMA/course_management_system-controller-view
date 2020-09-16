/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('GetReviewCourseInfoService', [function () {
    this.getReviewCourseInfo = function getReviewCourseInfo($http, $scope, $sce, sessionEmail, courseId, getEmail) {
        return $http({
            method: 'GET',
            port: 8443,
            url: 'https://localhost:8443/review/course/' + sessionEmail + '/' + courseId + '/course_info', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            crossDomain: true,
            timeout: 5000,
        }).then(
            function successCallback(response) {
                $scope.courseInfo = response.data;
                getEmail(response.data.instructor.email);
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
    }

    this.getCourseMember = function getCourseMember($http, $scope, $sce, sessionEmail, courseId) {
        return $http({
            method: 'GET',
            port: 8443,
            url: 'https://localhost:8443/review/course/' + sessionEmail + '/' + courseId + '/get_course_member', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            crossDomain: true,
            timeout: 5000,
        }).then(
            function successCallback(response) {
                $scope.courseMemberList = response.data;
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
    }

    this.isStudentJoinToThisCourse = function isStudentJoinToThisCourse($http, $scope, $sce, sessionEmail, courseId) {
        return $http({
            method: 'GET',
            port: 8443,
            url: 'https://localhost:8443/review/course/' + sessionEmail + '/' + courseId + '/is_student_join_to_this_course', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            crossDomain: true,
            timeout: 5000,
        }).then(
            function successCallback(response) {
                $scope.isStudentJoinToThisCourse = response.data;
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
    }

    this.getInstructorTechCourses = function getInstructorTechCourses($http, $scope, $sce, sessionEmail, courseId, instructorEmail) {
        return $http({
            method: 'GET',
            port: 8443,
            url: 'https://localhost:8443/review/course/' + sessionEmail + '/' + courseId + '/' + instructorEmail + '/get_instructor_info', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            crossDomain: true,
            timeout: 5000,
        }).then(
            function successCallback(response) {
                $scope.instructorInfo = response.data;
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
    }

    this.getCurrentTime = function getCurrentTime($http, $scope, $sce, sessionEmail, courseId) {
        return $http({
            method: 'GET',
            port: 8443,
            url: 'https://localhost:8443/review/course/' + sessionEmail + '/' + courseId + '/get_time', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            crossDomain: true,
            timeout: 5000,
        }).then(
            function successCallback(response) {
                $scope.currentTime = response.data;
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
    }
}]);