/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('ReviewAccountStudentController', ReviewAccountStudentCtrl);
ReviewAccountStudentCtrl.$inject = ['$scope', '$http', '$location', '$sce', 'GetReviewAccountService',
    'GetUserStatusService', 'GetJoinCourseReviewAccountService'];

function ReviewAccountStudentCtrl($scope, $http, $location, $sce, GetReviewAccountService, GetUserStatusService,
                                  GetJoinCourseReviewAccountService) {
    let emailLoginUser = $location.absUrl().toString().split('/')[5];

    let emailReviewUser = $location.absUrl().toString().split("/")[6];

    GetReviewAccountService.getReviewAccountInfo($http, $scope, $sce, emailLoginUser, emailReviewUser);
    GetUserStatusService.isUserOnline($http, $location, $scope, $sce, emailLoginUser, emailReviewUser);
    GetUserStatusService.isUserAtCourse($http, $location, $scope, $sce, emailLoginUser, emailReviewUser);
    GetJoinCourseReviewAccountService.getStudentJoinCourse($http, $scope, $sce, emailLoginUser, emailReviewUser);
    GetJoinCourseReviewAccountService.getInstructorTechCourses($http, $scope, $sce, emailLoginUser, emailReviewUser);

}

