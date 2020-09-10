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
ReviewAccountStudentCtrl.$inject = ['$scope', '$http', '$location', '$sce', 'GetReviewAccountService', '$window'];

function ReviewAccountStudentCtrl($scope, $http, $location, $sce, GetReviewAccountService,  $window) {
    let emailUri = $location.absUrl().toString().split("/")[5];
    console.log(emailUri);
    $scope.urlEmail = $location.absUrl().toString().split('/')[5];

    GetReviewAccountService.getReviewAccountInfo($http, $scope, $sce, emailUri);
}

