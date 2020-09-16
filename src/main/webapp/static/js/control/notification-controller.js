/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('NotificationController', NotificationCtrl);
NotificationCtrl.$inject = ['$scope', '$http', '$sce', 'GetNotificationService'];

function NotificationCtrl($scope, $http, $sce, GetNotificationService) {
    GetNotificationService.getAllNotificationCount($http, $scope, $sce);
    GetNotificationService.getFeedbackNotificationCount($http, $scope, $sce);
    GetNotificationService.getMaterialNotificationCount($http, $scope, $sce);
}

