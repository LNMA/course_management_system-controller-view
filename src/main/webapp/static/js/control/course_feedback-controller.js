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
FeedbackHomeCtrl.$inject = ['$scope', '$http', '$location', '$sce', '$window', 'GetCourseInfoService',
    'GetFeedbackDataService', 'GetSessionIdService', 'CreateFeedbackService', 'EditFeedbackService'];

function FeedbackHomeCtrl($scope, $http, $location, $sce, $window, GetCourseInfoService, GetFeedbackDataService,
                          GetSessionIdService, CreateFeedbackService, EditFeedbackService) {
    let currentFeedbackUrl = $scope.homeCourseFeedbackUrl = $location.absUrl();
    let courseId = currentFeedbackUrl.toString().split("/")[4];
    let courseUrl = 'https://localhost:8443/course/' + courseId;

    GetCourseInfoService.getCourseInfo($http, $location, $scope, $sce, courseUrl);
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
}