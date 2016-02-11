package mytwtr

class Account {

    String handlename
    String name
    String password
    String email

    Date dateCreated
    //static hasMany = [followers: Account, following: Account, statuses: Status]  // or [followed: Account]
    static hasMany = [followedBy: Account]

    static constraints = {
        handlename blank: false, unique: true
        name blank: false
        password blank: false,
                matches: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,16}$/ // Matches at least 1 uppercase, 1 lowercase
                                                                            // 1 digit, size must be between 8..16
        email nullable: false, email: true, unique: true
    }
}
