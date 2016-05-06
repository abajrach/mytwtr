/*describe("true", function()
 {
 it("Should be true", function(){
 expect(true).toBeTruthy()

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
        console.log('------------------0---------------------')
    }));


    describe('save', function () {
        it('handles save message', function () {
            //    $httpBackend.expectPOST('/accounts/1/messages', {status_message:'test', account:'1'}).respond(201, {messageId: '1', status_message: 'test'});
            console.log('------------------1---------------------')
            $httpBackend.expectPOST('/accounts/messages', {status_message:'test',id:1}).respond(201, {id:1,status_message:'test'});
            console.log('------------------2---------------------')
            // messageService.postMessage(1, 'test');
            messageService.$resource.post(1, 'test');
            console.log('------------------3---------------------')
            $httpBackend.flush();
            console.log('------------------4---------------------')
            var message = messageService.status_message();
            console.log('------------------5---------------------')
            expect(message).toBeDefined();
            console.log('------------------6---------------------')
            console.log("Message -> ", message)
            expect(message = 'test');
        });


    });


    afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });


});