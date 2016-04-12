angular.module('app').factory('followAccountService', function ($resource) {
	return $resource('/accounts/:selfId/follow/:followId', {}, 
		{ 
			post: {method: 'POST', params: 
					{
						selfId: '@selfId', 
						followId: '@followId'
					}, isArray:true
				  }
	    });
});