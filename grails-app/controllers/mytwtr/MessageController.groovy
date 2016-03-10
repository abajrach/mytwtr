package mytwtr

import grails.converters.JSON
import grails.rest.RestfulController

class MessageController extends RestfulController<Message> {
    static responseFormats = ['json', 'xml']

    MessageController() {
        super(Message)
    }

    def index() {

        if(params.handle) {
            //println 'inside index '+ params.handle
            def message = Message.findByHandlename(params.handle)
            if(message) {
                render message as JSON
            }
            else {
                response.status = 404 // @Todo: Fix the notFound.gsp issue. Return status is 404 but the page is empty
            }
        }
        else {
            // @Todo: Return all the message
        }
    }
}