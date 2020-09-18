/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('FeedbackHomeController', FeedbackHomeCtrl);
FeedbackHomeCtrl.$inject = ['$scope', '$http', '$location', '$sce', '$window', 'GetFeedbackDataService',
    'GetSessionIdService', 'CreateFeedbackService', 'EditFeedbackService', 'GetCourseInfoService',
    'GetInstructorCourseInfoService'];

function FeedbackHomeCtrl($scope, $http, $location, $sce, $window, GetFeedbackDataService, GetSessionIdService,
                          CreateFeedbackService, EditFeedbackService, GetCourseInfoService,
                          GetInstructorCourseInfoService) {
    let currentFeedbackUrl = $scope.homeCourseFeedbackUrl = $location.absUrl();//https://localhost:8443/course/{courseId}/feedback
    let courseId = currentFeedbackUrl.toString().split("/")[4];
    $scope.courseId = courseId;

    GetCourseInfoService.getCourseInfo($http, $location, $scope, $sce, courseId);
    GetInstructorCourseInfoService.getInstructorCourseInfo($http, $location, $scope, $sce, courseId);
    GetFeedbackDataService.getFeedbackData($http, $location, $scope, $sce);
    GetSessionIdService.getSessionId($http, $scope, $sce);

    //$scope.sessionAccountId;
    $scope.showTextPostForm = function () {
        $scope.textImagePostForm = false;
        $scope.imagePostForm = false;
        $scope.postFormContainer = true;
        $scope.textPostFrom = true;
    }

    $scope.showImagePostForm = function () {
        $scope.textImagePostForm = false;
        $scope.textPostFrom = false;
        $scope.postFormContainer = true;
        $scope.imagePostForm = true;
    }

    $scope.showImageTextPostForm = function () {
        $scope.textPostFrom = false;
        $scope.imagePostForm = false;
        $scope.postFormContainer = true;
        $scope.textImagePostForm = true;
    }

    $scope.hidePostForm = function () {
        $scope.textPostFrom = false;
        $scope.postFormContainer = false;
        $scope.imagePostForm = false;
        $scope.textImagePostForm = false;
    }

    $scope.saveTextPost = function () {
        $scope.submittedTextPost = true;
        if ($scope.createtextFeedbackForm.$valid) {
            CreateFeedbackService.createTextFeedback($http, $location, $scope, $window, $sce);
        }
    }

    $scope.deletePost = function (feedbackId) {
        EditFeedbackService.deleteFeedback($http, $location, $scope, $window, $sce, feedbackId);
    }

    $scope.EditPost = function (feedbackId) {
        $window.location.href = $location.absUrl() + '/' + feedbackId + '/edit-feedback';
    }

    $scope.comment = {text: ''};
    $scope.addComment = function (feedbackId) {
        if ($scope.comment.text !== null && $scope.comment.text !== '') {
            CreateFeedbackService.createComment($http, $location, $scope, $window, $sce, feedbackId);
        }
    }

    $scope.deleteComment = function (commentId){
        EditFeedbackService.deleteComment($http, $location, $scope, $window, $sce, commentId);
    }
}