/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('MaterialHomeController', MaterialHomeCtrl);
MaterialHomeCtrl.$inject = ['$scope', '$http', '$location', '$sce', 'GetMaterialContentService',
    'GetMaterialShallowInfoService', 'GetCourseInfoService', 'GetInstructorCourseInfoService'];

function MaterialHomeCtrl($scope, $http, $location, $sce, GetMaterialContentService,
                          GetMaterialShallowInfoService, GetCourseInfoService, GetInstructorCourseInfoService) {
    let courseId = $location.absUrl().toString().split('/')[4];

    GetMaterialShallowInfoService.getMaterialShallowInfo($http, $location, $scope, $sce);

    $scope.showContent = function (courseId, materialType, materialId) {
        GetMaterialContentService.getMaterialContent($http, $scope, $sce, courseId, materialType, materialId)
    }

    GetCourseInfoService.getCourseInfo($http, $location, $scope, $sce, courseId);
    GetInstructorCourseInfoService.getInstructorCourseInfo($http, $location, $scope, $sce, courseId);
    $scope.courseId = courseId;

}