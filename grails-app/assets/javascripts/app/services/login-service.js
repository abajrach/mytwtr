/**
 * Created by Arbindra on 4/3/2016.
 */
app.service('loginService', function ($http) {
    var token = {};
    var accountHandle = {};

    //var getToken = function()

    var authenticate = function ($credentials) {
        console.log("I am inside authenticate() in loginService code");

        $http.defaults.headers.post["Content-Type"] = "application/json";

        return $http({
            url: '/api/login',
            method: "POST",
            data: {'username': $credentials.usrName, 'password': $credentials.pwd}
        });
    };

    var getToken = function() {
        return token;
    }

    var setToken = function(t) {
        token = t;
    }

    var getAccountHandle = function() {
        return accountHandle;
    }

    var setAccountHandle = function(name) {
        accountHandle = name;
    }

    return {
        authenticate: authenticate,
        getToken: getToken,
        setToken: setToken,
        getAccountHandle: getAccountHandle,
        setAccountHandle: setAccountHandle
    };
});
