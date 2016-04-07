/**
 * Created by Arbindra on 4/6/2016.
 */
app.service('accountDetailService', function ($http) {
    var token = {};

    var getAccountDetails = function ($token, $handle) {
        console.log("I am inside getAccountDetails() in accountDetailService code");
        console.log($token);
        console.log($handle);
        $http.defaults.headers.post["Content-Type"] = "application/json";

        return $http({
            url: '/accounts/handle='+$handle,
            method: "GET",
            headers: {
                'X-Auth-Token': $token
            }
        });

    };

    var getMessagesForAccount = function($token, $handle) {

    };

   return {
        getAccountDetails: getAccountDetails,
       getMessagesForAccount: getMessagesForAccount
    };
});
