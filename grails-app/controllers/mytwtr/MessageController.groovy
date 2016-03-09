package mytwtr

import grails.rest.RestfulController

class MessageController extends RestfulController<Message>{

    static responseFormats = ['json', 'xml']

    MessageController(){
        super(Message)
    }
    protected Message getMessage(Serializable id) {
        def messageId = params.messageId
        Message.where {
            id == id && status_message.id == messageId
        }.find()
    }
   /* def get() {
        def message = Message.get(params.id)
        if (!message){
            response.sendError(404)
        } else {
            [ message: message ]
        }
    }*/
}
