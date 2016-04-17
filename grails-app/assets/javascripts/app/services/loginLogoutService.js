
angular.module('app').factory('loginLogoutService', ['$http', '$rootScope', 'webStorage', function ($http, $rootScope, webStorage) {

    var service = {};
    var currentUser;

    var setCurrentUser = function (user) {
        currentUser = user;
        webStorage.set('accountUser', currentUser);
        $rootScope.$emit('userChange', currentUser);
    };

    var loginSuccess = function (response) {
        setCurrentUser({
            username: response.data.username,
            roles: response.data.roles,
            token: response.data['access_token'],
            followingAccounts: []
        });
    };

    var loginFailure = function () {
        setCurrentUser(undefined);
    }

    service.doLogin = function ($credentials) {
        var loginPayLoad = {username: $credentials.usrName, password: $credentials.pwd};
        return $http.post('/api/login', loginPayLoad).then(loginSuccess, loginFailure);
    }

    service.doLogout = function () {
        delete $rootScope.currentUser;
        setCurrentUser(undefined);
    }

    service.isLoggedIn = function() {
        if(currentUser) {
            return true;
        }
        else {
            return false;
        }
    }

    service.getCurrentUser = function () {
        return currentUser
    }

    service.setCurrentUserId = function ($id) {
        currentUser.id = $id;
    }

    service.setFollowingAccounts = function (following) {
        currentUser.followingAccounts = [];
        for(i = 0; i < following.length; i++) {
            currentUser.followingAccounts.push(following[i].id);
        }
        console.log(currentUser);
    }

    service.addToFollowingAccounts = function($followingId) {
        currentUser.followingAccounts.push($followingId);
        console.log(currentUser);
    }

    service.setFollowedByAccounts = function (followedByAccounts) {
        currentUser.followedByAccounts = followedByAccounts;
    }

    setCurrentUser(webStorage.get('accountUser'));

    return service;
}]);
