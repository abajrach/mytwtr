describe("true", function()
{
    it("Should be true", function(){
        expect(true).toBeTruthy()

    });

});




/*



describe('MessageController', function () {

    var $controller, $httpBackend, $scope;
    var now = new Date();
    var posts = [
        {song: {title: 'Loser', artist: {name: 'Beck'}}, timestamp: now},
        {song: {title: 'Enjoy The Silence', artist: {name: 'Depeche Mode'}}, timestamp: now}];

    beforeEach(module('app'));

    beforeEach(inject(function ($injector) {
        $controller = $injector.get('$controller');
        $httpBackend = $injector.get('$httpBackend');
        $scope = $injector.get('$rootScope').$new();
    }));

    describe('initializes scope', function () {

        it('fetches the play data into scope', function () {
            $httpBackend.expectGET('play/').respond(200, plays);
            $controller('PostMessageController', {$scope: $scope});
            $httpBackend.flush();

            expect($scope.plays).toEqual(plays);
        });

    });

    afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
});



*/








describe('messageService', function () {
    beforeEach(module('app'));


    var messageService;
    var $httpBackend;


    beforeEach(inject(function (_messageService_, _$httpBackend_) {
        messageService = _messageService_;
        $httpBackend = _$httpBackend_;
    }));


    describe('save', function () {
        it('handles save message', function () {
            $httpBackend.expectPOST('/accounts/1/messages', {selfId: 1}, payLoad).respond(200, {username: 'earl', roles: ['root'], 'access_token': 'xyz123'});
            messageService.save('u', 'p');
            $httpBackend.flush();
            var user = messageService.currentUser();
         //   expect(user).toBeDefined();
         //   expect(user.username).toBe('earl');
         //   expect(user.roles).toContain('root');
         //   expect(user.token).toBe('xyz123');
        });


      //  it('handles invalid message too long', function () {
      //      $httpBackend.expectPOST('/api/login', {username: 'u', password: 'p'}).respond(403, {status: 403, message: 'Forbidden'});
      //      securityService.login('u', 'p');
      //      $httpBackend.flush();
      //      var user = securityService.currentUser();
      //      expect(user).toBeUndefined();
      //  });
    });


    afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });


});