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

        // Get all the messages for that account ID
        render Message.where {
            account.id == accountId
        }.findAll() as JSON

    }

    /*
     * Returns the list of messages for an account
     * max and offset query parameters can be used to filter the maximum and offset value for the result
     */

    def recentMessages() {

        def accountId = Integer.parseInt(params.id)
        def max = params.max ? Integer.parseInt(params.max) : 10
        def offset = params.offset ? Integer.parseInt(params.offset) : 0

        if (Account.get(accountId)) {
            def messages = Message.listOrderByDateCreated('from Message msg where msg.id = Account.get(AccountID)',
                    [max: max, offset: offset, order: 'dsc'])

            render messages as JSON
        } else {
            render(status: 404, text: "Messages for account with account ID ${accountId} don't exists")
        }
    }

    /*
     * This method is used to search for specified search term in all messages. If the search term is found, it returns
     * the message body (message ID, account ID, status message and date Created) and account handle name.
     * The search is case-insensitive
     * The format of the URL should be http://localhost:8080/messages/search?query=mssetwitter
     */

    def search() {

        def querySearch = params.query.toString()

        respond Message.where {
            status_message =~ "%${querySearch}%"
        }.list().collect {
            m -> return [message: m, accountHandle: Account.get(m.account.id).handlename]
        }
    }
}