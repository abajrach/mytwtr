/**
 * Created by Arbindra on 4/3/2016.
 */
app.service('loginService', function ($http) {
    var token = {};

    //var getToken = function()

    var authenticate = function ($scope.usrName, $scope.pwd) {
        console.log("I am inside authenticate() in loginService code");

        $http.defaults.headers.post["Content-Type"] = "application/json";
        console.log($usrNamePassword.usrName);
        console.log($usrNamePassword.pwd);
        $http({
            url: '/api/login',
            method: "POST",
            data: {'username': $usrNamePassword.usrName, 'password': $usrNamePassword.pwd}
        })
            .then(function successCallback(response) {
                    // success
                    console.log("success");
                    console.log(response.status);
                    console.log(response.data);
                },
                function errorCallback(response) {
                    // failed
                    console.log("failed");
                    console.log(response.status);
                });
    };

    return {
        authenticate: authenticate
    };
});
