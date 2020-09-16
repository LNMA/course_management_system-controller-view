/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('StudentHomeCourseController', StudentHomeCourseCtrl);
StudentHomeCourseCtrl.$inject = ['$scope', '$http', '$location', '$sce', '$window', 'GetStudentCourseService'];

function StudentHomeCourseCtrl($scope, $http, $location, $sce, $window, GetStudentCourseService) {
    let emailUri = $location.absUrl().toString().split('/')[5]; //https://localhost:8443/student/student_home/{email}
    GetStudentCourseService.getStudentCourse($http, $location, $scope, $sce, emailUri);
}

