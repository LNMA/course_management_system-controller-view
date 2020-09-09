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
StudentHomeCtrl.$inject = ['$scope', '$http', '$location', '$sce', '$window', 'GetStudentHomeInfoService',
    'UpdateStudentInterestsService', 'GetStudentCourseService'];

function StudentHomeCtrl($scope, $http, $location, $sce, $window, GetStudentHomeInfoService,
                         UpdateStudentInterestsService, GetStudentCourseService) {
    GetStudentHomeInfoService.getStudentInfo($http, $location, $scope, $sce);
    GetStudentCourseService.getStudentCourse($http, $location, $scope, $sce);
    $scope.updatePictureUrl = $location.absUrl() + '/profile_picture-update';
    $scope.joinToCourseUrl = $location.absUrl() + '/to_my_course/';
    $scope.submitted = false;
    $scope.updateInterests = function () {
        $scope.submitted = true;
        if ($scope.editInterestsForm.$valid) {
            UpdateStudentInterestsService.updateInterestsService($http, $location, $scope);
        }
    };

    $scope.urlEmail = $location.absUrl().toString().split('/')[5];
}

