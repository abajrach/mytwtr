package mytwtr.uitests

import geb.spock.GebSpec
import grails.test.mixin.integration.Integration
import spock.lang.Ignore

@Integration
class UserDetailRequirementTests extends GebSpec {

    def "U1: Users detail page is displayed with all their postings"() {
        when:
        go '/'

        then: 'Login Page displays login to your account message'
        waitFor(5, 0.1) {
            getCurrentUrl().endsWith('#/login')
        }
        $(".login-header").text() == "Login Into Your Account"

        when:
        $("#login-form input[id=username-field]").value("a")
        $("#login-form input[id=password-field]").value("a")
        $("#login-form input[id=submit-button]").click()
        waitFor(5, 1) {
            getCurrentUrl().endsWith('#/account/')
        }

        then:
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
        $('form').find("h3", id: "loggedInUserMessages").allElements()[49].getText().contains("Message #1 admin was partying")

        $('form').find("small", id: "messageDateCreated").displayed

    }
    def "U3: When the logged in user is following the detail user, the detail page will display a message or icon indicating this."() {
        when:
        $("#account-form input[id=searchTokenValue]").value("superman ")
        $('form').find("button", id: "goButton").click()
        waitFor(5, 1) {
            def tempString = $('form').find("h3", id: "searchedMessageResults").allElements()[0].getText()
            $('form').find("h3", id: "searchedMessageResults").allElements()[0].getText().contains("Message #1 Superman")
        }
        $('form').find(".links_main").find("a",0).click()

        then:
        sleep(1000)
        $('form').find("h4", id: "handlename").text() == "Account: superman"
        $('form').find("h4", id: "name").text() == "Name: Superman, Chick Magnet"
        $('form').find("h4", id: "followers-count").text() == "Followers: 1"
        $('form').find("h4", id: "following-count").text() == "Following: 1"

        !$('form').find("button", id: "update-info-button").displayed
        !$('form').find("button", id: "followButton").displayed
        $('form').find("button", id: "followingButton").displayed
    }

    def "U2: User’s detail page will provide a way for the logged in user to follow the detail user"() {
        when:
        go '/'

        then: 'Login Page displays login to your account message'
        waitFor(5, 0.1) {
            getCurrentUrl().endsWith('#/login')
        }
        $(".login-header").text() == "Login Into Your Account"

        when: 'User bsanders logs in'
        $("#login-form input[id=username-field]").value("bsanders")
        $("#login-form input[id=password-field]").value("p")
        $("#login-form input[id=submit-button]").click()
        waitFor(5, 1) {
            $('form').find("h4", id: "name").text() == "Name: Bernie Sanders"
        }

        then: 'Logged in Users detail page is displayed'
        $('form').find("h4", id: "handlename").text() == "Account: bsanders"
        $('form').find("h4", id: "name").text() == "Name: Bernie Sanders"
        $('form').find("h4", id: "followers-count").text() == "Followers: 0"
        $('form').find("h4", id: "following-count").text() == "Following: 0"
        $('form').find("h4", id: "email").text() == "Email: bern@gmail.com"

        $('form').find("button", id: "update-info-button").displayed
        !$('form').find("button", id: "followButton").displayed
        !$('form').find("button", id: "followingButton").displayed

        when: 'bsanders searched for batman in message search box'
        $("#account-form input[id=searchTokenValue]").value("batman")
        $('form').find("button", id: "goButton").click()
        waitFor(5, 1) {
            $('form').find("h3", id: "searchedMessageResults").allElements()[0].getText().contains("Message #1 Batman doesn't sleep")
        }

        then: 'Verify all 15 messages with word batman appears'
        $('form').find("h3", id: "searchedMessageResults").allElements().size() == 15
        $('form').find("h3", id: "searchedMessageResults").allElements()[14].getText().contains("Message #15 Batman doesn't sleep")
        $('form').find("button", id: "update-info-button").displayed
        !$('form').find("button", id: "followButton").displayed
        !$('form').find("button", id: "followingButton").displayed

        when: 'Account handle for batman is clicked'
        $('form').find("a", id: "accountHandleLink").click()
        waitFor(5, 1) {
            $('form').find("button", id: "followButton").displayed
        }

        then: 'Batmans page is loaded and followButton is there, he is being followed by 0 accounts'
        $('form').find("button", id: "followButton").displayed
        $('form').find("h4", id: "followers-count").text() == "Followers: 0"

        when: 'Follow button is clicked, expect the followButton to turn into followingButton'
        $('form').find("button", id: "followButton").click()
        waitFor(5, 1) {
            $('form').find("button", id: "followingButton").displayed
        }

        then: 'Followers count for batman is now 1'
        $('form').find("h4", id: "followers-count").text() == "Followers: 1"
    }



    def "U4: User can update their own detail page logged in"() {
        when:
        go '/'

        then: 'Login Page displays login to your account message'
        waitFor(5, 0.1) {
            getCurrentUrl().endsWith('#/login')
        }
        $(".login-header").text() == "Login Into Your Account"

        when:
        $("#login-form input[id=username-field]").value("dtrump")
        $("#login-form input[id=password-field]").value("p")
        $("#login-form input[id=submit-button]").click()
        waitFor(5, 1) {
            $('form').find("h4", id: "name").text() == "Name: Donald J. Drumpf"
        }

        then:
        $('form').find("h4", id: "handlename").text() == "Account: dtrump"
        $('form').find("h4", id: "name").text() == "Name: Donald J. Drumpf"
        $('form').find("h4", id: "email").text() == "Email: don@gmail.com"

        $('form').find("button", id: "update-info-button").displayed
        !$('form').find("button", id: "followButton").displayed
        !$('form').find("button", id: "followingButton").displayed

        when:
        $('form').find("button", id: "update-info-button").click()
        waitFor(5, 1) {
            $('form').find("h4", class: "modal-title").text() == "Update My Info"
        }

        then: 'Update My Info screen is displayed'
        $('form').find("h4", class: "modal-title").text() == "Update My Info"

        when:
        $('form').find(id: "update-info-name-field").value("dtrump2")
        $('form').find(id: "update-info-email-field").value("dtrump2@gmail.com")
        $('form').find("button", id: "update-info-submit-button").click()
        waitFor(5, 1) {
            $('form').find("h4", id: "name").text() == "Name: dtrump2"
        }

        then: 'name and email are updated'
        $('form').find("h4", id: "name").text() == "Name: dtrump2"
        $('form').find("h4", id: "email").text() == "Email: dtrump2@gmail.com"

    }
}
