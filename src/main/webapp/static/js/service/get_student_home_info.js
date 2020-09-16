/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.factory('GetStudentHomeInfoService', function () {
    return {
        getStudentInfo: function getStudentInfo($http, $location, $scope, $sce, emailUri) {
            return $http({
                method: 'GET',
                port: 8443,
                url: 'https://localhost:8443/student/student_home/' + emailUri + "/myInfo", //FIXME
                headers: {'content-type': 'application/json'},
                contentType: "application/json; charset=utf-8",
                async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
                cache: true,    //This will force requested pages not to be cached by the browser
                processData: false, //To avoid making query String instead of JSON
                timeout: 4000
            }).then(
                function successCallback(response) {
                    $scope.email = response.data.student.email;
                    $scope.password = 'response.data.student.admin.password';
                    $scope.joinDate = response.data.timeJoinDate;
                    $scope.forename = response.data.student.forename;
                    $scope.surname = response.data.student.surname;
                    $scope.gender = response.data.student.gender;
                    $scope.phone = response.data.student.phone;
                    $scope.birthday = response.data.timeBirthday;
                    $scope.country = response.data.student.country;
                    $scope.state = response.data.student.state;
                    $scope.address = response.data.student.address;
                    $scope.headline = response.data.student.headline;
                    $scope.interests = response.data.student.interests;
                    $scope.userRole = response.data.student.userRole;
                    $scope.age = response.data.student.age;
                    $scope.lastSignIn = response.data.lastSignInDate;
                    $scope.picture = response.data.student.accountPicture.profilePictureBase64;
                    $scope.pictureUploadDate = response.data.student.accountPicture.uploadPicDate;
                    $scope.submitted = false;

                }, function errorCallback(response) {
                    $scope.isPageError = true;
                    let errorData = response.data;
                    if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                        $scope.errorRender = $sce.trustAsHtml(errorData);
                    } else {
                        $scope.pageErrorMessage = errorData;
                    }
                    $scope.submitted = false;

                });
        }
    };
});
