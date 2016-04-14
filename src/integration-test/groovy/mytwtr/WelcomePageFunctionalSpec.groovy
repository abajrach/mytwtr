package mytwtr

import geb.spock.GebSpec
import grails.test.mixin.integration.Integration
import spock.lang.Ignore

@Integration
class WelcomePageFunctionalSpec extends GebSpec {

    // @Todo: Need more validation
    def 'L1: When not logged in, route user to the login screen'() {
        when:
        go '/'

        then: 'Login Page displays login to your account message'
        $(".login-header").text() == "Login Into Your Account"

        //and: 'Angular generated test displayed properly'
        //$('h2').first().text() == 'Hello Stranger'
    }

    def "L2: Login screen allows a user to enter username and password to gain access"(){
        when:
        go '/'

        then: 'Login Page displays login to your account message'
        $(".login-header").text() == "Login Into Your Account"

        when:
        $("#login-form input[id=username-field]").value("a")
        $("#login-form input[id=password-field]").value("a")
        $("#login-form input[id=submit-button]").click()
        waitFor(5,1) {
            getCurrentUrl().endsWith('#/account/')
        }

        then:
        $('form').find("h4", id:"handlename").text() == "Account: a"
        $('form').find("h4", id:"name").text() == "Name: Mr. Admin"
        $('form').find("h4", id:"followers-count").text() == "Followers: 3"
        $('form').find("h4", id:"following-count").text() == "Following: 2"
        $('form').find("h4", id:"email").text() == "Email: i_am_admin@gmail.com"
        $('form').find("h4", id:"dateCreated").text()
    }
}