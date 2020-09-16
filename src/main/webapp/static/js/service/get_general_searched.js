/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('GeneralSearchService', [function () {
    this.getPageNumber = function getPageNumber($http, $location, $scope, $sce) {
        $http({
            method: 'GET',
            port: 8443,
            url: $location.absUrl() + '/get_record_number', //FIXME: https://localhost:8443/search/{KEY}
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 5000,
        }).then(
            function successCallback(response) {
                let rowCount = Number.parseInt(response.data);
                let pageSize = 3;
                let pageNumberResult = rowCount / pageSize;
                if (rowCount > 0 && rowCount < 4){
                    pageNumberResult = 1;
                }
                $scope.pageNumber = parseInt(pageNumberResult);
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

    this.showResult = function showResult($http, $location, $scope, $sce, pageNumber) {
        $http({
            method: 'GET',
            port: 8443,
            url: $location.absUrl() + '/' + pageNumber + '/get_result', //FIXME: https://localhost:8443/search/{KEY}
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 5000,
        }).then(
            function successCallback(response) {
                console.log(response.data);
                $scope.searchResult = response.data;
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