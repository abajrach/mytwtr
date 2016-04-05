package mytwtr

class Message {

    String status_message
    Date dateCreated

    static belongsTo = [account: Account]

    static constraints = {
        status_message size: 1..40, blank: false
    }
}
