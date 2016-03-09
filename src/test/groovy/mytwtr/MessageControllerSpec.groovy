package mytwtr

import geb.spock.GebSpec
import grails.test.mixin.TestFor
import grails.test.mixin.integration.Integration
import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(MessageController)
@Integration
@Stepwise
class MessageControllerSpec extends GebSpec {
    @Shared
    def messageId
    RESTClient restClient

    def setup() {
        restClient = new RESTClient(baseUrl)
    }
    def 'MC1.Unit - Get all message information with no records'(){
        when:
        def resp = restClient.get(path: '/messages')

        then:
        resp.status == 200
        resp.data.size() == 0

    }

    def 'M1: Create a Message given a specified Account id or handle and message text'(){
        expect: "fix me"
        true == false
    }

    def 'M2: Return an error response rom the create Message endpoint if user is not found or message text is not valid (data-driven test)'(){
        expect: "fix me"
        true == false
    }

    def 'M3: Create a REST endpoint that will return the most recent messages for an Account. The endpoint must honor a limit parameter that caps the number of responses. The default limit is 10. (data-driven test)'(){
        expect: "fix me"
        true == false
    }

    def 'M4: Support an offset parameter into the recent Messages endpoint to provide paged responses.'(){
        expect: "fix me"
        true == false
    }

    def 'M5: Create a REST endpoint that will search for messages containing a specified search term. Each response value will be a JSON object containing the Message details (text, date) as well as the Account (handle)'(){
        expect: "fix me"
        true == false
    }

    def cleanup() {
    }

    void "test something"() {
        expect: "fix me"
        true == false
    }
}
