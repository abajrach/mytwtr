/**
 * Created by Arbindra on 4/6/2016.
 */

app.controller('accountController', function ($scope, loginService, accountDetailService) {
    $scope.message = 'User Account Page';

    $scope.getAccountDetails = function() {

        var token = loginService.getToken();
        var handle = loginService.getAccountHandle();
        var accountDetails = {};

        accountDetailService.getAccountDetails(token, handle)
            .then(function (response) {
                console.log(response.status);
                console.log(response.data);
                accountDetails = response.data;
                $scope.accountDetails = response.data;  // @Todo: account.tpl.html expects $scope. Fix this

                accountDetailService.getRecentMessagesForAccount(token, accountDetails)
                    .then(function(response) {
                        $scope.recentMessages = response.data;
                    },
                    function(response) {
                        console.log("getRecentMessagesForAccount failed");
                        console.log(response.status);
                    });
            },
            function(response) {
                console.log("getAccountDetails failed");  // @Todo: Display auth fail message
                console.log(response.status);
            });
    }

});