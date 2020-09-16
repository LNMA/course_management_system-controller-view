/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('ReviewCourseStudentController', ReviewCourseStudentCtrl);
ReviewCourseStudentCtrl.$inject = ['$scope', '$http', '$location', '$sce', 'GetReviewCourseInfoService',
    'CreateCourseMemberService'];

function ReviewCourseStudentCtrl($scope, $http, $location, $sce, GetReviewCourseInfoService,
                                 CreateCourseMemberService) {
    let emailLoginUser = $location.absUrl().toString().split('/')[5];
    $scope.sessionId = emailLoginUser;
    $scope.updatePictureUrl = 'https://localhost:8443/user/update/' + emailLoginUser + '/profile_picture-update'; //FIXME

    let courseId = $location.absUrl().toString().split("/")[6];

    GetReviewCourseInfoService.getReviewCourseInfo($http, $scope, $sce, emailLoginUser, courseId, getEmail);

    function getEmail(emailInstructor) {
        GetReviewCourseInfoService.getInstructorTechCourses($http, $scope, $sce, emailLoginUser, courseId, emailInstructor);
    }

    GetReviewCourseInfoService.getCourseMember($http, $scope, $sce, emailLoginUser, courseId);
    GetReviewCourseInfoService.isStudentJoinToThisCourse($http, $scope, $sce, emailLoginUser, courseId);
    GetReviewCourseInfoService.getCurrentTime($http, $scope, $sce, emailLoginUser, courseId);

    $scope.addToCourse = function (){
        CreateCourseMemberService.beAMember($http, $scope, $sce, emailLoginUser, courseId);
    }
}
