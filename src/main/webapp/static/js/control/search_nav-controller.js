/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('SearchNavController', SearchNavCtrl);
SearchNavCtrl.$inject = ['$scope', '$http', '$window'];

function SearchNavCtrl($scope, $http, $window) {
    $scope.searchNow = function () {
        let key = $scope.keySearch;
        if (key !== null && key !== '' && key !== undefined) {
            $window.location.href = 'https://localhost:8443/search/' + key;//FIXME
        }
    }
}