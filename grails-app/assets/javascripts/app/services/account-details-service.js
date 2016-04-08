/**
 * Created by Arbindra on 4/6/2016.
 */
app.service('accountDetailService', function ($http) {
    var token = {};



    var getAccountDetails = function ($token, $handle) {
        //console.log("I am inside getAccountDetails() in accountDetailService code");
        //console.log($token);
        //console.log($handle);
        $http.defaults.headers.post["Content-Type"] = "application/json";

        return $http({
            url: '/accounts/handle='+$handle,
            method: "GET",
            headers: {
                'X-Auth-Token': $token
            }
        });

    };

    var getRecentMessagesForAccount = function($token, $accountDetails) {
        console.log("Attempting to get most recent messages for id "+$accountDetails.id+" with token: "+$token);
        $http.defaults.headers.post["Content-Type"] = "application/json";

        return $http({
            url: '/messages/'+$accountDetails.id+'/recentmessages?max='+$accountDetails.messages.length,
            method: "GET",
            headers: {
                'X-Auth-Token': $token
            }
        });
    };

   return {
        getAccountDetails: getAccountDetails,
        getRecentMessagesForAccount: getRecentMessagesForAccount
    };
});
