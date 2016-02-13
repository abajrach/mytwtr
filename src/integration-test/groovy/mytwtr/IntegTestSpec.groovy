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
        def famous_account = new Account(handlename: '@famous_celebrity', name: 'Mr. Famous', password: 'passWord1', email: 'foo_bar@gmail.com').save(flush: true, failOnError: true)
        def account_follower1 = new Account(handlename: '@follower1', name: 'Mr. Follower 1', password: 'passWord1', email: 'follower_1@gmail.com').save(flush: true, failOnError: true)
        def account_follower2 = new Account(handlename: '@follower2', name: 'Mr. Follower 2', password: 'passWord2', email: 'follower_2@gmail.com').save(flush: true, failOnError: true)

        when: 'make two accounts follow famous_account'
        account_follower1.addToFollowing(famous_account).save(flush: true, failOnError: true)
        account_follower2.addToFollowing(famous_account).save(flush: true, failOnError: true)

        famous_account.addToFollowedBy(account_follower1).save(flush: true, failOnError: true)
        famous_account.addToFollowedBy(account_follower2).save(flush: true, failOnError: true)

        def famous_account_verify = Account.get(famous_account.id)
        def account_follower1_verify = Account.get(account_follower1.id)
        def account_follower2_verify = Account.get(account_follower2.id)

        then: 'verify that the primary account has 2 followers and they are correct ones'
        famous_account_verify.followedBy.size() == 2
        account_follower1_verify == famous_account_verify.followedBy.find { it.name == 'Mr. Follower 1' }
        account_follower2_verify == famous_account_verify.followedBy.find { it.name == 'Mr. Follower 2' }

        account_follower1_verify.following.size() == 1
        famous_account_verify == account_follower1_verify.following.find { it. name == "Mr. Famous"}

        account_follower2_verify.following.size() == 1
        famous_account_verify == account_follower2_verify.following.find { it. name == "Mr. Famous"}
    }

    void "F2. Verify two accounts may follow each other" () {
        setup:
        def account_1 = new Account(handlename: '@account_1', name: 'Mr Account 1', password: 'passWord1', email: 'account_1@gmail.com').save(flush: true, failOnError: true)
        def account_2 = new Account(handlename: '@account_2', name: 'Mr Account 2', password: 'passWord2', email: 'account_2@gmail.com').save(flush: true, failOnError: true)

        when: "account 1 follows account 2 and vice versa"
        account_2.addToFollowing(account_1).save(flush: true, failOnError: true)
        account_1.addToFollowedBy(account_2).save(flush: true, failOnError: true)

        account_1.addToFollowing(account_2).save(flush: true, failOnError: true)
        account_2.addToFollowedBy(account_1).save(flush: true, failOnError: true)

        def account_1_verify = Account.get(account_1.id)
        def account_2_verify = Account.get(account_2.id)

        then: 'verify that each accounts are following each other and they are correct ones'
        account_1_verify.following.size() == 1
        account_1_verify.followedBy.size() == 1

        account_2_verify.following.size() == 1
        account_2_verify.followedBy.size() == 1

        account_1_verify == account_2_verify.followedBy.find { it.name == 'Mr Account 1'}
        account_2_verify == account_1_verify.followedBy.find { it.name == 'Mr Account 2'}

        account_1_verify == account_2_verify.following.find { it.name == 'Mr Account 1'}
        account_2_verify == account_1_verify.following.find { it.name == 'Mr Account 2'}
    }

    @Unroll "A4. Saving account with a non-unique email or handle address must fail (integration test) #description" () {
        setup:
        def account1 = new Account(handlename: handlename1, name: 'John Doe', password: 'John123445', email: email1)
        def account2 = new Account(handlename: handlename2, name: 'Foo Bar', password: 'fooBar12345', email: email2)

        when:
        account1.save(flush: true )
        def account_1_verify = Account.get(account1.id)
        account2.save(flush: true)

        then: "Saving of account2 should fail in both cases (duplicate handle, duplicate email)"

        account1.hasErrors() == false
        account1.errors.errorCount == 0
        account1.id
        Account.get(account_1_verify.id).name == 'John Doe'

        account2.hasErrors() == true
        account2.errors.errorCount == 1
        !account2.id

        where:
        description                      | handlename1   | handlename2   | email1                 | email2
        'Unique handle, duplicate email' | '@jdoe1'      | '@foobar1'    | 'same_email@gmail.com' | 'same_email@gmail.com'
        'Duplicate email address'        | '@dup_handle' | '@dup_handle' | 'john_doe@gmail.com'   | 'foo_bar@gmail.com'

    }
}
