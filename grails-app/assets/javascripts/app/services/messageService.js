angular.module('app').factory('messageService', function ($resource) {
    var service = {};

    /**
     * Post message
     */

    angular.module('app').factory('createMessageService', function ($resource) {

        return $resource('/accounts/:id/messages', null,
            {
                'create': {method: 'PUT'}
            });

    });

    return service;
});
