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
    let currentUrl = $scope.homeCourseMateialUrl = $location.absUrl();
    GetCourseInfoService.getCourseInfo($http, $location, $scope, $sce, currentUrl);
    GetInstructorCourseInfoService.getInstructorCourseInfo($http, $location, $scope, $sce, currentUrl);
}