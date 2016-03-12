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

    def 'Create set of accounts to test Messages functionality #description'() {
        given:
        def account = new Account(handlename: handlename, name: name, password: password, email: email)
        def json = account as JSON

        when:
        def resp = restClient.post(path: '/accounts', body: json as String, requestContentType: 'application/json')

        then: 'Verify that all accounts are successfully created'
        resp.status == 201
        resp.data

        where:
        description | handlename | name            | password       | email
        'm31'       | '@m31'     | 'M31 test acct' | 'd8ArthVader1' | 'm31@gmail.com'
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
        def status_message = "M1-Message"
        def messageJson = "{\"status_message\":\"" + status_message + "\",\"account\":" + accountID + "}"
        def messageResp = restClient.post(path: '/accounts/1/messages', body: messageJson as String, requestContentType: 'application/json')
        messageId = messageResp.responseData.id
        then:
        messageResp.status == 201
        messageResp.data
        messageId
        messageResp.responseData.status_message == status_message
        //  messageResp.responseData.account.id == accountID

    }

    def 'M2: Return an error response from the create Message endpoint if user is not found or message text is not valid (data-driven test) #description'() {

        when:
        def messageJson = "{\"status_message\":\"" + status_message + "\",\"account\":" + accountID + "}"
        def pathForTest = "/accounts/" + accountID + "/messages"
        def messageResp = restClient.post(path: pathForTest, body: messageJson as String, requestContentType: 'application/json')

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
        when: 'Add message #status_message'
        def messageJson = "{\"status_message\":\"" + status_message + "\",\"account\":" + accountID + "}"
        def pathForTest = "/accounts/"+ accountID + "/messages"
        def messageResp = restClient.post(path: pathForTest, body: messageJson as String, requestContentType: 'application/json')
        messageId = messageResp.responseData.id

        then: 'Message added'
        messageResp.status == 201
        messageResp.data
        messageId
        messageResp.responseData.status_message == status_message

        when: 'Getting all the message, no query parameters used - Result count should be #expectedMsgCount'
        pathForTest = "/accounts/"+ accountID + "/messages/getMessages"
        messageResp = restClient.get(path: pathForTest, requestContentType: 'application/json')

        then: 'Account #accountId should have #expectedMsgCount. As list is in desc order should be current number added'
        messageResp.status == 200
        messageResp.data.size() == expectedMsgCount
        messageResp.data[0].id == expectedMsgCount

        when: 'Getting latest 3 messages for account Id 6'
   //     resp = restClient.get(path: "/accounts/6/getfollowers", query: ['max': 3])
        pathForTest = "/accounts/"+ accountID + "/messages/getMessages"
        messageResp = restClient.get(path: pathForTest, , query: ['max': 3], requestContentType: 'application/json')

        then: 'If there are more than 3 messages, Returns at max only 3 messages'
        messageResp.status == 200
        if (expectedMsgCount > 3) {
            messageResp.data.size() == 3
            messageResp.data[0].id == 3
            messageResp.data[1].id == 2
            messageResp.data[2].id == 1
        }
        else {
            messageResp.data.size() == expecteMsgCount
        }

        where:
        description      | status_message  | accountID  | expectedMsgCount
        'Message1'       | 'Message 1'     | 1          | 1
        'Message2'       | 'Message 2'     | 1          | 2
        'Message3'       | 'Message 3'     | 1          | 3
        'Message4'       | 'Message 4'     | 1          | 4
        'Message5'       | 'Message 5'     | 1          | 5
        'Message6'       | 'Message 6'     | 1          | 6
        'Message7'       | 'Message 7'     | 1          | 7
        'Message8'       | 'Message 8'     | 1          | 8
        'Message9'       | 'Message 9'     | 1          | 9
        'Message10'      | 'Message 10'    | 1          | 10
        'Message11'      | 'Message 11'    | 1          | 11




    }
*/
    /*   def 'M4: Support an offset parameter into the recent Messages endpoint to provide paged responses.'() {
           expect: "fix me"
           true == false
       }
   */

    def 'M5: Create a REST endpoint that will search for messages containing a specified search term. Each response value will be a JSON object containing the Message details (text, date) as well as the Account (handle)'() {
        given: 'Create few messages with search term which we want to search later'

        def message1 = '{"status_message": "This ferrari is an awesome car", "account": 1}'
        def message2 = '{"status_message": "Ferraris are very fast cars", "account": 1}'
        def message3 = '{"status_message": "The type of this car is ferrari.", "account": 1}'
        def message4 = '{"status_message": "I saw ferari today", "account": 1}'

        restClient.post(path: '/accounts/1/messages', body: message1 as String, requestContentType: 'application/json')
        restClient.post(path: '/accounts/1/messages', body: message2 as String, requestContentType: 'application/json')
        restClient.post(path: '/accounts/1/messages', body: message3 as String, requestContentType: 'application/json')
        restClient.post(path: '/accounts/1/messages', body: message4 as String, requestContentType: 'application/json')

        when:
        def resp = restClient.get(path: "/messages/search", query: ['query': 'ferrari'])

        then:
        resp.status == 200
        resp.data.size() == 3
        resp.data.message.status_message.toString().contains("ferrari")
        resp.data[0].accountHandle == '@m31'
    }

    def 'Verify GET on /accounts/1/messages works' () {
        when:
        def resp = restClient.get(path: "/accounts/1/messages")

        then:
        resp.status == 200
        resp.data.size() == 4
    }

    def 'Verify GET on /accounts/1/messages/2 works' () {
        when:
        def resp = restClient.get(path: "/accounts/1/messages/2")

        then:
        resp.status == 200
        resp.data.account.id.toString() == "1"
        resp.data.status_message.toString() == "This ferrari is an awesome car"
    }
}

