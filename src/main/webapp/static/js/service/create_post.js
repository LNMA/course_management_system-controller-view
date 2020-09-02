/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('CreateFeedbackService', [function () {
    this.createTextFeedback = function createTextFeedback($http, $location, $scope, $window, $sce) {
        return $http({
            method: 'POST',
            port: 8443,
            url: $location.absUrl() + "/add_text_post",
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                postMessage: $scope.textMessageFeedback,
            }
        }).then(
            function successCallback(response) {
                $scope.submittedTextPost = false;
                $scope.createtextFeedbackForm = null;
                $window.location.href = $location.absUrl();

            }, function errorCallback(response) {
                $scope.submittedTextPost = false;
                $scope.isPageError = true;
                let errorData = response.data;
                if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                    $scope.errorRender = $sce.trustAsHtml(errorData);
                } else {
                    $scope.pageErrorMessage = errorData;
                }
            });
    }

    this.createComment = function createComment($http, $location, $scope, $window, $sce, feedbackId) {
        return $http({
            method: 'POST',
            port: 8443,
            url: $location.absUrl() + "/comment/add_comment",
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                courseFeedback: {
                    feedbackID: feedbackId,
                },
                commentMessage: $scope.comment.text,
            }
        }).then(
            function successCallback(response) {
                $scope.submittedTextPost = false;
                $scope.comment = null;
                $window.location.href = $location.absUrl();

            }, function errorCallback(response) {
                $scope.submittedTextPost = false;
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