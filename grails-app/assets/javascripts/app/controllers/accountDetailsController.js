/**
 * Created by Arbindra on 4/6/2016.
 */

angular.module('app').controller('accountDetailsController', function ($scope, loginLogoutService, accountDetailsService) {
    $scope.message = 'User Account Page';

    $scope.getAccountDetails = function() {

        var currentUser = loginLogoutService.getCurrentUser();

        $scope.accountDetails = accountDetailsService.getAccountDetails(currentUser.username).get();

        $scope.accountDetails.$promise.then(function(response){
            $scope.recentMessages = accountDetailsService.getRecentMessagesForAccount(response).query();
        });
    }
});
