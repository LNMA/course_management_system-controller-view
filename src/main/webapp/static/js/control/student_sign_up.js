/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
app.controller('StudentRegisterController', RegisterCtrl);
RegisterCtrl.$inject = ['$scope', '$http','RegisterStudentSubmitService', 'CountryStateService'];
function RegisterCtrl($scope, $http, RegisterStudentSubmitService, CountryStateService) {
    $scope.submitted = false;
    $scope.countryList = CountryStateService.allCountry();
    $scope.stateList = CountryStateService.allState();

    $scope.saveStudent = function () {
        $scope.submitted = true;
        if ($scope.studentForm.$valid) {
            if ($scope.student.password === $scope.student.rePassword) {
                RegisterStudentSubmitService.studentSubmitForm($scope.student)
                    .then(function success(response) {
                        $scope.isSuccess = true;
                        $scope.student = null;
                        $scope.submitted = false;
                    }, function error(response) {
                        if (response.status === 409) {
                            $scope.submitErrorMessage = response.data.message;
                        } else {
                            $scope.submitErrorMessage = 'Error adding student!';
                        }
                        $scope.message = '';
                    });
            }else {
                $scope.studentForm.password.$invalid = true;
                $scope.studentForm.rePassword.$invalid = true;
                $scope.isError = true;
                $scope.submitErrorMessage = 'Password did not match!';
            }
        }


    };
}
