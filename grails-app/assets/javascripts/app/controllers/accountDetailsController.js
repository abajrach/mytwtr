/**
 * Created by Arbindra on 4/6/2016.
 * Controller that handles logic after successfully logging into an account
 * @Todo: Break the functionalities into separate modular controllers in phase 4
 */

angular.module('app').controller('accountDetailsController', function ($scope, $routeParams, $location, loginLogoutService, accountDetailsService, updateAccountDetailService, followAccountService, messageService) {
    $scope.message = 'User Account Page';
    $scope.canUpdate = false;

    $scope.getAccountDetails = function () {
        $scope.Following = false;

        /**
         * Shows the user's detail page by handlename
         */
        if ($routeParams.handle) {

            var currentUser = loginLogoutService.getCurrentUser();

            var thisUsersId;

            // Get the account details for this user
            $scope.accountDetails = accountDetailsService.getAccountDetails($routeParams.handle).get();

            // Get the list of most recent messages for this user
            $scope.accountDetails.$promise.then(function (response) {
                $scope.recentMessages = accountDetailsService.getRecentMessagesForAccount(response).query();
                thisUsersId = $scope.accountDetails.id;
                for (var i = 0; i < currentUser.followingAccounts.length; i++) {
                    if (thisUsersId === currentUser.followingAccounts[i]) {
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

            // Get the account details for this user
            $scope.accountDetails = accountDetailsService.getAccountDetails(currentUser.username).get();

            // Get the list of most recent messages for this user
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

    /**
     * Search messages by message content
     */
    $scope.doSearch = function () {
        console.log("inside doSearch")
        $scope.showSearchResult = true;
        var messageResultByToken = accountDetailsService.searchMessageByToken($scope.searchToken).query();

        messageResultByToken.$promise.then(function (data) {
            $scope.searchResults = data;
        });
    }

    /**
     * Follow an account
     */
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
     * Update email and name for logged in user
     * Do PUT on http://localhost:8080/accounts/1 with a request body as:
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

        // If new Name is not entered in the text box, use original name
        if (!angular.isDefined($scope.newName)) {
            $scope.newName = currentUser.username;
        }

        // If new email is not entered in the text box, use original email address
        if (!angular.isDefined($scope.newEmail)) {
            $scope.newEmail = currentUser.email;
        }

        var payLoad = {"name": $scope.newName, "email": $scope.newEmail};

        // Make a PUT request
        var upd = updateAccountDetailService.update({id: currentUser.id}, payLoad);

        upd.$promise.then(function (response) {
            $location.path("/account");
        });
    }

    $scope.doPostMessage = function () {

        var currentUser = loginLogoutService.getCurrentUser();

        var payLoad = {"status_message": $scope.postMessageToken, "account": currentUser.id};

        // Make a POST request
        var tweet = messageService.save({selfId: currentUser.id}, payLoad);

        tweet.$promise.then(function(response) {
            console.log("tweet created successfully");
        });

    }
});
