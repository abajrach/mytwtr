package mytwtr

import grails.converters.JSON
import grails.rest.RestfulController

class MessageController extends RestfulController<Message> {
    static responseFormats = ['json', 'xml']

    MessageController() {
        super(Message)
    }
}