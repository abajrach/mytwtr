/**
 * Created by Arbindra on 4/6/2016.
 * Service that handles most of the functionality once the user logs in
 * @Todo: For phase 4, break it into smaller services
 */

angular.module('app').factory('accountDetailsService', function ($resource) {
    var service = {};
    var tweetPosted = false;

    /**
     * Get account details by handlename
     * @param $handle: handlename for the user
     * @returns {Account details such as name, id, message ids, etc}
     */
    service.getAccountDetails = function ($handle) {
        return $resource('/accounts/handle=' + $handle, {});
    };

/*    service.updateAccountDetails = function ($id, $newName, $newEmail) {
        console.log("service: id passed = " + $id + " " + $newName + " " + $newEmail);
        return $resource('/accounts/:id', null,
            {
                'update': {method: 'PUT'}
            });
    };*/

    /**
     * Returns the list of most recent messages
     * @param $response Response body returned by GET /accounts/1
     * @returns {List of most recent messages}
     */
    service.getRecentMessagesForAccount = function ($response) {
        return $resource('/messages/' + $response.id + '/recentmessages?max=' + $response.messages.length, {});
    };

    /**
     * Returns the list of messages by token
     * Does GET on 'http://localhost:8080/messages/search?query=mssetwitter'
     * @param $searchToken Message to search for
     * @returns {List of messages with matching result}
     */
    service.searchMessageByToken = function ($searchToken) {
        return $resource('messages/search?query=' + $searchToken, {});
    };


    service.setTweetPosted = function (flag) {
        tweetPosted = flag;
    };

    service.getTweetPosted = function() {
        return tweetPosted;
    }

    return service;
});
