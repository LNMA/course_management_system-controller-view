/*jshint esversion: 6 */
/*jshint sub:true*/
/*Content-Disposition:inline;filename=f.txt*/
/*content-type:application/javascript*/
app.service('LoginAccountsSubmitService', ['$http', function ($http) {
    this.loginSubmitForm = function loginSubmitForm(account) {
        return $http({
            method: 'POST',
            port: 8443,
            url: 'https://localhost:8443/perform_login',
            headers: {'content-type': 'application/json'},
            contentType: "application/json; charset=utf-8",
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser
            processData: false, //To avoid making query String instead of JSON
            timeout: 4000,
            data: {
                admin:{
                    email: account.email,
                    password: account.password
                },
                rememberMe: account.rememberMe
            }
        });
    };
}]);
