package mytwtr

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
@TestFor(Message)
@Mock(Account)
@TestMixin(DomainClassUnitTestMixin)
class MessageSpec extends Specification {

    def 'M1. Saving a message with a valid account and message text will succeed' () {
        given:
        def test_account = new Account(handlename: '@foobarsfs', name: 'Mr. Foo Bar', password: 'passWord1', email: 'foo_bar1@gmail.com')
        def test_status = new Message(status_message: '#Grails is fun :)', account: test_account)

        when:
        test_status.save(flush: true)

        then:
        test_status.id
        !test_status.hasErrors()
    }

    def 'M2. Creating a message/status fails with invalid values data-driven unit test: #description'() {
        given:
        def messagesBeforeSaving = Message.count()
        def account = new Account(handlename: '@foobar', name: 'Mr. Foo Bar', password: 'passWord1', email: 'foo_bar@gmail.com')
        def message = new Message(status_message: status_message, account: account)

        when:
        message.save(flush: true, failOnError: false)

        then:
        !message.id
        message.hasErrors()
        message.errors.getFieldError('status_message')
        Message.count() == messagesBeforeSaving
        where:
        description                       | status_message
        'message text null'               | null
        'message text blank'              | ''
        'message text more than 40 chars' | 'abcdefghijklmnopqrstuvwxyz12345678912345677889afa;sdfjasl;f'
    }

}
