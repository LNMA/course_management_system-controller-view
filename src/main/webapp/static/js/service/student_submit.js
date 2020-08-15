/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
app.service('RegisterStudentSubmitService', ['$http', function ($http) {
    this.studentSubmitForm = function studentSubmitForm(student) {
        return $http({
            method: 'POST',
            port: 8443,
            url: 'https://localhost:8443/subStudentSignUp',
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                email: student.email,
                forename: student.forename,
                surname: student.surname,
                gender: student.gender,
                birthday: student.birthday,
                phone: student.phone,
                country: student.country,
                state: student.state,
                address: student.address,
                admin: {
                    email: student.email,
                    password: student.password
                }
            }
        })/*.then(function successCallback(response) {
            console.log(response.data);
        }, function errorCallback(response) {
            console.log(response);
        })*/;
    };
}]);
