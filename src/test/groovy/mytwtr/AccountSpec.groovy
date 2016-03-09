package mytwtr

import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
@TestFor(Account)
@TestMixin(DomainClassUnitTestMixin)
class AccountSpec extends Specification {

    def 'A1. Saving an account with a valid handle, email, password and name will succeed' () {
        given:
        def accountsBeforeSave = Account.count()
        def account = new Account(handlename: '@foobar', name: 'Mr. Foo Bar', password: password, email: 'foo_bar@gmail.com')

        when:
        account.save(flush: true)

        then:
        account.id
        !account.hasErrors()
        Account.count() == accountsBeforeSave + 1
        Account.get(account.id)

        where:
        description     | password
        'valid pwd'     | 'pSDsfsfsFD2'
        '8 characters'  | 'a'*3 + 'X'*3 + '99'

    }

    def 'A2. Saving an account with required fields missing will fail. data-driven unit test: #description'() {
        given:
        def accountsBeforeSave = Account.count()
        def account = new Account(handlename: handlename, name: name, password: password, email: email)

        when:
        account.save(flush: true)

        then: "Verify account creation passes/fails for different required fields missing"
        !account.id
        account.hasErrors()
        Account.count() == accountsBeforeSave

        where:
        description            | handlename  | name     | password      | email
        'handle name mising'   | null        | 'John B' | 'John1234556' | 'john_b@gmail.com'
        'Account name missing' | '@johnB'    | null     | 'John1234452' | 'john_b@gmail.com'
        'Password missing'     | '@johnB'    | 'John B' | null          | 'john_b@gmail.com'
        'email missing'        | '@johnB'    | 'John B' | 'John1234452' | null
    }

    def 'A3. invalid passwords fail to save:  #description '() {
        given:
        def accountsBeforeSave = Account.count()
        def account = new Account(handlename: '@foobar', name: 'Mr. Foo Bar', password: password, email: 'foo_bar@gmail.com')

        when:
        account.save(flush: true)

        then: "check if the account creation succeeds or fails for different password values"
        !account.id
        account.hasErrors()
        account.errors.getFieldError('password')
        Account.count() == accountsBeforeSave

        where:
        description           | password
        'has special chars'   |  'aDFS23$32Ddfs'
        'lower case missing'  |  'SDFWEFWWEF23'
        'upper case missing'  |  'sdfwerwcw324'
        'digit missing'       |  'pihoiKJILKJLd'
        'less than 8 chars'   |  'saFJ9'
        'seven characters'    |  'a'*3 + 'X'*3 + '9'
        'more than 16 chars'  |  'aB3456789012345678'

    }

}
