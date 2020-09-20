/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.factory('GetInstructorHomeInfoService', function () {
    return {
        getInstructorInfo: function getInstructorInfo($http, $location, $scope, $sce, emailUri) {
            return $http({
                method: 'GET',
                port: 8443,
                url: 'https://localhost:8443/instructor/instructor_home/' + emailUri + "/my_info", //FIXME
                headers: {'content-type': 'application/json'},
                contentType: "application/json; charset=utf-8",
                async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
                cache: true,    //This will force requested pages not to be cached by the browser
                processData: false, //To avoid making query String instead of JSON
                timeout: 4000
            }).then(
                function successCallback(response) {
                    console.log(response.data);
                    $scope.email = response.data.instructor.email;
                    $scope.password = 'response.data.instructor.admin.password';
                    $scope.joinDate = response.data.timeJoinDate;
                    $scope.forename = response.data.instructor.forename;
                    $scope.surname = response.data.instructor.surname;
                    $scope.gender = response.data.instructor.gender;
                    $scope.phone = response.data.instructor.phone;
                    $scope.birthday = response.data.timeBirthday;
                    $scope.country = response.data.instructor.country;
                    $scope.state = response.data.instructor.state;
                    $scope.address = response.data.instructor.address;
                    $scope.headline = response.data.instructor.headline;
                    $scope.specialty = response.data.instructor.specialty;
                    $scope.nickname = response.data.instructor.nickname;
                    $scope.portfolio = response.data.instructor.portfolio;
                    $scope.profileVisibility = response.data.instructor.profileVisibility;
                    $scope.age = response.data.instructor.age;
                    $scope.userRole = response.data.instructor.userRole;
                    $scope.lastSignIn = response.data.lastSignInDate;
                    $scope.picture = response.data.instructor.accountPicture.profilePictureBase64;
                    $scope.pictureUploadDate = response.data.instructor.accountPicture.uploadPicDate;
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
