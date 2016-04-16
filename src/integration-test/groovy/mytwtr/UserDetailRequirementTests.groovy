package mytwtr

import geb.spock.GebSpec
import grails.test.mixin.integration.Integration

@Integration
class UserDetailRequirementTests extends GebSpec {
    def "U1: Users detail page is displayed with all their postings"() {

    }

    def "U2: Other users detail page will allow logged in user to follow postings"() {

    }

    def "U3: User info is displayed when logged in"() {
        when:
        go '/'

        then: 'Login Page displays login to your account message'
        $(".login-header").text() == "Login Into Your Account"

        when:
        $("#login-form input[id=username-field]").value("dtrump")
        $("#login-form input[id=password-field]").value("p")
        $("#login-form input[id=submit-button]").click()
        waitFor(5,1) {
            $('form').find("h4", id:"handlename").text() == "Account: dtrump"
        }

        then:
        $('form').find("h4", id:"handlename").text() == "Account: dtrump"
        $('form').find("h4", id:"name").text() == "Name: Donald J. Drumpf"
        $('form').find("h4", id:"followers-count").text() == "Followers: 0"
        $('form').find("h4", id:"following-count").text() == "Following: 0"
        $('form').find("h4", id:"email").text() == "Email: don@gmail.com"
        $('form').find("h4", id:"dateCreated").text()

        $('form').find("button", id:"udpate-info-button").displayed
        !$('form').find("button", id:"followButton").displayed
        !$('form').find("button", id:"followingButton").displayed
    }
    def "U4: User can update their own detail page logged in"() {
        when:
        go '/'

        then: 'Login Page displays login to your account message'
        $(".login-header").text() == "Login Into Your Account"

        when:
        $("#login-form input[id=username-field]").value("dtrump")
        $("#login-form input[id=password-field]").value("p")
        $("#login-form input[id=submit-button]").click()
        waitFor(5,1) {
            $('form').find("h4", id:"name").text() == "Name: Donald J. Drumpf"
        }

        then:
        $('form').find("h4", id:"handlename").text() == "Account: dtrump"
        $('form').find("h4", id:"name").text() == "Name: Donald J. Drumpf"
        $('form').find("h4", id:"email").text() == "Email: don@gmail.com"

        $('form').find("button", id:"udpate-info-button").displayed
        !$('form').find("button", id:"followButton").displayed
        !$('form').find("button", id:"followingButton").displayed

        when:
        $('form').find("button", id:"udpate-info-button").click()

        then: 'Update My Info screen is displayed'
        $('form').find("h4", class:"modal-title").text() == "Update My Info"

        when:
        $('form').find(id:"update-info-name-field").value("dtrump2")
        $('form').find(id:"update-info-email-field").value("dtrump2@gmail.com")
        $('form').find("button", id:"update-info-submit-button").click()
        waitFor(5,1) {
            getCurrentUrl().endsWith('#/account/')
        }

        then: 'name and email are updated'
        $('form').find("h4", id:"name").text() == "Name: dtrump2"
        $('form').find("h4", id:"email").text() == "Email: dtrump2@gmail.com"

    }
}
