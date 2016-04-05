app.controller('accountController', function ($scope) {
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
    

});