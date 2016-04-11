/**
 * Created by Arbindra on 4/6/2016.
 */

angular.module('app').controller('accountDetailsController', function ($scope, $routeParams, loginLogoutService, accountDetailsService) {
    $scope.message = 'User Account Page';

    $scope.getAccountDetails = function() {
    	//console.log("hahaha getAccountDetails called");

        /**
         * Shows the user's detail page by handlename
         */
        if($routeParams.handle) {
            console.log("handle passed = " + $routeParams.handle);
        
            $scope.accountDetails = accountDetailsService.getAccountDetails($routeParams.handle).get();

            $scope.accountDetails.$promise.then(function(response){
                $scope.recentMessages = accountDetailsService.getRecentMessagesForAccount(response).query();
            });            

        }

        /**
         * Shows the user's detail page for the currently logged in user
         */
        else {
            var currentUser = loginLogoutService.getCurrentUser();

            $scope.accountDetails = accountDetailsService.getAccountDetails(currentUser.username).get();

            $scope.accountDetails.$promise.then(function(response){
                $scope.recentMessages = accountDetailsService.getRecentMessagesForAccount(response).query();
            });
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
        return null;
    }

    /**
     * Do PUT on http://localhost:8080/accounts/4 with a request body as:
     * {
     *       "name": "flash",
     *       "email": "flashisfast@gmail.com"
     * }
     */
    $scope.updateInfo = function() {
        return null;
    }    
});
