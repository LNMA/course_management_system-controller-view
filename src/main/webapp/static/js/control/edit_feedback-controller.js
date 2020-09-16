/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('EditFeedbackController', EditFeedbackCtrl);
EditFeedbackCtrl.$inject = ['$scope', '$http', '$location', '$sce', '$window', 'GetCourseInfoService',
    'GetFeedbackDataService', 'EditFeedbackService'];

function EditFeedbackCtrl($scope, $http, $location, $sce, $window, GetCourseInfoService, GetFeedbackDataService,
                          EditFeedbackService) {
    let currentFeedbackUrl = $scope.homeCourseFeedbackUrl = $location.absUrl();//https://localhost:8443/course/{courseId}/feedback
    let courseId = currentFeedbackUrl.toString().split("/")[4];
    let feedbackId = currentFeedbackUrl.toString().split("/")[6];
    let courseUrl = 'https://localhost:8443/course/' + courseId;

    GetCourseInfoService.getCourseInfo($http, $location, $scope, $sce, courseUrl);
    GetFeedbackDataService.getOneFeedback($http, $location, $scope, $sce, currentFeedbackUrl);

    $scope.textMessageFeedback = {feedback: ''};
    $scope.updateTextPost = function () {
        $scope.submittedTextPost = true;
        if ($scope.textMessageFeedback.feedback !== '' && $scope.textMessageFeedback.feedback !== null) {
            EditFeedbackService.updateTextFeedback($http, $location, $scope, $window, $sce, courseId);
        }
    }


}