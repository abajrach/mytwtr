package mytwtr.uitests

import geb.spock.GebSpec
import grails.test.mixin.integration.Integration
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import spock.lang.Ignore

@Integration

class SearchRequirementTests extends GebSpec {

    def "S1 and S3: Provide a search box for finding messages and contents show posting user"() {
        when:
        go '/'

        then: 'S1 - Login Page displays login to your account message'
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

        then: 'S1 - Screen is displayed.'
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

        when: 'a search token of batman is entered in the search box'
        $("#account-form input[id=searchTokenValue]").value("batman")
        $('form').find("button", id: "goButton").click()
        waitFor(5, 1) {
            def tempString = $('form').find("h3", id: "searchedMessageResults").allElements()[0].getText()
            $('form').find("h3", id: "searchedMessageResults").allElements()[0].getText().contains("Message #1 Batman doesn't sleep")
        }

        then: 'S1 - Searched records for batman are displayed.'
        $('form').find("h3", id: "searchedMessageResults").allElements().size() == 15
        $('form').find("h3", id: "searchedMessageResults").allElements()[0].getText().contains("Message #1 Batman doesn't sleep")
        $('form').find("h3", id: "searchedMessageResults").allElements()[14].getText().contains("Message #15 Batman doesn't sleep")
        $('div').find("small", id: "postedByLabel").text() == "Posted by batman"
    }

    def "S4: Clicking on posting user will route to the users detail page"() {
        when:
        $('form').find(".links_main").find("a",0).click()

        then:
        sleep(1000)
        $('form').find("h4", id: "handlename").text() == "Account: batman"
        $('form').find("h4", id: "name").text() == "Name: Batman, Dark Knight"
        $('form').find("h4", id: "followers-count").text() == "Followers: 0"
        $('form').find("h4", id: "following-count").text() == "Following: 1"
        $('form').find("h4", id: "email").text() == "Email: batman@aol.com"
        $('form').find("h4", id: "dateCreated").text()

        !$('form').find("button", id: "update-info-button").displayed
        $('form').find("button", id: "followButton").displayed
        !$('form').find("button", id: "followingButton").displayed

        $('form').find("h3", id: "loggedInUserMessages").allElements().size() == 15
        $('form').find("h3", id: "loggedInUserMessages").allElements()[0].getText().contains("Message #15 Batman doesn't sleep")

        $('form').find("small", id: "messageDateCreated").displayed
    }

    def "S2: Display matching results in a scrollable window"() {
        when:
        go '/'

        then: 'S2 - Login Page displays login to your account message'
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

        then: 'S2 - Screen is displayed.'
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

        when: 'The page is scrolled to the bottom of the screen'
        WebElement element = driver.findElement(By.id("EndOfMessage"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);

        then: 'Check if the EndOfMessage tag is displayed after scrolled down'
        $('form').find("h3", id: "EndOfMessage").displayed
    }

}
