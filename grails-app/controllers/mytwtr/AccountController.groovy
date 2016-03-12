package mytwtr

import grails.converters.JSON
import grails.rest.RestfulController


class AccountController extends RestfulController<Account>{
    static responseFormats = ['json', 'xml']

    AccountController() {
        super(Account)
    }

    /* This returns appropriate error message if specified account is not found */
    def show() {

        def account = Account.get(params.id)
        if (account) {
            render account as JSON
        } else {
            response.status = 404
        }
    }

    /* Called when GET on /accounts URI without Ids */
    def index() {

        if(params.handle) {

            def account = Account.findByHandlename(params.handle)
            if(account) {
                render account as JSON
            }
            else {
                response.status = 404 // @Todo: Fix the notFound.gsp issue. Return status is 404 but the page is empty
            }
        }
        else {
            // Return all the accounts
            render Account.getAll() as JSON
        }
    }

    /**
     * @Todo: Requires more testing
     */
    def follow() {
        def account = Account.get(params.id)
        def accountToFollow = Account.get(params.followAccountId)
        account.addToFollowing(accountToFollow).save()
        accountToFollow.addToFollowedBy(account).save()
        render account.following as JSON                // Displays what accounts self is following
    }

    /**
     * @Todo: Requires more testing
     */
    def unfollow() {
        def account = Account.get(params.id)
        def accountToUnFollow = Account.get(params.unfollowAccountId)
        account.removeFromFollowing(accountToUnFollow).save()
        accountToUnFollow.removeFromFollowedBy(account).save()
        render account.following as JSON               // Displays what accounts self is following
    }

    /*
    @Todo: Add the limit and offset logic implemented for messages to this endpoint.
    @Todo: Figure out if we need to get rid of Followers and FollowedBy from the output
     */
    def getFollowers() {
        def account = Account.get(params.id)
        def followers = account.followedBy
       // followers.each { followerAccount ->
        //    render followerAccount as JSON
       // }
        render followers as JSON

    }
}
