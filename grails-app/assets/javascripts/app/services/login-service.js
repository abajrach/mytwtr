/**
 * Created by Arbindra on 4/3/2016.
 */
app.service('loginService', function ($http) {
    var token = {};

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

    return {
        authenticate: authenticate,
        getToken: getToken,
        setToken: setToken
    };
});
