/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('EditFeedbackService', [function () {
    this.deleteFeedback = function deleteFeedback($http, $location, $scope, $window, $sce, feedbackId) {
        return $http({
            method: 'DELETE',
            port: 8443,
            url: $location.absUrl() + "/delete_post", //FIXME: https://localhost:8443/course/{courseId}/feedback
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                feedbackID: feedbackId
            }
        }).then(
            function successCallback(response) {
                $window.location.href = 'https://localhost:8443'+response.data; //FIXME: https://localhost:8443/course/{courseId}/feedback

            }, function errorCallback(response) {
                $scope.isPageError = true;
                let errorData = response.data;
                if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                    $scope.errorRender = $sce.trustAsHtml(errorData);
                } else {
                    $scope.pageErrorMessage = errorData;
                }
            });
    };

    this.updateTextFeedback = function updateTextFeedback($http, $location, $scope, $window, $sce, courseId) {
        return $http({
            method: 'PUT',
            port: 8443,
            url: $location.absUrl() + "/update_text_post",//FIXME: https://localhost:8443/course/{courseId}/feedback/edit-feedback/{feedbackId}/update_text_post
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                postMessage: $scope.textMessageFeedback.feedback,
            }
        }).then(
            function successCallback(response) {
                $window.location.href = 'https://localhost:8443'+response.data;//FIXME: https://localhost:8443/course/{courseId}/feedback

            }, function errorCallback(response) {
                $scope.isPageError = true;
                let errorData = response.data;
                if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                    $scope.errorRender = $sce.trustAsHtml(errorData);
                } else {
                    $scope.pageErrorMessage = errorData;
                }
            });
    };

    this.deleteComment = function deleteComment($http, $location, $scope, $window, $sce, commentId) {
        return $http({
            method: 'DELETE',
            port: 8443,
            url: $location.absUrl() + "/comment/remove_comment", //FIXME: https://localhost:8443/course/{courseId}/feedback
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                commentID: commentId
            }
        }).then(
            function successCallback(response) {
                $window.location.href = 'https://localhost:8443'+response.data;//FIXME: https://localhost:8443/course/{courseId}/feedback

            }, function errorCallback(response) {
                $scope.isPageError = true;
                let errorData = response.data;
                if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                    $scope.errorRender = $sce.trustAsHtml(errorData);
                } else {
                    $scope.pageErrorMessage = errorData;
                }
            });
    };
}]);