/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('CourseSearchController', CourseSearchCtrl);
CourseSearchCtrl.$inject = ['$scope', '$http', '$location', '$sce', 'CourseSearchService'];

function CourseSearchCtrl($scope, $http, $location, $sce, CourseSearchService) {
    CourseSearchService.getPageNumber($http, $location, $scope, $sce);
    $scope.showPage = function (pageNumber){
        CourseSearchService.showCourses($http, $location, $scope, $sce, pageNumber);
    }
}