angular.module('app').factory('messageService', function ($resource) {

/*    var service = {};
    var tweetPosted;

    service.setTweetPosted = function (flag) {
        tweetPosted = flag;
    };

    service.getTweetPosted = function() {
        return tweetPosted;
    }*/

    return $resource('/accounts/:selfId/messages', {},
        {
            post: {
                method: 'POST', params: {
                    selfId: '@selfId',
                    status_message: '@message'
                }, isArray: true
            }
        });

    //return service
});
