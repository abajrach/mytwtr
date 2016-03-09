package mytwtr

import geb.spock.GebSpec
import groovyx.net.http.RESTClient
import grails.converters.JSON
import grails.test.mixin.integration.Integration
import spock.lang.Stepwise

/**
 * Created by Arbindra on 3/9/2016.
 */
@Integration
@Stepwise
class FollowAccountFunctionalSpec extends GebSpec {

    RESTClient restClient

    def setup() {
        restClient = new RESTClient(baseUrl)
    }

    def 'Create set of accounts to test Account Following functionality'() {
        given:
        def account = new Account(handlename: handlename, name: name, password: password, email: email)
        def json = account as JSON

        when:
        def resp = restClient.post(path: '/accounts', body: json as String, requestContentType: 'application/json')

        then: 'Verify that all accounts are successfully created'
        resp.status == 201
        resp.data

        where:
        description    | handlename      | name            | password         | email
        'Account Id 1' | '@darthVader'   | 'Darth Vader'   | 'd8ArthVader1'   | 'darth.vader@gmail.com'
        'Account Id 2' | '@darthSidious' | 'Darth Sidious' | 'd8ArthSidious1' | 'darth.Sidous@gmail.com'
        'Account Id 3' | '@yoda'         | 'Yoda'          | 'Yoda80012'      | 'yoda@gmail.com'
        'Account Id 4' | '@hanSolo'      | 'Han Solo'      | 'H8nSolo1234'    | 'han.solo@gmail.com'
    }

    def 'Verify an account other accounts'() {

        when:
        def resp = restClient.post(path: "/accounts/1/follow/"+followIds )

        then:
        resp.status == 200

        where:
        description  | followIds
        '1' | 2
       // '2' | 3

    }
}
