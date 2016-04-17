/**
 * Created by Arbindra on 4/6/2016.
 */

angular.module('app').controller('accountDetailsController', function ($scope, $routeParams, $location, loginLogoutService, accountDetailsService, updateAccountDetailService, followAccountService) {
    $scope.message = 'User Account Page';
    $scope.canUpdate = false;

    $scope.getAccountDetails = function () {
        $scope.Following = false;
        /**
         * Shows the user's detail page by handlename
         */
        if ($routeParams.handle) {
            console.log("loading page for " + $routeParams.handle);

            // Check to see if the logged in user is following this user
            var currentUser = loginLogoutService.getCurrentUser();

            var thisUsersId;
            $scope.accountDetails = accountDetailsService.getAccountDetails($routeParams.handle).get();

            $scope.accountDetails.$promise.then(function (response) {
                console.log("inside promise resolved, following accounts length = "+currentUser.followingAccounts.length);
                $scope.recentMessages = accountDetailsService.getRecentMessagesForAccount(response).query();
                thisUsersId = $scope.accountDetails.id;
                for (var i = 0; i < currentUser.followingAccounts.length; i++) {
                    console.log("inside for");
                    if (thisUsersId === currentUser.followingAccounts[i]) {
                        console.log(currentUser.id + " is following " + thisUsersId);
                        $scope.Following = true;
                        break;
                    }
                }
            });

            $scope.canUpdate = false;
        }

        /**
         * Shows the user's detail page for the currently logged in user
         */
        else {
            console.log("Getting account details for logged in user");
            var currentUser = loginLogoutService.getCurrentUser();

            $scope.accountDetails = accountDetailsService.getAccountDetails(currentUser.username).get();

            $scope.accountDetails.$promise.then(function (response) {

                var followingAccounts = $scope.accountDetails.following;
                var followedByAccounts = $scope.accountDetails.followedBy;

                loginLogoutService.setFollowingAccounts(followingAccounts);
                loginLogoutService.setFollowedByAccounts(followedByAccounts);

                loginLogoutService.setCurrentUserId($scope.accountDetails.id);
                $scope.recentMessages = accountDetailsService.getRecentMessagesForAccount(response).query();
            });
            $scope.canUpdate = true;
        }
    }

    $scope.doSearch = function () {
        $scope.showSearchResult = true;
        var messageResultByToken = accountDetailsService.searchMessageByToken($scope.searchToken).query();

        messageResultByToken.$promise.then(function (data) {
            $scope.searchResults = data;
        });
    }

    $scope.followAccount = function () {
        var currentUser = loginLogoutService.getCurrentUser();

        var follow = followAccountService.post({selfId: currentUser.id, followId: $scope.accountDetails.id}, {});
        follow.$promise.then(function (response) {
            loginLogoutService.addToFollowingAccounts($scope.accountDetails.id);

            var currentUser2 = loginLogoutService.getCurrentUser();
            console.log(currentUser2);
            loginLogoutService.publicSetCurrentUser(currentUser2);

            $scope.Following = true;
            var path = "#/account/"+$routeParams.handle;
            window.location.reload()
        });
    }

    /**
     * Do PUT on http://localhost:8080/accounts/4 with a request body as:
     * {
     *       "name": "flash",
     *       "email": "flashisfast@gmail.com"
     * }
     */
    $scope.updateInfo = function () {
        var currentUser = loginLogoutService.getCurrentUser();

        if (!$scope.newName && !$scope.newEmail) {
            // Throw error
        }
        if (!angular.isDefined($scope.newName)) {
            $scope.newName = currentUser.username;

        }

        if (!angular.isDefined($scope.newEmail)) {
            $scope.newEmail = currentUser.email;
        }

        var payLoad = {"name": $scope.newName, "email": $scope.newEmail};
        var upd = updateAccountDetailService.update({id: currentUser.id}, payLoad);

        upd.$promise.then(function (response) {
            $location.path("/account");
        });

    }
});
