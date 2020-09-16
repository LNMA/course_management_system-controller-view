/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('InstructorHomeController', InstructorHomeCtrl);
InstructorHomeCtrl.$inject = ['$scope', '$http', '$location', '$sce', 'GetInstructorHomeInfoService',
    'UpdateInstructorDetailsService'];

function InstructorHomeCtrl($scope, $http, $location, $sce, GetInstructorHomeInfoService,
                            UpdateInstructorDetailsService) {
    let emailUri = $location.absUrl().toString().split('/')[5]; //https://localhost:8443/student/student_home/{email}
    GetInstructorHomeInfoService.getInstructorInfo($http, $location, $scope, $sce, emailUri);
    $scope.updatePictureUrl = 'https://localhost:8443/user/update/' + emailUri + '/profile_picture-update'; //FIXME
    $scope.joinToCourseUrl = 'https://localhost:8443/instructor/' + emailUri + '/to_my_course';//FIXME
    $scope.sessionId = $location.absUrl().toString().split('/')[5];

    $scope.updateHeadline = function () {
        $scope.submitted = true;
        if ($scope.editHeadlineForm.$valid) {
            UpdateInstructorDetailsService.updateHeadlineService($http, $location, $scope, emailUri);
        }
    };

    $scope.updateSpecialty = function () {
        $scope.submitted = true;
        if ($scope.editSpecialtyForm.$valid) {
            UpdateInstructorDetailsService.updateSpecialtyService($http, $location, $scope, emailUri) ;
        }
    };

    $scope.updateNickname = function () {
        $scope.submitted = true;
        if ($scope.editNicknameForm.$valid) {
            UpdateInstructorDetailsService.updateNicknameService($http, $location, $scope, emailUri);
        }
    };

    $scope.updatePortfolio = function () {
        $scope.submitted = true;
        if ($scope.editPortfolioForm.$valid) {
            UpdateInstructorDetailsService.updatePortfolioService($http, $location, $scope, emailUri);
        }
    };

    $scope.updateProfileVisibility = function () {
        $scope.submitted = true;
        if ($scope.editProfileVisibilityForm.$valid) {
            UpdateInstructorDetailsService.updateProfileVisibilityService($http, $location, $scope, emailUri);
        }
    };

}

