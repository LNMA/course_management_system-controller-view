/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('CourseHomeController', CourseHomeCtrl);
CourseHomeCtrl.$inject = ['$scope', '$http', '$location', '$sce', 'GetCourseInfoService', 'GetInstructorCourseInfoService'];

function CourseHomeCtrl($scope, $http, $location, $sce, GetCourseInfoService, GetInstructorCourseInfoService) {
    let courseId = $location.absUrl().toString().split('/')[4];
    $scope.courseId = courseId;
    $scope.homeCourseMateialUrl = courseId;
    GetCourseInfoService.getCourseInfo($http, $location, $scope, $sce, courseId);
    GetInstructorCourseInfoService.getInstructorCourseInfo($http, $location, $scope, $sce, courseId);
}