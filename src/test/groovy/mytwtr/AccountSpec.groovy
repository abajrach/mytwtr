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
/*
    void "test something"() {
        expect:"fix me"
            true == false
    }
*/
    def 'A1. Saving an account with a valid handle, email, password and name will succeed' () {
        given:
        def account = new Account(handlename: '@foobar', name: 'Mr. Foo Bar', password: 'pSDsfsfsFD2', email: 'foo_bar@gmail.com')

        when:
        account.save()

        then:
        account.id
        !account.hasErrors()
    }

    @Unroll('A3. test password all constraints #password ')
    def 'A3. Saving an account with an invalid password will fail. data-driven unit test'() {
        given:
        def account = new Account(handlename: '@foobar', name: 'Mr. Foo Bar', password: val, email: 'foo_bar@gmail.com')

        when:
        account.save()

        then:
        account.id
        !account.hasErrors()

        where:
        error   | password  | val

    }
}
