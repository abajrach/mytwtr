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
        /*if(account == null) {
            //render status:404
            response.status = 404
        }
        else {
            //return [account: account]
            render account as JSON
        } */

        /*if(params.id) {

            println "handle = ${handle}"
            def account = Account.get(params.id)
            render account as JSON
        }
        else {
            log.info "haha"
        }
        */
        if(${handle}) {
            println "handle = ${handle}"
            def account = Account.findByHandlename(params.handle)
            render account as JSON
        }
        else if(params.id) {
            println "id = params.id"
            def account = Account.get(params.id)
            render account as JSON
        }


    }

    /* protected Account queryForAccount(Serializable id) {
        def accountId = params.accountId
        Account.where {
            id == id && Account.id == accountId
        }.find()
    }
*/
    //def index() { }

    /*def create() {
        respond new Account(params)
    } */
}
