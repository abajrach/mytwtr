/**
 * Created by Arbindra on 4/6/2016.
 */

/**
 * Controller that handles the login/logout functionality
 */
angular.module('app').controller('loginLogoutController', function ($scope, $location, loginLogoutService) {

    /**
     * Login Controller method used for login with username/password
     */
    $scope.login = function () {

        $scope.ui = {alert: false};  // Sends alert message when login fails

        var credentials = new Object();
        credentials.usrName = $scope.usrName;
        credentials.pwd = $scope.pwd;

        loginLogoutService.doLogin(credentials)
            .finally(function (result) {
                var currentUser = loginLogoutService.getCurrentUser();
                if (currentUser) {
                    $scope.loggedOut = false;
                    $location.path("/account");
                }
                else {
                    $scope.ui.alert = true;
                }
            });
    };

    /**
     * Logout controller method used for logging out user
     */
    $scope.logout = function () {
        var isLoggedIn = loginLogoutService.isLoggedIn();
        if (isLoggedIn) {
            $scope.loggedOut = true;
            loginLogoutService.doLogout();
        }
    };
});

/**
 * Controller that gets invoked when clicking the 'About' button in the navbar
 */
angular.module('app').controller('aboutController', function ($scope) {
    $scope.message = 'MSSE 5199 Class Project';
});
