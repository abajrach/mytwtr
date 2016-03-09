package mytwtr

import geb.spock.GebSpec
import grails.converters.JSON
import grails.test.mixin.integration.Integration
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

    def 'Create a new account' () {
        given:
        def account = new Account(handlename: '@darthVader', name: 'Mr. Darth Vader', password: 'd8RTHV8der1', email: 'darth_vader@gmail.com')
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
        resp.data.handlename == '@darthVader'
        resp.data.name == 'Mr. Darth Vader'
        resp.data.email == 'darth_vader@gmail.com'
    }
}
