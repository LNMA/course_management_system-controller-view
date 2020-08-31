/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.factory('GetStudentCourseService', function () {
    return {
        getStudentCourse: function getStudentCourse($http, $location, $scope, $sce) {
            return $http({
                method: 'GET',
                port: 8443,
                url: $location.absUrl() + "/my_course",
                headers: {'content-type': 'application/json'},
                contentType: "application/json; charset=utf-8",
                async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
                cache: true,    //This will force requested pages not to be cached by the browser
                processData: false, //To avoid making query String instead of JSON
                timeout: 4000
            }).then(
                function successCallback(response) {
                    $scope.courseData = response.data;
                    $scope.submitted = false;

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
        }
    };
});
