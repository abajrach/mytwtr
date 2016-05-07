describe('messageService', function () {
    beforeEach(module('app'));

    var messageService;
    var $controller;
    var $httpBackend;
    var $scope;

    beforeEach(inject(function ($injector) {
        $controller = $injector.get('$controller');
        $httpBackend = $injector.get('$httpBackend');
        $scope = $injector.get('$rootScope').$new();
        messageService = $injector.get('messageService');
    }));

    describe('save', function () {
        it('handles post message', function () {
            $httpBackend.expectPOST('/accounts/1/messages', {"status_message":"test","account":1}).respond(201, {"status_message":"test","account":1});

            var payLoad = {"status_message": "test", "account": 1};
            var returnMessage = messageService.save({selfId: 1}, payLoad);
            $httpBackend.flush();

            expect(returnMessage.status_message == 'test');
            expect(returnMessage.account == 1);


        });

        it('Spy on messageService', function(){
            spyOn(messageService, 'save');
            var payLoad = {"status_message": "test", "account": 1};
            var returnMessage = messageService.save({selfId: 1}, payLoad);

            expect(messageService.save).toHaveBeenCalled();
        });

    });

    afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

});