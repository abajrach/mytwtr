package mytwtr

import grails.converters.JSON
import grails.rest.RestfulController

import java.text.SimpleDateFormat


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
                                      // @Todo: Set error status message
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
    @Todo: Figure out if we need to get rid of Followers and FollowedBy from the output
     */
    def getFollowers() {

        def accountId = Integer.parseInt(params.id)
        def max       = params.max ? Integer.parseInt(params.max) : 10
        def offset    = params.offset ? Integer.parseInt(params.offset) : 0

        if(Account.get(accountId)) {
            def followers = Account.findAll('from Account acc where acc.id in (:accounts)',
                    [accounts: Account.get(accountId).followedBy.id],
                    [max: max, offset: offset, order: 'asc'])

            render followers as JSON
        }
        else {
            render(status: 404, text: "Specified account with account ID ${accountId} doesn't exists")
        }
    }

    def shownewsfeed() {
        def accountId   = Integer.parseInt(params.id)
        def limit       = params.limit ? Integer.parseInt(params.limit) : 10
        def from        = params.from

        if(Account.get(accountId)) {
            def messageResults = Message.withCriteria {
               'in' ('account', Account.get(accountId).following)
                maxResults(limit)
                order('dateCreated', 'desc')
                if(from) {
                    gte('dateCreated', new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX").parse(from))
                }
            }

            render messageResults as JSON
        }
        else {
            render(status: 404, text: "Specified account with account ID ${accountId} doesn't exists")
        }

    }
}
