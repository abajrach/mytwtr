package mytwtr

import grails.converters.JSON
import grails.rest.RestfulController
import grails.transaction.Transactional
import org.apache.http.protocol.HTTP

import javax.servlet.http.HttpServletResponse
import java.text.SimpleDateFormat

class MessageController extends RestfulController<Message> {
    static responseFormats = ['json', 'xml']

    MessageController() {
        super(Message)
    }

    /**
     * This method is called when URL path is /accounts/1/messages/1
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
        def limit = params.max ? Integer.parseInt(params.max) : 10
        def offset = params.offset ? Integer.parseInt(params.offset) : 0

        if (Account.get(accountId)) {
            def messageResults = Message.withCriteria {
                'in'('account', Account.get(accountId))
                order('dateCreated', 'desc')
                firstResult(offset)
                maxResults(limit)
            }
            render messageResults as JSON
        } else {
            render(status: 404, text: "No messages for account with account ID ${accountId} exist")
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

    /*
     * This method is used to post Message for an account
     */
    /*
    @Transactional
    def postMessage() {
        Account account;
        def tweet = request.JSON.tweet

        if(tweet.size() == 0 || tweet.size() > 40) {
            response.sendError(HttpServletResponse.SC_LENGTH_REQUIRED)
            return
        }

        if(params.id) {
            account = Account.findById(params.id)
        }
        else if (params.handle) {
            account = Account.findByHandlename(params.handle)
        }
        else {
            response.sendError(404)
            response.sendError(HttpServletResponse.SC_BAD_REQUEST)
            return
        }

        if(!account) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST)
            return
        }

        Message newTweet = new Message(status_message: tweet, account: account)
        respond(status: 201, text: 'Tweet successfully posted!!!')
    }
    */
}