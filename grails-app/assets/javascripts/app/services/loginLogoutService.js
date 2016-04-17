/**
 * Created by Arbindra on 4/6/2016.
 * Service that handles login/logout and keeps the current User information in context
 */
angular.module('app').factory('loginLogoutService', ['$http', '$rootScope', 'webStorage', function ($http, $rootScope, webStorage) {

    var service = {};
    var currentUser;

    /**
     * Save the current User information in context
     * @param user
     */
    var setCurrentUser = function (user) {
        console.log("setting Current user "+user)
        currentUser = user;
        webStorage.set('accountUser', currentUser);
        $rootScope.$emit('userChange', currentUser);
    };

    /**
     * Method to set the Current User outside of this Service class
     * @param user User to set as current User
     */
    service.publicSetCurrentUser = function(user) {
        console.log("publicSetCurrentUser "+user)
        setCurrentUser(user);
    }

    /**
     * Method that gets called when user successfully logs in
     * @param response
     */
    var loginSuccess = function (response) {
        console.log("login success");
        setCurrentUser({
            username: response.data.username,
            roles: response.data.roles,
            token: response.data['access_token'],
            followingAccounts: []
        });
    };

    /**
     * Method that gets called when user fails to login
     */
    var loginFailure = function () {
        setCurrentUser(undefined);
    }

    /**
     * Service method that does login
     * @param $credentials
     * @returns {*}
     */
    service.doLogin = function ($credentials) {
        var loginPayLoad = {username: $credentials.usrName, password: $credentials.pwd};
        return $http.post('/api/login', loginPayLoad).then(loginSuccess, loginFailure);
    }

    /**
     * Service method that does logout
     */
    service.doLogout = function () {
        delete $rootScope.currentUser;
        setCurrentUser(undefined);
    }

    /**
     * Service method to check if the user is currently logged in or not
     * @returns {boolean}
     */
    service.isLoggedIn = function () {
        if (currentUser) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Service method to return the currently logged in user info in context
     * @returns {*}
     */
    service.getCurrentUser = function () {
        return currentUser
    }

    /**
     * Returns the user Id of the currently logged in user
     * @param $id
     */
    service.setCurrentUserId = function ($id) {
        currentUser.id = $id;
    }

    /**
     * Method to set the following accounts
     * @param following
     */
    service.setFollowingAccounts = function (following) {
        currentUser.followingAccounts = [];
        for (i = 0; i < following.length; i++) {
            currentUser.followingAccounts.push(following[i].id);
        }
    }

    /**
     * Method to append the newly following account
     * @param $followingId
     */
    service.addToFollowingAccounts = function ($followingId) {
        currentUser.followingAccounts.push($followingId);
    }

    /**
     * Method to set the followed by accounts
     * @param followedByAccounts
     */
    service.setFollowedByAccounts = function (followedByAccounts) {
        currentUser.followedByAccounts = followedByAccounts;
    }

    /**
     * WebStorage. Handles the page reload without logging in
     */
    setCurrentUser(webStorage.get('accountUser'));

    return service;
}]);
