/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('GetReviewAccountService', [function () {
    this.getReviewAccountInfo = function getReviewAccountInfo($http, $scope, $sce, sessionEmail, emailUri) {
        return $http({
            method: 'GET',
            port: 8443,
            url: 'https://localhost:8443/review/account/' + sessionEmail + '/' + emailUri + '/user_info', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 5000,
        }).then(
            function successCallback(response) {
                $scope.infoList = response.data;
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