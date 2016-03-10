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

    def 'F1. Verify an account can follow other accounts #description'() {

        when:
        def resp = restClient.post(path: "/accounts/" + selfId + "/follow/" + followIds)

        then:
        resp.status == 200
        sleep(100) // @Todo: Figure out how to get rid of this problem!

        where: 'Account 1 and 3 both follow 2. Account 2 follows 3'
        description                   | selfId | followIds
        'Account 1 follows Account 4' | 1      | 4
        'Account 2 follows Account 4' | 2      | 4
        'Account 3 follows Account 4' | 3      | 4
        'Account 4 follows Account 2' | 4      | 2
    }

    def 'F2. Verify the total number of Followers and Following are correct. #description'() {
        when:
        def resp = restClient.get(path: "/accounts/" + accountId)

        then:
        resp.status == 200
        resp.data.following.size() == expectedFollowingCount // @Todo: Check with Mike if this is okay to do
        resp.data.followedBy.size() == expctedFollowedByCount

        where:
        description    | accountId | expectedFollowingCount | expctedFollowedByCount
        'Account Id 1' | 1         | 1                      | 0
        'Account Id 2' | 2         | 1                      | 1
        'Account Id 3' | 3         | 1                      | 0
        'Account Id 4' | 4         | 1                      | 3
    }
/*
    def 'F3. Retrieve the followers for an account and ensure they are correct.'() {
        when:
        def resp = restClient.get(path: "/accounts/4/getfollowers")

        then:
        resp.status == 200
        resp.data.size() == 3
        //resp.data.findByHandlename()
        //Account.findByHandlename("@darthVader") == resp.data.findByHandlename("@darthVader")
        resp.data[1..3].each { it ->
            assert resp.data.find { a -> a.id  == it}.following.size() == 1
        }
    }
*/

}
