package mytwtr.uitests

import geb.spock.GebSpec
import grails.test.mixin.integration.Integration

/**
 * Created by Arbindra on 5/4/2016.
 */

@Integration
class Assignment4FunctionalSpec extends GebSpec {

    def setup() {
        when: 'the root page of the app is accessed and username/password entered'
        go '/'
        waitFor(5, 0.1) {
            getCurrentUrl().endsWith('#/login')
        }
        $("#login-form input[id=username-field]").value("ironman")
        $("#login-form input[id=password-field]").value("p")
        $("#login-form input[id=submit-button]").click()
        waitFor(5, 1) {
            getCurrentUrl().endsWith('#/account/')
        }

        then: 'Login Page displays login to your account message'
        $(".login-header").text() == "Login Into Your Account"

    }

    def 'R0. Allow for the logged in user to post a new message'() {

        when: 'When the user types in a tweet and hits the post Message button'
        $("#account-form input[id=postMessageId]").value("This is my first tweet!")
        $('div').find("button", id: "postMessageButton").click()
/*        waitFor(5, 1) {
            getCurrentUrl().endsWith('#/account/')
        }*/
        sleep(2000)

        then: 'Verify the newly created tweet gets posted and shows up in the list of tweets/messages'
        $('form').find("h3", id: "loggedInUserMessages").allElements()[0].getText().contains("This is my first tweet!")

    }

    def 'R1. Use a alert control from the Angular UI library to display an info message saying ‘Message Posted!’'() {

        when: 'When the user types in a tweet and hits the post Message button'
        $("#account-form input[id=postMessageId]").value("This is my second tweet!")
        $('div').find("button", id: "postMessageButton").click()
        waitFor(5,1) {
            $('form').find("h3", id: "loggedInUserMessages").allElements()[0].getText().contains("This is my second tweet!")
        }

        then: 'Verify the angular alert saying Message posted is displayed'
        $('#tweetPosted-alert').displayed
        $('#tweetPosted-alert').text() == "×\n" + "Close\n" + "Message Posted!"
        waitFor(5,1) {
            !$('#tweetPosted-alert').displayed
        }

    }
}
