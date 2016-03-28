import mytwtr.Role
import mytwtr.User
import mytwtr.UserRole

class BootStrap {

    def init = { servletContext ->
        def admin = new User(username: 'admin', password: 'r00t!').save(flush: true, failOnError: true)
        def role = new Role(authority: 'ROLE_READ').save(flush: true, failOnError: true)
        new UserRole(user: admin, role: role).save(flush: true, failOnError: true)
    }

    def destroy = {
    }
}
