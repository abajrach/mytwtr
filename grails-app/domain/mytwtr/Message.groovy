package mytwtr

import grails.rest.Resource
import mytwtr.Account
@Resource(uri='/messages',formats = ['json','xml'])
class Message {

    String status_message
    Date dateCreated


    static belongsTo = [account: Account]

    static constraints = {
        status_message size: 1..40, blank: false
    }
}
