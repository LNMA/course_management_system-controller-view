/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('UpdateInstructorDetailsService', [function () {
    this.updateProfileVisibilityService = function updateProfileVisibilityService($http, $location, $scope, emailUri) {
        return $http({
            method: 'PATCH',
            port: 8443,
            url: 'https://localhost:8443/instructor/instructor_home/' + emailUri + '/profile_visibility-update', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                profileVisibility: $scope.editProfileVisibility
            }
        }).then(
            function successCallback(response) {
                $scope.submitted = false;
                $scope.editProfileVisibility = null;
                $scope.profileVisibility = response.data;
                $scope.isEditProfileVisibilitySuccess = true;
                $scope.profileVisibilitySuccessMessage = 'profile Visibility change successfully';

            }, function errorCallback(response) {
                $scope.submitted = false;
                $scope.editProfileVisibilityForm.$valid = false;
                $scope.editProfileVisibilityForm.$error = true;
                $scope.profileVisibilityErrorMessage = response.data;
            });
    }

    this.updatePortfolioService = function updatePortfolioService($http, $location, $scope, emailUri) {
        return $http({
            method: 'PATCH',
            port: 8443,
            url: 'https://localhost:8443/instructor/instructor_home/' + emailUri + '/portfolio-update', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                portfolio: $scope.editPortfolio
            }
        }).then(
            function successCallback(response) {
                $scope.submitted = false;
                $scope.editPortfolio = null;
                $scope.portfolio = response.data;
                $scope.isEditPortfolioSuccess = true;
                $scope.portfolioSuccessMessage = 'portfolio change successfully';

            }, function errorCallback(response) {
                $scope.submitted = false;
                $scope.editPortfolioForm.$valid = false;
                $scope.editPortfolioForm.$error = true;
                $scope.portfolioErrorMessage = response.data;
            });
    }

    this.updateNicknameService = function updateNicknameService($http, $location, $scope, emailUri) {
        return $http({
            method: 'PATCH',
            port: 8443,
            url: 'https://localhost:8443/instructor/instructor_home/' + emailUri + '/nickname-update', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                nickname: $scope.editNickname
            }
        }).then(
            function successCallback(response) {
                $scope.submitted = false;
                $scope.editNickname = null;
                $scope.nickname = response.data;
                $scope.isEditNicknameSuccess = true;
                $scope.nicknameSuccessMessage = 'nickname change successfully';

            }, function errorCallback(response) {
                $scope.submitted = false;
                $scope.editNicknameForm.$valid = false;
                $scope.editNicknameForm.$error = true;
                $scope.nicknameErrorMessage = response.data;
            });
    }

    this.updateSpecialtyService = function updateSpecialtyService($http, $location, $scope, emailUri) {
        return $http({
            method: 'PATCH',
            port: 8443,
            url: 'https://localhost:8443/instructor/instructor_home/' + emailUri + '/specialty-update', //FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                specialty: $scope.editSpecialty
            }
        }).then(
            function successCallback(response) {
                $scope.submitted = false;
                $scope.editSpecialty = null;
                $scope.specialty = response.data;
                $scope.isEditSpecialtySuccess = true;
                $scope.specialtySuccessMessage = 'specialty change successfully';

            }, function errorCallback(response) {
                $scope.submitted = false;
                $scope.editSpecialtyForm.$valid = false;
                $scope.editSpecialtyForm.$error = true;
                $scope.specialtyErrorMessage = response.data;
            });
    }

    this.updateHeadlineService = function updateHeadlineService($http, $location, $scope, emailUri) {
        return $http({
            method: 'PATCH',
            port: 8443,
            url: 'https://localhost:8443/instructor/instructor_home/' + emailUri + '/headline-update',//FIXME
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                headline: $scope.editHeadline
            }
        }).then(
            function successCallback(response) {
                $scope.submitted = false;
                $scope.editHeadline = null;
                $scope.headline = response.data;
                $scope.isEditHeadlineSuccess = true;
                $scope.headlineSuccessMessage = 'headline change successfully';

            }, function errorCallback(response) {
                $scope.editHeadlineForm.$valid = false;
                $scope.editHeadlineForm.$error = true;
                $scope.headlineErrorMessage = response.data;
            });
    }
}]);