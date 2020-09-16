/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('UpdateStudentDetailsService', [function () {
    this.updateInterestsService = function updateInterestsService($http, $location, $scope, emailUri) {
        return $http({
            method: 'PATCH',
            port: 8443,
            url: 'https://localhost:8443/student/student_home/' + emailUri + '/interests-update', //FIXME
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

    this.updateHeadlineService = function updateHeadlineService($http, $location, $scope, emailUri) {
        return $http({
            method: 'PATCH',
            port: 8443,
            url: 'https://localhost:8443/student/student_home/' + emailUri + '/headline-update',//FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                headline: $scope.editHeadline
            }

        }).then(
            function successCallback(response) {
                $scope.submitted = false;
                $scope.editHeadline = null;
                $scope.headline = response.data;
                $scope.isEditHeadlineSuccess = true;
                $scope.headlineSuccessMessage = 'headline change successfully';

            }, function errorCallback(response) {
                $scope.editHeadlineForm.$valid = false;
                $scope.editHeadlineForm.$error = true;
                $scope.headlineErrorMessage = response.data;
            });
    }
}]);