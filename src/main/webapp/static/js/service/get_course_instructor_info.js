/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('GetInstructorCourseInfoService', [function () {
    this.getInstructorCourseInfo = function getInstructorCourseInfo($http, $location, $scope, $sce, url) {
        return $http({
            method: 'GET',
            port: 8443,
            url: url + "/course_instructor_info",
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 5000,
        }).then(
            function successCallback(response) {
                $scope.instructorEmail = response.data.email;
                $scope.instructorGender = response.data.gender;
                $scope.instructorPicture = response.data.accountPicture.profilePictureBase64;
                $scope.instructorProfileVisibility = response.data.profileVisibility;
                $scope.instructoruserRole = response.data.userRole;
                $scope.instructorNickname = response.data.nickname;
                $scope.instructorForename = response.data.forename;
                $scope.instructorSurname = response.data.surname;
                $scope.instructorHeadline = response.data.headline;
                $scope.instructorPortfolio = response.data.portfolio;
                $scope.instructorSpecialty = response.data.specialty;
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