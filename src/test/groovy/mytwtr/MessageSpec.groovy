package mytwtr

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Message)
class MessageSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    def 'M1. Saving a message with a valid account and message text will succeed' () {
        given:
        def test_account = new Account(handlename: '@foobar', name: 'Mr. Foo Bar', password: 'passWord1', email: 'foo_bar@gmail.com')
        def test_status = new Message(account: test_account, status_message: '#Grails is fun :)')

        when:
        test_status.save()

        then:
        test_status.id
        !test_status.hasErrors()
    }

    @Unroll('M2. Test message creation with valid fields #description ')
    def 'M2. Creating a message/status fails with invalid values data-driven unit test'() {
        given:
        def test_account = new Account(handlename: '@foobar', name: 'Mr. Foo Bar', password: 'passWord1', email: 'foo_bar@gmail.com')
        def test_status = new Message(account: test_account, status_message: status_message)


        when:
        test_status.save()

        then:
        //account.id
        test_status.hasErrors() == status_creation_failed

        where:
        description                       | status_message                                                | status_creation_failed
        'message text null'               | null                                                          | true
        'message text blank'              | ''                                                            | true
        'message text more than 40 chars' | 'abcdefghijklmnopqrstuvwxyz12345678912345677889afa;sdfjasl;f' | true
        'valid message creation succeeds' | 'Hi #Grails, you are fun :)'                                  | false
    }

}
