/**
 * Created by Arbindra on 4/6/2016.
 * Controller that handles logic after successfully logging into an account
 */

angular.module('app').controller('accountDetailsController', function ($scope, $routeParams, $location, loginLogoutService, accountDetailsService, updateAccountDetailService, followAccountService, messageService) {
    $scope.message = 'User Account Page';
    $scope.canUpdate = false;
    $scope.canPostMessage = false;

    $scope.getAccountDetails = function () {
        $scope.Following = false;

        /**
         * Shows the user's detail page by handlename
         */
        if ($routeParams.handle) {

            var currentUser = loginLogoutService.getCurrentUser();
            console.log("currentUser", currentUser);
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
            $scope.canPostMessage = false;
        }

        /**
         * Shows the user's detail page for the currently logged in user
         */
        else {
            //console.log("Getting account details for logged in user");
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
            $scope.canPostMessage = true;

            if (accountDetailsService.getTweetPosted()) {
                $scope.alerts = [
                    {type: 'success', msg: 'Message Posted!'}
                ];
            }

            accountDetailsService.setTweetPosted(false);
        }
    }

    /**
     * Search messages by message content
     */
    $scope.doSearch = function () {
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
            var path = "#/account/" + $routeParams.handle;
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

        $scope.alerts = [
            {type: 'success', msg: 'Message Posted!'}
        ];
        var currentUser = loginLogoutService.getCurrentUser();

        var payLoad = {"status_message": $scope.postMessageToken, "account": currentUser.id};

        // Make a POST request
        var tweet = messageService.save({selfId: currentUser.id}, payLoad);

        tweet.$promise.then(function (response) {
            accountDetailsService.setTweetPosted(true);
            $location.path("/account");
        });

    }
});

angular.module("app").directive('buttonDirective', function ($compile) {
    return {
        // ... Link lets you keep track of changes in your directive
        link: function (scope, element) {
            scope.$watchGroup(['canUpdate', 'Following'], function (newValues, oldValues, scope) {
                console.log(newValues)
                console.log("following", scope.Following)
                console.log("canUpdate", scope.canUpdate)


                if (!scope.canUpdate && !scope.Following) {
                    element.html('<h5>You are not following this account yet!</h5>')
                    element.html('<button id="followButton" type="button" class="btn btn-danger btn-lg" ng-click="followAccount()">Follow this account!</button>');

                }
                if (!scope.canUpdate && scope.Following) {
                    element.html('<h5>You are following this account</h5>')
                    element.html('<button id="followingButton" type="button" class="btn btn-success btn-lg" data-target="#myModal">Following</button>');
                }

                //... Once you make a button, for it to be able to use an Angular click, it needs to be recompiled
                $compile(element.contents())(scope.$new());
            });
        }
    }
});
