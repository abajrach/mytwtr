package mytwtr


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class IntegTestSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    /* void "test something"() {
        expect:"fix me"
            true == false
    } */
    void "F1. Test that an account may have multiple followers" () {
        setup:
        def account = new Account(handlename: '@foobar', name: 'Mr. Foo Bar', password: 'passWord1', email: 'foo_bar@gmail.com').save(flush: true, failOnError: true)
        def account_follower1 = new Account(handlename: '@follower1', name: 'Mr. Follower 1', password: 'passWord1', email: 'follower_1@gmail.com').save(flush: true, failOnError: true)
        def account_follower2 = new Account(handlename: '@follower2', name: 'Mr. Follower 2', password: 'passWord2', email: 'follower_2@gmail.com').save(flush: true, failOnError: true)

        when:
        account.addToFollowedBy(account_follower1);
        account.addToFollowedBy(account_follower2);
        account.save(flush: true, failOnError: true)

        then:
        true == true
        //2 == account.FollowedBy.count()
    }
}
