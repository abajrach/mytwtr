package mytwtr

import geb.spock.GebSpec
import groovyx.net.http.RESTClient
import spock.lang.Shared

/**
 * Created by taz-hp-1 on 3/10/2016.
 */
class MessageResourceFunctionalSpec extends GebSpec{
    @Shared
    def messageId
    def accountId

    RESTClient restClient

    def setup() {
        restClient = new RESTClient(baseUrl)
        /* def account = new Account(handlename: 'Andy', name: 'Andrew Jackson', password: 'jacksonforprez', email: 'jackson.andrew@whitehouse.gov')
         def accountJson = account as JSON
         def respAccount = restClient.post(path: '/accounts', body: accountJson as String, requestContentType: 'application/json')
 */
    }
    def 'Sanity check'() {
        expect: "fix me"
        true == true
    }
   /* def 'MC1.Unit - Get all message information with no records'(){
        when:
        def resp = restClient.get(path: '/messages')

        then:
        resp.status == 200
        resp.data.size() == 0
    }*/

    /* def 'M1: Create a Message given a specified Account id or handle and message text'() {
         given:

         def message = new Message(status_message: 'Hola TWTR', belongsTo: account)
         def messageJson = message as JSON

         when:

         def resp = restClient.post(path: '/messages', body: messageJson as String, requestContentType: 'application/json')

         then:
         resp.status == 201
         resp.data

         when:
         messageId = resp.data.id

         then:
         punchId
         resp.data.status_message == 'Hola TWTR'
         resp.data.belongsTo == account

     }

     def 'M2: Return an error response from the create Message endpoint if user is not found or message text is not valid (data-driven test)'() {
         expect: "fix me"
         true == false
     }

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

