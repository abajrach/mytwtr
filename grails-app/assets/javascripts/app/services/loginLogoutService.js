/**
 * Created by Arbindra on 4/3/2016.
 */

angular.module('app').factory('loginLogoutService', ['$http', '$rootScope', function($http, $rootScope) {

    var service = {};
    var currentUser;

    var loginSuccess = function(response) {
        currentUser = {
            username: response.data.username,
            roles: response.data.roles,
            token: response.data['access_token']
        };
        $rootScope.$emit('userChange', currentUser);
    };

    var loginFailure = function() {
        currentUser = undefined;
        delete $rootScope.currentUser;
    }

    service.doLogin = function($credentials) {
        var loginPayLoad = {username: $credentials.usrName, password: $credentials.pwd};
        return $http.post('/api/login', loginPayLoad).then(loginSuccess, loginFailure);
    }

    service.doLogout = function() {
        delete $rootScope.currentUser;
    }

    service.getCurrentUser = function() {
        return currentUser
    }

    service.setCurrentUserId = function($id) {
        currentUser.id = $id;
        //console.log("from loginLogoutService, currentUser = "+currentUser.username + " " + currentUser.id);
    }

    service.setFollowingAccounts =  function(followingAccounts) {
        currentUser.followingAccounts = followingAccounts;
/*        for (var i = 0; i < currentUser.followingAccounts.length; i++) {
            console.log("following accounts: "+currentUser.followingAccounts[i].id);
        }*/
    }

    service.setFollowedByAccounts =  function(followedByAccounts) {
        currentUser.followedByAccounts = followedByAccounts;
    }

    return service;
}]);
