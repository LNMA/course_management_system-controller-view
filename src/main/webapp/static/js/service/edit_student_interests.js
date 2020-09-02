/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('UpdateStudentInterestsService', [function () {
    this.updateInterestsService = function updateInterestsService($http, $location, $scope) {
        return $http({
            method: 'POST',
            port: 8443,
            url: $location.absUrl() + "/interests-update",
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                interests: $scope.editInterests
            }
        }).then(
            function successCallback(response) {
                $scope.submitted = false;
                $scope.editInterests = null;
                $scope.interests = response.data;
                $scope.isEditInterestsSuccess = true;
                $scope.interestsSuccessMessage = 'interests change successfully';

            }, function errorCallback(response) {
                $scope.submitted = false;
                $scope.editInterestsForm.$valid = false;
                $scope.editInterestsForm.$error = true;
                $scope.interestsErrorMessage = response.data;
            });
    }
}]);