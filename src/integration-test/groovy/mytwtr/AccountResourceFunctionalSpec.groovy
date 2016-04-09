package mytwtr

import geb.spock.GebSpec
import grails.converters.JSON
import grails.test.mixin.integration.Integration
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll

@Ignore
@Integration
@Stepwise
class AccountResourceFunctionalSpec extends GebSpec {

    @Shared
    def token

    @Shared
    def accountId

    RESTClient restClient

    def setup() {
        restClient = new RESTClient(baseUrl)
    }

    def 'calling accounts endpoint without token is forbidden'() {
        when:
        restClient.get(path: '/accounts')

        then:
        HttpResponseException problem = thrown(HttpResponseException)
        problem.statusCode == 403
        problem.message.contains('Forbidden')
    }

    @Ignore
    def 'passing a valid username and password generates a token'() {
        setup:
        def authentication = ([username: 'admin', password: 'root'] as JSON) as String

        when:
        def response = restClient.post(path: '/api/login', body: authentication, requestContentType: 'application/json')

        then:
        response.status == 200
        response.data.username == 'admin'
        response.data.roles == ['ROLE_READ']
        //noinspection GroovyDoubleNegation
        !!(token = response.data.access_token)
    }

    def 'using token access to accounts endpoint allowed'() {
        when:
        def response = restClient.get(path: '/accounts', headers: ['X-Auth-Token': token])

        then:
        response.status == 200
        response.data.size() == 0
    }

    def 'Create a new account'() {
        given:
        def account = new Account(handlename: 'darthVader', name: 'Mr. Darth Vader', password: 'd8RTHV8der1', email: 'darth_vader@gmail.com')
        def json = account as JSON

        when:
        def resp = restClient.post(path: '/accounts', body: json as String, requestContentType: 'application/json',  headers: ['X-Auth-Token': token])

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
        def resp = restClient.get(path: '/accounts', headers: ['X-Auth-Token': token])

        then:
        resp.status == 200
        resp.data.size() == 1
    }

    def 'Retrieve the account by accountId'() {
        when:
        def resp = restClient.get(path: "/accounts/${accountId}", headers: ['X-Auth-Token': token])

        then:
        resp.status == 200
        resp.data.id == accountId
        resp.data.handlename == 'darthVader'
        resp.data.name == 'Mr. Darth Vader'
        resp.data.email == 'darth_vader@gmail.com'
    }

    def 'Retrieve the account by handleName with format /accounts?handlename=darthVader'() {
        when:
        def resp = restClient.get(path: '/accounts',
                query: ['handle': 'darthVader'], headers: ['X-Auth-Token': token])

        then:
        resp.status == 200
        resp.data.id == accountId
        resp.data.handlename == 'darthVader'
        resp.data.name == 'Mr. Darth Vader'
        resp.data.email == 'darth_vader@gmail.com'
    }

    def 'Retrieve the account by handleName with format /accounts/handle=darthVader'() {
        when:
        def resp = restClient.get(path: '/accounts/handle=darthVader', headers: ['X-Auth-Token': token])

        then:
        resp.status == 200
        resp.data.id == accountId
        resp.data.handlename == 'darthVader'
        resp.data.name == 'Mr. Darth Vader'
        resp.data.email == 'darth_vader@gmail.com'
    }


    @Unroll
    def 'Account creation with invalid input returns error: #description'() {
        given:
        def account = new Account(handlename: handlename, name: name, password: password, email: email)
        def json = account as JSON

        when:
        restClient.post(path: '/accounts', body: json as String, requestContentType: 'application/json', headers: ['X-Auth-Token': token])

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
