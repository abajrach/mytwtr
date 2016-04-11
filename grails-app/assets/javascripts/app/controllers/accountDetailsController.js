/**
 * Created by Arbindra on 4/6/2016.
 */

angular.module('app').controller('accountDetailsController', function ($scope, $routeParams, loginLogoutService, accountDetailsService) {
    $scope.message = 'User Account Page';

    $scope.getAccountDetails = function() {
    	console.log("hahaha getAccountDetails called");
        if($routeParams.handle) {
            console.log("handle passed = " + $routeParams.handle);
        
            $scope.accountDetails = accountDetailsService.getAccountDetails($routeParams.handle).get();

            $scope.accountDetails.$promise.then(function(response){
                $scope.recentMessages = accountDetailsService.getRecentMessagesForAccount(response).query();
            });            

        }

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
});
