/**
 * Created by Arbindra on 4/6/2016.
 * Service Used to follow accounts
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