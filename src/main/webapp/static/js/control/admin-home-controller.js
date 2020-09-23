/*jshint esversion: 6 */
/*jshint sub:true*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('AdminHomeController', AdminHomeCtrl);
AdminHomeCtrl.$inject = ['$scope', '$http', '$location', '$sce'];

function AdminHomeCtrl($scope, $http, $location, $sce) {
    let emailUri = $location.absUrl().toString().split('/')[5]; //https://localhost:8443/admin/admin_home/{email}
    $scope.email = emailUri;
    $scope.courseIdToAttendanceByCourseId = '';
    $scope.isNumber = function(value) {
        return isNaN(value);
    }

    $scope.courseIdToAttendanceByCourseIdDate = '';
    $scope.fromDateToAttendanceByCourseIdDate = '';
    $scope.toDateToAttendanceByCourseIdDate = '';



}