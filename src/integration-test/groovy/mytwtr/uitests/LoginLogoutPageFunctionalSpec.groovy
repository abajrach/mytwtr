package mytwtr.uitests

import geb.spock.GebSpec
import grails.test.mixin.integration.Integration

@Integration
class LoginLogoutPageFunctionalSpec extends GebSpec {

    def 'L1: When not logged in, route user to the login screen'() {
        when: 'the root page of the app is accessed'
        go '/'
        waitFor(5, 1) {
            getCurrentUrl().endsWith('#/login')
        }

        then: 'Login Page displays login to your account message'
        $(".login-header").text() == "Login Into Your Account"
        !$('form').find("div", id: "account-details").displayed
        !$('form').find("div", id: "center-box").displayed
        !$('form').find("div", id: "update-info-button").displayed

    }

    def "L2: Login screen allows a user to enter username and password to gain access"() {
        when: 'the root page of the app is accessed'
        go '/'

        then: 'Login Page displays login to your account message'
        $(".login-header").text() == "Login Into Your Account"

        when: 'the user enters correct username, password and hits submit button'
        $("#login-form input[id=username-field]").value("a")
        $("#login-form input[id=password-field]").value("a")
        $("#login-form input[id=submit-button]").click()
        waitFor(5, 1) {
            getCurrentUrl().endsWith('#/account/')
        }

        then: 'verify account details, recent messages and update,follow buttons are there'
        $('form').find("h4", id: "handlename").text() == "Account: a"
        $('form').find("h4", id: "name").text() == "Name: Mr. Admin"
        $('form').find("h4", id: "followers-count").text() == "Followers: 3"
        $('form').find("h4", id: "following-count").text() == "Following: 2"
        $('form').find("h4", id: "email").text() == "Email: i_am_admin@gmail.com"
        $('form').find("h4", id: "dateCreated").text()

        $('form').find("button", id: "update-info-button").displayed
        !$('form').find("button", id: "followButton").displayed
        !$('form').find("button", id: "followingButton").displayed

        $('form').find("h3", id: "loggedInUserMessages").allElements().size() == 50
        $('form').find("h3", id: "loggedInUserMessages").allElements()[0].getText().contains("Message #50 admin was partying")

        $('form').find("small", id: "messageDateCreated").displayed

    }

    def "N3: Logout - clicking this should bring you to the login screen and provide a helpful message"() {

        when: 'the user clicks logout button'
        $('form').find("p", id: "logoutButton").click()

        then: "Helpful message, login page is displayed, account details is not displayed"
        $('div').find("h3").text() == "We are sorry to see you go :("
        $(".login-header").text() == "Login Into Your Account"
        getCurrentUrl().endsWith('#/login')
        !$('form').find("div", id: "account-details").displayed
        !$('form').find("div", id: "center-box").displayed
        !$('form').find("div", id: "update-info-button").displayed
    }

    def "L3: Invalid login will be rejected with an error message"() {
        when: 'the root page of the app is accessed'
        go '/'

        then: 'Login Page displays login to your account message'
        $(".login-header").text() == "Login Into Your Account"

        when: 'Wrong credentials are entered'
        $("#login-form input[id=username-field]").value("bad")
        $("#login-form input[id=password-field]").value("login")
        $("#login-form input[id=submit-button]").click()
        waitFor(5, 1) {
            getCurrentUrl().endsWith('#/login')
        }

        then: 'alert warning with appropriate message appears'
        $('div').find("p", id: "alert-warning-popup").displayed
        $('div').find("p", id: "alert-warning-popup").text() == "Incorrect Credentials!"

        when: 'the x on the alert warning is pressed, the alert message is dismissed'
        $('div').find("button", id: "alert-dismiss-button").click()

        then: 'alert message disappears'
        !$('div').find("p", id: "alert-warning-popup").displayed

    }

}