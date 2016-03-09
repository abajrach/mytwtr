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

        //println "Hello"
        //println 'inside show ' + params.handle
        def account = Account.get(params.id)
        if (account) {
            render account as JSON
        } else {
            response.status = 404
        }
    }

    def index() {

        if(params.handle) {
            println 'inside index '+ params.handle
            def account = Account.findByHandlename(params.handle)
            if(account) {
                render account as JSON
            }
            else {
                response.status = 404 // @Todo: Fix the notFound.gsp issue. Return status is 404 but the page is empty
            }
        }
        else {
            // @Todo: Return all the accounts
        }

    }
}
