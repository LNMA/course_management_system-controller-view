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
MaterialHomeCtrl.$inject = ['$scope', '$http', '$location', '$sce', 'GetCourseInfoService',
    'GetMaterialContentService', "GetMaterialShallowInfoService"];

function MaterialHomeCtrl($scope, $http, $location, $sce, GetCourseInfoService, GetMaterialContentService,
                          GetMaterialShallowInfoService) {
    let currentMaterialUrl = $scope.homeCourseMaterialUrl = $location.absUrl();
    let courseId = currentMaterialUrl.toString().split("/")[4];
    let courseUrl = 'https://localhost:8443/course/'+courseId;

    GetCourseInfoService.getCourseInfo($http, $location, $scope, $sce, courseUrl);
    GetMaterialShallowInfoService.getMaterialShallowInfo($http, $location, $scope, $sce);

    $scope.showContent = function (courseId, materialType, materialId) {
        GetMaterialContentService.getMaterialContent($http, $scope, $sce, courseId, materialType, materialId)
    }
}