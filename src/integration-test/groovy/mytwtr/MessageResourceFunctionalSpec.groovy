package mytwtr

import geb.spock.GebSpec
import grails.converters.JSON
import grails.test.mixin.integration.Integration
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Stepwise


@Integration
@Stepwise
/**
 * Created by taz-hp-1 on 3/10/2016.
 */
class MessageResourceFunctionalSpec extends GebSpec {
    @Shared
    def messageId
    def accountId

    RESTClient restClient

    def setup() {
        restClient = new RESTClient(baseUrl)
    }

    def 'M1: Create a Message given a specified Account id and message text'() {
        given:
        def account = new Account(handlename: 'm1account', name: 'M1-Test', password: 'd8RTHV8der1', email: 'm1@gmail.com')
        def accountJson = account as JSON

        when:
        def resp = restClient.post(path: '/accounts', body: accountJson as String, requestContentType: 'application/json')

        then:
        resp.status == 201
        resp.data
        def accountID = resp.responseData.id

        when:
       // def message = new Message(status_message: 'M1-Message', account: accountID)
        //def messageJson = message as JSON
        def status_message = "M1-Message"
        def messageJson = "{\"status_message\":\"" + status_message + "\",\"account\":" + accountID + "}"
  //      def countMessageB4 = Message.count()
        def messageResp = restClient.post(path: '/accounts/1/messages', body: messageJson as String, requestContentType: 'application/json')

        then:
        messageResp.status == 201
        messageResp.data
        //        Message.count() == countMessageB4 + 1

        when:
        messageId = messageResp.responseData.id

        then:
        messageId
        messageResp.responseData.status_message == status_message
      //  messageResp.responseData.account.id == accountID

    }

    def 'M2: Return an error response from the create Message endpoint if user is not found or message text is not valid (data-driven test) #description'() {

        when:
        // def message = new Message(status_message: 'M1-Message', account: accountID)
        //def messageJson = message as JSON
        //def status_message = "M1-Message"
        def messageJson = "{\"status_message\":\"" + status_message + "\",\"account\":" + accountID + "}"
        //      def countMessageB4 = Message.count()
        def messageResp = restClient.post(path: '/accounts/1/messages', body: messageJson as String, requestContentType: 'application/json')

        then: 'Verify that error code 422: Unprocessable Entity is thrown'
        HttpResponseException error = thrown(HttpResponseException)
        error.statusCode == 422

        where:
        description                  | status_message  | accountID
        'Empty status_message'       | ''              | 1
        'Bad account ID'             | '@ObiWanKenobi' | 0
        'empty message and bad acct' | 1               | 0
    }
/*
    def 'M3: Create a REST endpoint that will return the most recent messages for an Account. The endpoint must honor a limit parameter that caps the number of responses. The default limit is 10. (data-driven test)'() {
        expect: "fix me"
        true == false
    }

    def 'M4: Support an offset parameter into the recent Messages endpoint to provide paged responses.'() {
        expect: "fix me"
        true == false
    }

    def 'M5: Create a REST endpoint that will search for messages containing a specified search term. Each response value will be a JSON object containing the Message details (text, date) as well as the Account (handle)'() {
        expect: "fix me"
        true == false
    }*/
}

