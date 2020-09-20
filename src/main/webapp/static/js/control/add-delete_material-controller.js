/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
}).controller('AddEditMaterialController', AddEditMaterialCtrl);
AddEditMaterialCtrl.$inject = ['$scope', '$window', '$http', '$location', '$sce'];

function AddEditMaterialCtrl($scope, $window, $http, $location, $sce) {
    let courseId = $location.absUrl().toString().split('/')[4];
    $scope.addFileMaterialUrl = 'https://localhost:8443/course/' + courseId + '/material/add_file_material';<!--FIXME-->

    $scope.deleteMaterial = function (materialId) {
        $http({
            method: 'DELETE',
            port: 8443,
            url: 'https://localhost:8443/course/' + courseId + '/material/' + materialId + '/remove_material', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 2000,
        }).then(
            function successCallback(response) {
                $window.location.href = 'https://localhost:8443' + response.data;

            }, function errorCallback(response) {
                let errorData = response.data;
                if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                    $scope.errorRender = $sce.trustAsHtml(errorData);
                } else {
                    $scope.pageErrorMessage = errorData;
                }
            });
    }

    $scope.AddTextMaterial = function () {
        $scope.submittedTextMaterial = true;
        if ($scope.addTextMaterialFrom.$valid) {
            $http({
                method: 'POST',
                port: 8443,
                url: 'https://localhost:8443/course/' + courseId + '/material/add_text_material', //FIXME
                headers: {'content-type': 'application/json'},
                contentType: "application/json; charset=utf-8",
                async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
                cache: false,    //This will force requested pages not to be cached by the browser
                processData: false, //To avoid making query String instead of JSON
                timeout: 4000,
                data: {
                    text: $scope.textMaterial.content,
                    materialName: $scope.textMaterial.name
                }
            }).then(
                function successCallback(response) {
                    $scope.submittedTextMaterial = false;
                    $scope.textMaterial = null;
                    $scope.addTextMaterialFrom = null;
                    $scope.isAddTextMaterialSuccess = true;
                    $scope.addTextMaterialSuccessMessage = response.data;

                }, function errorCallback(response) {
                    $scope.addTextMaterialFrom.$valid = false;
                    $scope.addTextMaterialFrom.$error = true;
                    let errorData = response.data;
                    if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                        $scope.errorRender = $sce.trustAsHtml(errorData);
                    } else {
                        $scope.addTextMaterialErrorMessage = errorData;
                    }
                });
        }
    }
}