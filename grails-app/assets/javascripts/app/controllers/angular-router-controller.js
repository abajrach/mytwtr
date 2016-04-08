/**
 * Created by Arbindra on 4/6/2016.
 */

app.controller('headerController', function ($scope, $location) {
    $scope.message = 'I manage the header, meaning the nav tabs';
    $scope.isActive = function (viewLocation) {
        return viewLocation == $location.path();
    };
});

/**
 * change this to loginController
 */
app.controller('mainController', function ($scope, $location, $http, loginLogoutService) {

    $scope.loggedOut = false;

    $scope.login = function () {

        $scope.ui = { alert: false };  // Sends alert message when login fails

        var credentials = new Object();
        credentials.usrName = $scope.usrName;
        credentials.pwd = $scope.pwd;

        loginLogoutService.authenticate(credentials)
            .then(function (response) {
                    console.log("success");
                    console.log(response.status);
                    loginLogoutService.setToken(response.data.access_token);
                    loginLogoutService.setAccountHandle(response.data.username);
                    $location.path("/account");

                },
                function (response) {
                    console.log("failed");
                    console.log(response.status);
                    $scope.ui.alert = true;
                });
    };

    $scope.logout = function  () {
        console.log("logout in controller called");
        $scope.loggedOut = true;
        loginLogoutService.doLogout();
        console.log("loggedout state:"+$scope.loggedOut);
    };
});

app.controller('aboutController', function ($scope) {
    $scope.message = 'MSSE 5199 Class Project';
});

/*app.controller('contactController', function ($scope) {
    $scope.message = 'Arbindra Bajracharya, Jason Nordlund';
});*/