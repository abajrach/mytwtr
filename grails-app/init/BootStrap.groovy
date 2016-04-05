import mytwtr.Account
import mytwtr.AccountRole
import mytwtr.Role

class BootStrap {

    def init = { servletContext ->
        def adminAccount = new Account(handlename: 'admin', name: 'Mr. Admin', password: 'd8RTHV8der1', email: 'i_am_admin@gmail.com').save(flush: true, failOnError: true)
        def role = new Role(authority: 'ROLE_READ').save(flush: true, failOnError: true)
        new AccountRole(account: adminAccount, role: role).save(flush: true, failOnError: true)

      /*  def account1 = new Account(handlename: '@abajrach', name: 'Mr. Arbindra B.', password: paSSword123, email: 'arbindra_b@gmail.com').save(flush: true, failOnError: true)
        def account2 = new Account(handlename: '@darthVader', name: 'Mr. Darth Vader', password: paSSword123, email: 'DarthV@gmail.com').save(flush: true, failOnError: true)
        def account3 = new Account(handlename: '@Batman', name: 'Batman, Dark Knight', password: paSSword123, email: 'batman@aol.com').save(flush: true, failOnError: true)
        def account4 = new Account(handlename: '@Superman', name: 'Superman, Chick Magnet', password: paSSword123, email: 'superman@gmail.com').save(flush: true, failOnError: true)
        */
    }

    def destroy = {
    }
}
