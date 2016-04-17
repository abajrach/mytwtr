/**
 * Created by Arbindra on 4/6/2016.
 */

angular.module('app').factory('followAccountService', function ($resource) {
    return $resource('/accounts/:selfId/follow/:followId', {},
        {
            post: {
                method: 'POST', params: {
                    selfId: '@selfId',
                    followId: '@followId'
                }, isArray: true
            }
        });
});