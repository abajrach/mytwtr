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

    void "F1. Verify that an account may have multiple followers" () {
        setup: 'setup accounts for testing'
        def account = new Account(handlename: '@foobar', name: 'Mr. Foo Bar', password: 'passWord1', email: 'foo_bar@gmail.com').save(flush: true, failOnError: true)
        def account_follower1 = new Account(handlename: '@follower1', name: 'Mr. Follower 1', password: 'passWord1', email: 'follower_1@gmail.com').save(flush: true, failOnError: true)
        def account_follower2 = new Account(handlename: '@follower2', name: 'Mr. Follower 2', password: 'passWord2', email: 'follower_2@gmail.com').save(flush: true, failOnError: true)

        when: 'make to accounts follow primary account'
        account.addToFollowedBy(account_follower1);
        account.addToFollowedBy(account_follower2);
        account.save(flush: true, failOnError: true)
        def account_verify = Account.get(account.id)

        then: 'verify that the primary account has 2 followers and they are correct ones'
        account_verify.followedBy.size() == 2
        account_follower1 == account_verify.followedBy.find { it.name == 'Mr. Follower 1' }
        account_follower2 == account_verify.followedBy.find { it.name == 'Mr. Follower 2' }
    }

    void "F2. Verify two accounts may follow each other" () {
        setup:
        def account_1 = new Account(handlename: '@account_1', name: 'Mr Account 1', password: 'passWord1', email: 'account_1@gmail.com').save(flush: true, failOnError: true)
        def account_2 = new Account(handlename: '@account_2', name: 'Mr Account 2', password: 'passWord2', email: 'account_2@gmail.com').save(flush: true, failOnError: true)

        when:
        account_1.addToFollowedBy(account_2).save(flush: true, failOnError: true)
        account_2.addToFollowedBy(account_1).save(flush: true, failOnError: true)
        def account_1_verify = Account.get(account_1.id)
        def account_2_verify = Account.get(account_2.id)

        then: 'verify that each account has 1 follower and they are correct ones'
        account_1_verify.followedBy.size() == 1
        account_2_verify.followedBy.size() == 1
        account_1 == account_2_verify.followedBy.find { it.name == 'Mr Account 1'}
        account_2 == account_1_verify.followedBy.find { it.name == 'Mr Account 2'}
    }

    @Unroll "A4. Saving account with a non-unique email or handle address must fail (integration test) #description" () {
        given:
        def account = new Account(handlename: handlename, name: name, password: password, email: email)

        when:
        account.save(flush: true /*, failOnError: true*/)

        then:
        account.hasErrors() == account_creation_failed
        account.id
       /* if (account_creation_failed == false) {
            account.get(account.id).handlename == handlename
            account.get(account.id).name == name
            account.get(account.id).email == email
        } */

        where:
        description                 | handlename | name     | password      | email               | account_creation_failed
        'Good handle address'       | 'hello'  | 'John B' | 'John1234556' | 'testA4@gmail.com'  | false
        'Duplicate handle address'  | 'hello'  | 'John B' | 'John1234452' | 'testA42@gmail.com' | true
       // 'Duplicate email address'   | '@testA42' | 'John B' | 'John1234452' | 'testA4@gmail.com'  | true

    }
}
