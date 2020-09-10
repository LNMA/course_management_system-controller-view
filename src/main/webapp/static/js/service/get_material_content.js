/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('GetMaterialContentService', [function () {
    this.getMaterialContent = function getMaterialContent($http, $scope, $sce, courseId, materialType, materialId) {
        return $http({
            method: 'GET',
            port: 8443,
            url: 'https://localhost:8443/course/' + courseId + '/material/' + materialType + '/' + materialId + '/get_content', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
        }).then(
            function successCallback(response) {
                let materialType = response.data.materialType;
                if (materialType === 'FILE') {
                    let fileType = response.data.fileType;
                    if (fileType === 'IMAGE') {
                        $scope.viewContent = $sce.trustAsHtml(`<img class="col col-md-12" src="data:image/*;base64,` + response.data.base64 + `"/>`)
                    }
                    if (fileType === 'PDF') {
                        $scope.viewContent = $sce.trustAsHtml(`
                        <div class="embed-responsive embed-responsive-16by9">
                            <iframe class="embed-responsive-item" allowfullscreen src="data:application/pdf;base64,` + response.data.base64 + `"></iframe>
                        </div>`)
                    }
                }
                if (materialType === 'TEXT') {
                    $scope.viewContent = $sce.trustAsHtml(`
                        <div class="embed-responsive embed-responsive-16by9">
                            <iframe class="embed-responsive-item" src="` + response.data.text + `"></iframe>
                        </div>`)
                }

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