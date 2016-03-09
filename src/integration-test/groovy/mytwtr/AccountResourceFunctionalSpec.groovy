package mytwtr

import geb.spock.GebSpec
import grails.converters.JSON
import grails.test.mixin.integration.Integration
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Stepwise

/**
 * Created by Arbindra on 3/9/2016.
 */
@Integration
@Stepwise
class AccountResourceFunctionalSpec extends GebSpec {

    @Shared
    def accountId

    RESTClient restClient

    def setup() {
        restClient = new RESTClient(baseUrl)
    }

    def 'Create a new account'() {
        given:
        def account = new Account(handlename: 'darthVader', name: 'Mr. Darth Vader', password: 'd8RTHV8der1', email: 'darth_vader@gmail.com')
        def json = account as JSON

        when:
        def resp = restClient.post(path: '/accounts', body: json as String, requestContentType: 'application/json')

        then:
        resp.status == 201
        resp.data

        when:
        accountId = resp.data.id

        then:
        accountId
        resp.data.handlename == 'darthVader'
        resp.data.name == 'Mr. Darth Vader'
        resp.data.email == 'darth_vader@gmail.com'
    }

    def 'Retrieve all accounts'() {
        when:
        def resp = restClient.get(path: '/accounts')

        then:
        resp.status == 200
        resp.data.size() == 1
    }

    def 'Retrieve the account by accountId'() {
        when:
        def resp = restClient.get(path: "/accounts/${accountId}")

        then:
        resp.status == 200
        resp.data.id == accountId
        resp.data.handlename == 'darthVader'
        resp.data.name == 'Mr. Darth Vader'
        resp.data.email == 'darth_vader@gmail.com'
    }

    def 'Retrieve the account by handleName'() {
        when:
        def resp = restClient.get(path: '/accounts',
                query: ['handle': 'darthVader'])

        then:
        resp.status == 200
        resp.data.id == accountId
        resp.data.handlename == 'darthVader'
        resp.data.name == 'Mr. Darth Vader'
        resp.data.email == 'darth_vader@gmail.com'
    }

    def 'Account creation with invalid input returns error: #description'() {
        given:
        def account = new Account(handlename: handlename, name: name, password: password, email: email)
        def json = account as JSON

        when:
        def resp = restClient.post(path: '/accounts', body: json as String, requestContentType: 'application/json')

        then: 'Verify that error code 422: Unprocessable Entity is thrown'
        HttpResponseException error = thrown(HttpResponseException)
        error.statusCode == 422

        where:
        description              | handlename      | name             | password        | email
        'Empty Handlename'       | ''              | 'Obi-Wan Kenobi' | 'O89Axafwdlkji' | 'Obi_Wan_Kenobi@nsa.gov'
        'Empty Name'             | '@ObiWanKenobi' | ''               | 'O89Axafwdlkji' | 'Obi_Wan_Kenobi@nsa.gov'
        'Invalid Password'       | '@ObiWanKenobi' | 'Obi-Wan Kenobi' | 'x'             | 'Obi_Wan_Kenobi@nsa.gov'
        'Empty Password'         | '@ObiWanKenobi' | 'Obi-Wan Kenobi' | ''              | 'Obi_Wan_Kenobi@nsa.gov'
        'Invalid email'          | '@ObiWanKenobi' | 'Obi-Wan Kenobi' | 'O89Axafwdlkji' | 'Obi_Wan_Kenobi'
        'Multiple invalid input' | ''              | 'Obi-Wan Kenobi' | 'O'             | 'Obi_Wan_Kenobi'
    }
}
