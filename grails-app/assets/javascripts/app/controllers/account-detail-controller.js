/**
 * Created by Arbindra on 4/6/2016.
 */

app.controller('accountController', function ($scope, loginService, accountDetailService) {
    $scope.message = 'User Account Page';

/*    $scope.login = function () {

        $scope.ui = { alert: false };  // Sends alert message when login fails

        var credentials = new Object();
        credentials.usrName = $scope.usrName;
        credentials.pwd = $scope.pwd;

        loginService.authenticate(credentials)
            .then(function (response) {
                    console.log("success");
                    console.log(response.status);
                    loginService.setToken(response.data.access_token);
                    $location.path("/account");

                },
                function (response) {
                    console.log("failed");  // @Todo: Display auth fail message
                    console.log(response.status);
                    $scope.ui.alert = true;
                });
    }*/

    $scope.getAccountDetails = function() {
        //console.log("Inside getAccountDetails of accountController");

        var token = loginService.getToken();
        var handle = loginService.getAccountHandle();
        console.log("Token read from controller: "+token);

        accountDetailService.getAccountDetails(token, handle)
            .then(function (response) {
                console.log("successfully got accountDetails");
                console.log(response.status);
                console.log(response.data);
            },
            function(response) {
                console.log("getAccountDetails failed");  // @Todo: Display auth fail message
                console.log(response.status);
            });
    }

});