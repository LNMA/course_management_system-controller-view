/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('CourseSearchController', CourseSearchCtrl);
CourseSearchCtrl.$inject = ['$scope', '$location', '$http', '$sce', 'GetCourseInfoService',
    'GetInstructorCourseInfoService'];

function CourseSearchCtrl($scope, $location, $http, $sce, GetCourseInfoService,
                          GetInstructorCourseInfoService) {
    let courseId = $location.absUrl().toString().split('/')[4];
    $scope.courseId = courseId;
    $scope.homeCourseMateialUrl = courseId;
    GetCourseInfoService.getCourseInfo($http, $location, $scope, $sce, courseId);
    GetInstructorCourseInfoService.getInstructorCourseInfo($http, $location, $scope, $sce, courseId);

    $http({
        method: 'GET',
        port: 8443,
        url: $location.absUrl() + '/get_search_result', //FIXME
        headers: {'content-type': 'application/json'},
        contentType: "application/json; charset=utf-8",
        async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
        cache: true,    //This will force requested pages not to be cached by the browser
        processData: false, //To avoid making query String instead of JSON
        timeout: 4000
    }).then(
        function successCallback(response) {
            console.log(response.data);
            $scope.searchResult = response.data;
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

