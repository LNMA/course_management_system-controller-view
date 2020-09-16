/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('GetMaterialShallowInfoService', [function () {
    this.getMaterialShallowInfo = function getMaterialShallowInfo($http, $location, $scope, $sce) {
        return $http({
            method: 'GET',
            port: 8443,
            url: $location.absUrl() + "/course_shallow_material", //FIXME: https://localhost:8443/course/{courseId}/material
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 20000,
        }).then(
            function successCallback(response) {
                $scope.materialsList = response.data;

            }, function errorCallback(response) {
                $scope.submitted = false;
                $scope.isPageError = true;
                let errorData = response.data;
                if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                    $scope.errorRender = $sce.trustAsHtml(errorData);
                } else {
                    $scope.pageErrorMessage = errorData;
                }
            });
    }
}]);