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
        $('form').find("h4", id: "handlename").text() == "Account: ironman"
        $('form').find("h4", id: "name").text() == "Name: Tony Stark"
    }

    def 'R0. Allow for the logged in user to post a new message'() {

        when: 'When the user types in a tweet and hits the post Message button'
        $("#account-form input[id=postMessageId]").value("This is my first tweet!")
        $('div').find("button", id: "postMessageButton").click()
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

    def 'R2. AngularValidation to validate the message before posting'() {

        when: 'When the user enters a tweet longer than 40 characters the post button is disabled and a message is shown'
        $("#account-form input[id=postMessageId]").value("12345678911234567892123456789312345678941")
        sleep(1000)

        then: 'Verify the postMessageButton is disabled and error message is shown'
        $('div').find("button", id: "postMessageButton").@disabled == 'true'
        $('form').find("div", id: "length40Error").isDisplayed()

        when: 'The input field for posting message is empty, the postMessageButton is disabled'
        $("#account-form input[id=postMessageId]").value("")

        then: 'the postMessageButton is disabled'
        $('div').find("button", id: "postMessageButton").@disabled == 'true'
    }

    def 'The postMessage input field is disabled when in a different account page'() {
        when: 'the logged in user goes to another accounts user details page'
        $("#account-form input[id=searchTokenValue]").value("superman")
        $('form').find("button", id: "goButton").click()
        waitFor(5, 1) {
            $('form').find("h3", id: "searchedMessageResults").allElements()[0].getText().contains("Message #1 Superman")
        }

        $('form').find(".links_main").find("a", 0).click()
        waitFor(5, 1) {
            $('form').find("h4", id: "handlename").text() == "Account: superman"
        }

        then: 'the postMessage input field does not exist'
        !$('#postMessageField').displayed
    }

    def 'R4. Follow and Following buttons are displayed on another users page (using angular directive)'(){

        when: 'ironman searched for democrat in message search box'
        $("#account-form input[id=searchTokenValue]").value("democrat")
        $('form').find("button", id: "goButton").click()
        waitFor(5, 1) {
            $('form').find("h3", id: "searchedMessageResults").allElements()[0].getText().contains("democrat")
        }

        then: 'Verify all 15 messages with word democrat appears'
        $('form').find("h3", id: "searchedMessageResults").allElements().size() == 1
        $('form').find("h3", id: "searchedMessageResults").allElements()[0].getText().contains("democrat")

        when: 'Account handle for hclinton is clicked'
        $('form').find("a", id: "accountHandleLink").click()
        waitFor(5, 1) {
            $('form').find("button", id: "followButton").displayed
        }

        then: 'Hillary Clintons page is loaded and followButton is there, he is being followed by 0 accounts'
        $('form').find("button", id: "followButton").displayed
        $('form').find("h4", id: "followers-count").text() == "Followers: 0"

        when: 'Follow button is clicked, expect the followButton to turn into followingButton'
        $('form').find("button", id: "followButton").click()
        waitFor(5, 1) {
            $('form').find("button", id: "followingButton").displayed
        }

        then: 'Followers count for Hillary Clinton is now 1'
        $('form').find("h4", id: "followers-count").text() == "Followers: 1"
    }

    def "R5. Use AngularJS date filter to format the date of a message in the feed with MMM DD"(){
        when: 'Verify the date format is mmm dd format date will be current month and date - should already be displayed'
        String mmmDD_Date = new String(new Date().toString()).substring(4,6)

        then:
        $('form').find("small", id: "messageDateCreated").text().contains(mmmDD_Date)

    }
}
