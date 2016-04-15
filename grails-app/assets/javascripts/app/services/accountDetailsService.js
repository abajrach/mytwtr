/**
 * Created by Arbindra on 4/6/2016.
 */
angular.module('app').factory('accountDetailsService', function ($resource) {
    var service = {};

    service.getAccountDetails = function ($handle) {
        return $resource('/accounts/handle='+$handle, {});
    };

    /*
        @Todo: Remove this function
    */
    service.updateAccountDetails = function($id, $newName, $newEmail) {
        console.log("service: id passed = " + $id + " " + $newName + " " + $newEmail);
        return $resource('/accounts/:id', null,
        {
            'update': { method:'PUT' }
        });


    };

     service.getRecentMessagesForAccount = function($response) {
        //console.log("getRecentMessagesForAccount: " + $response.id);
        return $resource('/messages/'+$response.id+'/recentmessages?max='+$response.messages.length,{});
    };

    /* http://localhost:8080/messages/search?query=mssetwitter */
    service.searchMessageByToken = function($searchToken) {
    	//console.log("service: "+$searchToken);
    	return $resource('messages/search?query='+$searchToken,{});
    };

/*    service.searchMessageByPoster = function($poster) {

    };*/

  return service;
});
