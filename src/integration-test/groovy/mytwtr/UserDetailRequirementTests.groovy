package mytwtr

import geb.spock.GebSpec
import grails.test.mixin.integration.Integration

@Integration
class UserDetailRequirementTests extends GebSpec {
    def "U1: Users detail page is displayed with all their postings"() {
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

        $('form').find("button", id:"udpate-info-button").displayed
        !$('form').find("button", id:"followButton").displayed
        !$('form').find("button", id:"followingButton").displayed

        $('form').find("h3", id:"loggedInUserMessages").allElements().size() == 50
        $('form').find("h3", id:"loggedInUserMessages").allElements()[0].getText() == "Message #50 admin was partying"

        $('form').find("h3", id:"messageDateCreated").displayed

    }

    def "U2: Other users detail page will allow logged in user to follow"() {
        when:
        go '/'

        then: 'Login Page displays login to your account message'
        $(".login-header").text() == "Login Into Your Account"

        when:
        $("#login-form input[id=username-field]").value("bsanders")
        $("#login-form input[id=password-field]").value("p")
        $("#login-form input[id=submit-button]").click()
        waitFor(5,1) {
            $('form').find("h4", id:"name").text() == "Name: Bernie Sanders"
        }

        then:
        $('form').find("h4", id:"handlename").text() == "Account: bsanders"
        $('form').find("h4", id:"name").text() == "Name: Bernie Sanders"
        $('form').find("h4", id:"followers-count").text() == "Followers: 0"
        $('form').find("h4", id:"following-count").text() == "Following: 0"
        $('form').find("h4", id:"email").text() == "Email: bern@gmail.com"

        $('form').find("button", id:"udpate-info-button").displayed
        !$('form').find("button", id:"followButton").displayed
        !$('form').find("button", id:"followingButton").displayed

        when:
        $("#account-form input[id=searchTokenValue]").value("batman")
        $('form').find("button", id:"goButton").click()
        waitFor(5,1) {
            $('form').find("h3", id:"searchedMessageResults").allElements()[0].getText() == "Message #1 Batman doesn't sleep Posted by batman"
        }

        then:
        $('form').find("button", id:"udpate-info-button").displayed
        !$('form').find("button", id:"followButton").displayed
        !$('form').find("button", id:"followingButton").displayed

        when:
        $('form').find("a", id:"accountHandleLink").click()
        waitFor(5,1) {
            $('form').find("button", id:"followButton").displayed
        }

        then: 'Clicked on posted by link Batman info is displayed'
        $('form').find("button", id:"followButton").displayed
        $('form').find("h4", id:"followers-count").text() == "Followers: 0"

        when:
        $('form').find("button", id:"followButton").click()
        waitFor(5,1) {
            $('form').find("button", id:"followingButton").displayed
        }

        then: 'Following button is displayed'
        $('form').find("button", id:"followingButton").displayed
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
        waitFor(5,1) {
            $('form').find("h4", class:"modal-title").text() == "Update My Info"
        }

        then: 'Update My Info screen is displayed'
        $('form').find("h4", class:"modal-title").text() == "Update My Info"

        when:
        $('form').find(id:"update-info-name-field").value("dtrump2")
        $('form').find(id:"update-info-email-field").value("dtrump2@gmail.com")
        $('form').find("button", id:"update-info-submit-button").click()
        waitFor(5,1) {
            $('form').find("h4", id:"name").text() == "Name: dtrump2"
        }

        then: 'name and email are updated'
        $('form').find("h4", id:"name").text() == "Name: dtrump2"
        $('form').find("h4", id:"email").text() == "Email: dtrump2@gmail.com"

    }
}
