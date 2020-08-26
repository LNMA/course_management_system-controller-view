/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('StudentHomeController', StudentHomeCtrl);
StudentHomeCtrl.$inject = ['$scope', '$http', '$location', 'GetStudentHomeInfoService',
    'UpdateStudentInterestsService'];

function StudentHomeCtrl($scope, $http, $location, GetStudentHomeInfoService, UpdateStudentInterestsService) {
    GetStudentHomeInfoService.getStudentInfo($http, $location, $scope);
    $scope.uploadImageUrl = $location.absUrl()+'/profile_picture-update';
    $scope.submitted = false;
    $scope.updateInterests = function () {
        $scope.submitted = true;
        if ($scope.editInterestsForm.$valid) {
            UpdateStudentInterestsService.updateInterestsService($http, $location, $scope);
        }
    };
}

