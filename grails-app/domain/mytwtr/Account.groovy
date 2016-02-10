package mytwtr

class Account {

    String handlename
    String name
    String password
    String email

    Date dateCreated
    static hasMany = [followed: Account, status: Status]


    static constraints = {
        handlename blank: false, unique: true
        name blank: false
        password blank: false, unique: true, size: 8..16
                matches: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,16}$/ // Matches at least 1 uppercase, 1 lowercase
                                                                            // 1 digit, size must be between 8..16
        email nullable: false, email: true, unique: true
    }
}
