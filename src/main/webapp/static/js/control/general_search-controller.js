/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('GeneralSearchController', GeneralSearchCtrl);
GeneralSearchCtrl.$inject = ['$scope', '$http', '$window', '$location', '$sce', 'GeneralSearchService',
    'GetSessionIdService'];

function GeneralSearchCtrl($scope, $http, $window, $location, $sce, GeneralSearchService, GetSessionIdService) {
    GeneralSearchService.getPageNumber($http, $location, $scope, $sce);

    $scope.showPage = function (pageNumber){
        GeneralSearchService.showResult($http, $location, $scope, $sce, pageNumber);
    }

    GetSessionIdService.getSessionId($http, $scope, $sce);
}