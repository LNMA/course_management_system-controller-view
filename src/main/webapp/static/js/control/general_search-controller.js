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
GeneralSearchCtrl.$inject = ['$scope', '$http', '$window', '$location', '$sce', 'GeneralSearchService'];

function GeneralSearchCtrl($scope, $http, $window, $location, $sce, GeneralSearchService) {
    GeneralSearchService.getPageNumber($http, $location, $scope, $sce);

}