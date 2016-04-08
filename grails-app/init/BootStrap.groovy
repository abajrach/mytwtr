import mytwtr.Account
import mytwtr.AccountRole
import mytwtr.Role

class BootStrap {

    def init = { servletContext ->
        def adminAccount = new Account(handlename: 'admin', name: 'Mr. Admin', password: 'root', email: 'i_am_admin@gmail.com').save(flush: true, failOnError: true)
        def role = new Role(authority: 'ROLE_READ').save(flush: true, failOnError: true)
        new AccountRole(account: adminAccount, role: role).save(flush: true, failOnError: true)

        def account1 = new Account(handlename: '@abajrach', name: 'Mr. Arbindra B.', password: 'paSSword123', email: 'arbindra_b@gmail.com').save(flush: true, failOnError: true)
        def account2 = new Account(handlename: '@Batman', name: 'Batman, Dark Knight', password: 'paSSword123', email: 'batman@aol.com').save(flush: true, failOnError: true)
        def account3 = new Account(handlename: '@Superman', name: 'Superman, Chick Magnet', password: 'paSSword123', email: 'superman@gmail.com').save(flush: true, failOnError: true)

        new AccountRole(account: account1, role: role).save(flush: true, failOnError: true)
        new AccountRole(account: account2, role: role).save(flush: true, failOnError: true)
        new AccountRole(account: account3, role: role).save(flush: true, failOnError: true)

        //@Todo: Messages ids don't roll over to 1 for another account. Need fix?
        (1..20).each { id -> adminAccount.addToMessages(status_message: "admin said: Message #$id").save(flush: true) }
        (1..20).each { id -> account1.addToMessages(status_message: "@abajrach said: Message #$id").save(flush: true) }
        (1..15).each { id -> account2.addToMessages(status_message: "@Batman said: Message #$id").save(flush: true) }
        (1..5).each { id -> account3.addToMessages(status_message: "@Superman said: Message #$id").save(flush: true) }

        adminAccount.addToFollowedBy(account1).save(flush: true, failOnError: true)
        adminAccount.addToFollowedBy(account2).save(flush: true, failOnError: true)
        adminAccount.addToFollowedBy(account3).save(flush: true, failOnError: true)

        adminAccount.addToFollowing(account1).save(flush: true, failOnError: true)
    
    }

    def destroy = {
    }
}
