package mytwtr

class Account {

    String handlename
    String name
    String password
    String email
    Date dateCreated

    /**
     * Spring Security Related stuff
     */
    transient springSecurityService
    boolean enabled = true
    boolean accountExpired = false
    boolean accountLocked = false
    boolean passwordExpired = false

    Set<Role> getAuthorities() {
        AccountRole.findAllByAccount(this)*.role
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ?
                springSecurityService.encodePassword(password) :
                password
    }

    static hasMany = [followedBy: Account, following: Account, messages: Message]

    static constraints = {
        handlename blank: false, unique: true, nullable: false
        name blank: false, nullable: false
        //password blank: false,
        //      matches: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,16}$/ // Matches at least 1 uppercase, 1 lowercase
        // 1 digit, size must be between 8..16
        email nullable: false, email: true, unique: true
    }

}
