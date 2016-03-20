package mytwtr

import geb.spock.GebSpec
import groovyx.net.http.RESTClient
import grails.converters.JSON
import grails.test.mixin.integration.Integration
import spock.lang.Stepwise
import spock.lang.Unroll

@Integration
@Stepwise
@Unroll
class FollowAccountFunctionalSpec extends GebSpec {

    RESTClient restClient

    def setup() {
        restClient = new RESTClient(baseUrl)
    }

    def 'Create set of accounts to test Account Following functionality #description'() {
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
        'Account Id 5' | '@Aragorn'      | 'Aragorn'       | 'Ar8gorn12'      | 'aragorn@gmail.com'
        'Account Id 6' | '@Gandalf'      | 'Gandalf'       | 'G8d8Lf2134'     | 'Gandalf@gmail.com'
    }

    def 'Create some test messages'() {
        when:
        def account_resp = restClient.get(path: "/accounts/6")

        then:
        account_resp.status == 200

        when:
        def responses = []
        (1..8).each { i->
            def message = ([status_message: "status message ${i}", account: account_resp.data.id] as JSON) as String
            responses << restClient.post(path: '/accounts/6/messages', body: message as String, requestContentType: 'application/json')
        }

        then: 'Verify that all accounts are successfully created'
        responses.each { response ->
            assert response.status == 201
            assert response.data
        }
    }

    def 'F1. Verify an account can follow other accounts #description'() {

        when: 'Accounts 1-5 is following 6 and account 6 is following back 1'
        def resp = restClient.post(path: "/accounts/${selfId}/follow/${followId}")

        then:
        resp.status == 200
        resp.data.followedBy.id.toString().contains(selfId.toString())

        where: 'Accounts 1-5 follows 6. Account 6 follows 1'
        description                   | selfId | followId
        'Account 1 follows Account 6' | 1      | 6
        'Account 2 follows Account 6' | 2      | 6
        'Account 3 follows Account 6' | 3      | 6
        'Account 4 follows Account 6' | 4      | 6
        'Account 5 follows Account 6' | 5      | 6
        'Account 6 follows Account 1' | 6      | 1
    }

    def 'F2. Verify the total number of Followers and Following are correct. #description'() {
        when: 'Getting all followers for account Id 6'
        def resp = restClient.get(path: "/accounts/" + accountId)

        then: 'Account Id is following 1 and has 5 followers'
        resp.status == 200
        resp.data.following.size() == 1
        resp.data.followedBy.size() == expctedFollowedByCount

        where:
        description    | accountId | expctedFollowedByCount
        'Account Id 1' | 1         | 1
        'Account Id 2' | 2         | 0
        'Account Id 3' | 3         | 0
        'Account Id 4' | 4         | 0
        'Account Id 5' | 5         | 0
        'Account Id 6' | 6         | 5
    }

    def 'F3. Retrieve the followers for an account and ensure they are correct.'() {
        when: 'Getting all the followers, no query parameters used'
        def resp = restClient.get(path: "/accounts/6/getfollowers")

        then: 'Account 6 has 5 followers. First one being accound Id 1, since the list is sorted in asc order'
        resp.status == 200

        when:
        List followers = resp.data as List

        then:
        followers.size() == 5
        followers[0].id == 1

        when: 'Getting first 3 followers for account Id 6'
        resp = restClient.get(path: "/accounts/6/getfollowers", query: ['max': 3])

        then: 'Returns only 3 followers'
        resp.status == 200

        when:
        followers = resp.data as List

        then:
        followers.size() == 3
        followers[0].id == 1
        followers[1].id == 2
        followers[2].id == 3

        when: 'Skips first 2 followers for account Id 6'
        resp = restClient.get(path: "/accounts/6/getfollowers", query: ['offset': 2])

        then: 'Returns remaining 3 followers for account Id 6'
        resp.status == 200

        when:
        followers = resp.data as List

        then:
        followers.size() == 3
        followers[0].id == 3
        followers[1].id == 4
        followers[2].id == 5

        when: 'Show only 3 followers and skip first 2 followers for account Id 6'
        resp = restClient.get(path: "/accounts/6/getfollowers", query: ['max': 3, 'offset': 2])

        then: 'Returns 3 followers, skips first 2 followers for account Id 6'
        resp.status == 200

        when:
        followers = resp.data as List

        then:
        followers.size() == 3
        followers[0].id == 3
        followers[1].id == 4
        followers[2].id == 5
    }

    def 'F4. Show news feed for the account being followed'() {
        when: 'Getting all the messages in news feed for followed account, no query parameters used'
        def resp = restClient.get(path: "/accounts/1/shownewsfeed")

        then: 'Account Id 1 is following 6 who has 8 messages in total. First one being message Id 8, since the list is sorted in desc order by creation date'
        resp.status == 200

        when:
        def feed = resp.data as List

        then:
        feed.size() == 8
        feed[0].id == 8

        when: 'Getting only 4 news feed'
        resp = restClient.get(path: "/accounts/1/shownewsfeed", query: ['limit': 4])

        then: 'Returns only 4 messages'
        resp.status == 200

        when:
        def messages = resp.data as List

        then:
        messages.size() == 4
        messages[0].id == 8
        messages[1].id == 7
        messages[2].id == 6
        messages[3].id == 5

        when: 'Get the dateCreated for message'
        resp = restClient.get(path: "/accounts/6/messages/6")

        then:
        resp.status == 200
        def message_id_6_dateCreated = resp.data.dateCreated

        when: 'Search for news feed using filter for dateCreated'
        resp = restClient.get(path: "/accounts/1/shownewsfeed", query: ['from': message_id_6_dateCreated])

        then:
        resp.status == 200
        resp.data
    }

    def 'Verify that an account can unfollow another account'() {
        when: 'Make allow account Id 1 unfollow 6'
        def resp = restClient.post(path: "/accounts/1/unfollow/6")

        then:
        resp.status == 200

        when: 'Get the account Id 1 details'
        resp = restClient.get(path: "/accounts/1")

        then: 'Verify that account Id 1 is not following account Id 6 anymore'
        !resp.data.following.id.toString().contains("6")

        when: 'Get the account Id 6 details'
        resp = restClient.get(path: "/accounts/6")

        then: 'Verify that account Id 6 is not being followed by account Id 1 anymore'
        !resp.data.followedBy.id.toString().contains("1")
    }


}
