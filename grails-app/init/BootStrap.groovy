import mytwtr.Account
import mytwtr.Role
import mytwtr.User
import mytwtr.UserRole

class BootStrap {

    def init = { servletContext ->
        def admin = new User(username: 'admin', password: 'root').save(flush: true, failOnError: true)
        def role = new Role(authority: 'ROLE_READ').save(flush: true, failOnError: true)
        new UserRole(user: admin, role: role).save(flush: true, failOnError: true)

        def account1 = new Account(handlename: '@abajrach', name: 'Mr. Arbindra B.', password: paSSword123, email: 'arbindra_b@gmail.com').save(flush: true, failOnError: true)
        def account2 = new Account(handlename: '@darthVader', name: 'Mr. Darth Vader', password: paSSword123, email: 'DarthV@gmail.com').save(flush: true, failOnError: true)
        def account3 = new Account(handlename: '@Batman', name: 'Batman, Dark Knight', password: paSSword123, email: 'batman@aol.com').save(flush: true, failOnError: true)
        def account4 = new Account(handlename: '@Superman', name: 'Superman, Chick Magnet', password: paSSword123, email: 'superman@gmail.com').save(flush: true, failOnError: true)



    }

    def destroy = {
    }
}
