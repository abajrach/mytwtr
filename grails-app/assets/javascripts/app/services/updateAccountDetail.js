/**
 * Created by Arbindra on 4/6/2016.
 * Service to update Account details.
 * Makes a PUT call
 */
angular.module('app').factory('updateAccountDetailService', function ($resource) {

    return $resource('/accounts/:id', null,
        {
            'update': {method: 'PUT'}
        });

});