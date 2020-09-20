/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('RegisterInstructorSubmitService', function () {
    this.instructorSubmitForm = function instructorSubmitForm($http, $scope, $sce) {
        return $http({
            method: 'POST',
            port: 8443,
            url: 'https://localhost:8443/admin/register-instructor/submit_instructor_sign_up', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 20000,
            data: {
                email: $scope.instructor.email,
                forename: $scope.instructor.forename,
                surname: $scope.instructor.surname,
                gender: $scope.instructor.gender,
                birthday: $scope.instructor.birthday,
                phone: $scope.instructor.phone,
                country: $scope.instructor.country,
                state: $scope.instructor.state,
                address: $scope.instructor.address,
                nickname: $scope.instructor.nickname,
                profileVisibility: $scope.instructor.profileVisibility,
                admin: {
                    email: $scope.instructor.email,
                    password: $scope.instructor.password
                }
            }
        }).then(function successCallback(response) {
            $scope.loading = false;
            $scope.isSuccess = true;
            $scope.submitSuccessMessage = response.data;
            $scope.instructor = null;
            $scope.submitted = false;
        }, function errorCallback(response) {
            $scope.loading = false;
            let errorData = response.data;
            if (errorData.toString().substr(8, 15) === '<!DOCTYPE html>') {
                $scope.errorRender = $sce.trustAsHtml(errorData);
            } else {
                if (response.status === 406) {
                    $scope.instructorRegisterForm.email.$invalid = true;
                    $scope.isError = true;
                    $scope.submitErrorMessage = errorData;
                } else {
                    $scope.isError = true;
                    $scope.instructorRegisterForm.$invalid = true;
                    $scope.submitErrorMessage = errorData;
                }
            }
        });
    };
});
