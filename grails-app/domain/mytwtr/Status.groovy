package mytwtr

import mytwtr.Account

class Status { /* or Message */

    String status_message // Needs to be 40 characters or less
    //Account author
    Date dateCreated


    static belongsTo = [account: Account] // Test: Verify status is deleted when the account is deleted (cascades)

    static constraints = {
        status_message size: 1..40
    }
}
