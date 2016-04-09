/**
 * Created by Arbindra on 4/3/2016.
 */
app.service('loginLogoutService', function ($http) {
    var token = {};
    var accountHandle = {};

    var authenticate = function ($credentials) {
        console.log("I am inside authenticate() in loginLogoutService code");

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

    var doLogout = function() {
        token = null;
        accountHandle = null;
    }

    return {
        authenticate: authenticate,
        getToken: getToken,
        setToken: setToken,
        getAccountHandle: getAccountHandle,
        setAccountHandle: setAccountHandle,
        doLogout: doLogout
    };
});