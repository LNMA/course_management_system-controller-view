/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('FeedbackNotificationController', FeedbackNotificationCtrl);
FeedbackNotificationCtrl.$inject = ['$scope', '$http', '$sce', 'GetNotificationDetailsService', 'GetSessionIdService'];

function FeedbackNotificationCtrl($scope, $http, $sce, GetNotificationDetailsService, GetSessionIdService) {
    GetNotificationDetailsService.getFeedbackNotificationDetails($http, $scope, $sce);
    GetSessionIdService.getSessionId($http, $scope, $sce);
}

