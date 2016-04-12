/**
 * Created by Arbindra on 4/6/2016.
 */

angular.module('app').controller('accountDetailsController', function ($scope, $routeParams, $location, loginLogoutService, accountDetailsService, updateAccountDetailService, followAccountService) {
    $scope.message = 'User Account Page';
    $scope.canUpdate = false;

    $scope.getAccountDetails = function() {
    	//console.log("hahaha getAccountDetails called");
        $scope.Following = false;
        /**
         * Shows the user's detail page by handlename
         */
        if($routeParams.handle) {
            console.log("handle passed = " + $routeParams.handle);
            
            // Check to see if the logged in user is following this user
            var currentUser = loginLogoutService.getCurrentUser();

            var thisUsersId;
            $scope.accountDetails = accountDetailsService.getAccountDetails($routeParams.handle).get();

            $scope.accountDetails.$promise.then(function(response){
                $scope.recentMessages = accountDetailsService.getRecentMessagesForAccount(response).query();
                thisUsersId = $scope.accountDetails.id;   
                console.log("This users id: "+thisUsersId);      

                for (var i = 0; i < currentUser.followingAccounts.length; i++) {
                    if (thisUsersId === currentUser.followingAccounts[i].id) {
                        console.log(currentUser.id + " is following " + thisUsersId);
                        $scope.Following = true;
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

            $scope.accountDetails.$promise.then(function(response){
                
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

    $scope.doSearch = function() {
        $scope.showSearchResult = true;
        var query = accountDetailsService.searchMessageByToken($scope.searchToken).query();

        query.$promise.then(function(data) {
            $scope.searchResults = data;
        });
        
    }

    $scope.followAccount = function() {
        var currentUser = loginLogoutService.getCurrentUser();
        console.log("followAccount called, selfId: "+currentUser.id+" "+$scope.accountDetails.id);

        var follow = followAccountService.post({selfId: currentUser.id,followId: $scope.accountDetails.id},{});
        follow.$promise.then(function(response){
            console.log('promise resvoled, successfully followed');
            $scope.Following = true;
        });
    }

    /**
     * Do PUT on http://localhost:8080/accounts/4 with a request body as:
     * {
     *       "name": "flash",
     *       "email": "flashisfast@gmail.com"
     * }
     */
    $scope.updateInfo = function() {
        var currentUser = loginLogoutService.getCurrentUser();
        //console.log("from updateInfo in controller, currentUser = "+currentUser.username + " " + currentUser.id);
        
        // pass in id to put request, make it so that name and email are not both required

        if(!$scope.newName && !$scope.newEmail) {
            // Throw error
        }
        if(!angular.isDefined($scope.newName)) {
            $scope.newName = currentUser.username;
            //console.log('name');
            
        }

        if(!angular.isDefined($scope.newEmail)) {
            $scope.newEmail = currentUser.email;
            //console.log('email');
        } 

        var payLoad = { "name":$scope.newName, "email":$scope.newEmail };
        console.log(payLoad);
        //updateAccountDetailService.update({ id: currentUser.id}, '{"name":"new","email":"newmailsdfwe@gmail.com"}');
        var upd = updateAccountDetailService.update({ id: currentUser.id}, payLoad);

        upd.$promise.then(function(response){
            //console.log('promise resvoled');
            $location.path("/account");
        });

    }    
});
