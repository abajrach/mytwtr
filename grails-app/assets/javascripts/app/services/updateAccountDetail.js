angular.module('app').factory('updateAccountDetailService', function ($resource) {

	return $resource('/accounts/:id', null,
	{
	    'update': { method:'PUT' }
	});

});