/**
 * Created by Arbindra on 4/6/2016.
 */

/**
 * 
 */
angular.module('app').controller('loginLogoutController', function ($scope, $location, loginLogoutService) {
    $scope.login = function () {

        $scope.ui = { alert: false };  // Sends alert message when login fails

        var credentials = new Object();
        credentials.usrName = $scope.usrName;
        credentials.pwd = $scope.pwd;

        loginLogoutService.doLogin(credentials)
            .then(function (response) {
                    console.log("success");
                    $scope.loggedOut = false;

                    $location.path("/account");

                },
                function (response) {
                    console.log("failed");
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

angular.module('app').controller('aboutController', function ($scope) {
    $scope.message = 'MSSE 5199 Class Project';
});
