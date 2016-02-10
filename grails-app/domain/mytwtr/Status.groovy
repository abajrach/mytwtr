package mytwtr

import mytwtr.Account

class Status { /* or Message */

    String message // Needs to be 40 characters or less
    //Account author
    Date dateCreated
    static belongsTo = [Account] // Test: Verify status is deleted when the account is deleted (cascades)

    static constraints = {
        message size: 1..40
    }
}
