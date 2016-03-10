package mytwtr

import grails.converters.JSON
import grails.rest.RestfulController

class MessageController extends RestfulController<Message> {
    static responseFormats = ['json', 'xml']

    MessageController() {
        super(Message)
    }

    def index() {

        if (params.handle) {
            //println 'inside MessageController index '+ params.handle
            def message = Message.findByHandlename(params.handle)
            if (message) {
                render message as JSON
            } else {
                response.status = 404 // @Todo: Fix the notFound.gsp issue. Return status is 404 but the page is empty
            }
        } else {
            // @Todo: Return all the message
        }
    }

    def postMessage() {
        def account = Account.get(params.id)
        if (account){
        def message = new Message(status_message: status_message, account: account).save
        if (message) {
            render message as JSON
            }
            else{
            response.status = 404
            }
        }
        else{
             response.status = 404
        }
    }
}