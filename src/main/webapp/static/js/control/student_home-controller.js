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
    'UpdateStudentDetailsService'];

function StudentHomeCtrl($scope, $http, $location, $sce, $window, GetStudentHomeInfoService,
                         UpdateStudentDetailsService) {
    let emailUri = $location.absUrl().toString().split('/')[5]; //https://localhost:8443/student/student_home/{email}
    GetStudentHomeInfoService.getStudentInfo($http, $location, $scope, $sce, emailUri);
    $scope.updatePictureUrl = 'https://localhost:8443/user/update/' + emailUri + '/profile_picture-update'; //FIXME
    $scope.joinToCourseUrl = 'https://localhost:8443/student/student_home/' + emailUri + '/to_my_course/';//FIXME
    $scope.submitted = false;
    $scope.updateInterests = function () {
        $scope.submitted = true;
        if ($scope.editInterestsForm.$valid) {
            UpdateStudentDetailsService.updateInterestsService($http, $location, $scope, emailUri);
        }
    };

    $scope.updateHeadline = function () {
        $scope.submitted = true;
        if ($scope.editHeadlineForm.$valid) {
            UpdateStudentDetailsService.updateHeadlineService($http, $location, $scope, emailUri);
        }
    };

    $scope.sessionId = $location.absUrl().toString().split('/')[5];
}

