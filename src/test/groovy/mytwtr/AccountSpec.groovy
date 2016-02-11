package mytwtr

import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Account)
@TestMixin(DomainClassUnitTestMixin)
class AccountSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    def 'A1. Saving an account with a valid handle, email, password and name will succeed' () {
        given:
        def account = new Account(handlename: '@foobar', name: 'Mr. Foo Bar', password: 'passWord1', email: 'foo_bar@gmail.com')

        when:
        account.save()

        then:
        account.id
        !account.hasErrors()
    }

    @Unroll('A2. test account creation with required fields missing #description ')
    def 'A2. Saving an account with required fields missing will fail. data-driven unit test'() {
        given:
        def account = new Account(handlename: handlename, name: name, password: password, email: email)

        when:
        account.save()

        then:
        //!account.id
        account.hasErrors() == account_creation_failed

        where:
        description            | handlename  | name   | password    | email                   | account_creation_failed
        'handle name mising'   | null        | 'John B' | 'John1234556' | 'john_b@gmail.com'  | true
        'Account name missing' | '@johnB'    | null     | 'John1234452' | 'john_b@gmail.com'  | true
        'Password missing'     | '@johnB'    | 'John B' | null          | 'john_b@gmail.com'  | true
        'email missing'        | '@johnB'    | 'John B' | 'John1234452' | null                | true
    }

    @Unroll('A3. test password all constraints #description ')
    def 'A3. Saving an account with an invalid password will fail. data-driven unit test'() {
        given:
        def account = new Account(handlename: '@foobar', name: 'Mr. Foo Bar', password: password, email: 'foo_bar@gmail.com')

        when:
        account.save()

        then:
        //account.id
        account.hasErrors() == account_creation_failed

        where:
        description           | password                    | account_creation_failed
        'valid pwd'           |  'pSDsfsfsFD2'              | false
        'has special chars'   |  'aDFS23$32Ddfs'            | true
        'lower case missing'  |  'SDFWEFWWEF23'             | true
        'upper case missing'  |  'sdfwerwcw324'             | true
        'digit missing'       |  'pihoiKJILKJLd'            | true
        'less than 8 chars'   |  'saFJ9'                    | true
        'more than 16 chars'  |  'aB3456789012345678'       | true


    }

}
