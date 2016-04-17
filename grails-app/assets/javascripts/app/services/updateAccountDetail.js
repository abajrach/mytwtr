/**
 * Created by Arbindra on 4/6/2016.
 */
angular.module('app').factory('updateAccountDetailService', function ($resource) {

    return $resource('/accounts/:id', null,
        {
            'update': {method: 'PUT'}
        });

});