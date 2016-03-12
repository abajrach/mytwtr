package mytwtr

import grails.converters.JSON
import grails.rest.RestfulController

class MessageController extends RestfulController<Message> {
    static responseFormats = ['json', 'xml']

    MessageController() {
        super(Message)
    }

    /**
     * This method is called when URL path is /accounts/1/messages/1
     * * @Todo: Make sure there is tests for this
     */
    @Override
    protected Message queryForResource(Serializable id) {
        def accountId = params.accountId

        // Get the particular message for that account
        Message.where {
            id == id && account.id == accountId
        }.find()
    }

    /**
     * This method is called when URL path is /accounts/1/messages
     * @Todo: Make sure there is tests for this
     */
    def index() {
        def accountId = params.accountId

        // Verify account ID is correct
        if (Account.get(accountId) == null) {
            response.status = 404
            return
        }

        // Get all the messages for that account ID
        render Message.where {
            account.id == accountId
        }.findAll() as JSON

    }
}