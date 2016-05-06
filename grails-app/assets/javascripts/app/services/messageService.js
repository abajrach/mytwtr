angular.module('app').factory('messageService', function ($resource) {

    return $resource('/accounts/:selfId/messages', {},
        {
            post: {
                method: 'POST', params: {
                    selfId: '@selfId',
                    status_message: '@message'
                }, isArray: true
            }
        });
});
