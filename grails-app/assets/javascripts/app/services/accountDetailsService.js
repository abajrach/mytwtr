/**
 * Created by Arbindra on 4/6/2016.
 */
angular.module('app').factory('accountDetailsService', function ($resource) {
    var service = {};

    service.getAccountDetails = function ($handle) {
        return $resource('/accounts/handle='+$handle, {});
    };

     service.getRecentMessagesForAccount = function($response) {
        console.log("getRecentMessagesForAccount: " + $response.id);
        return $resource('/messages/'+$response.id+'/recentmessages?max='+$response.messages.length,{});
    };

  return service;
});
