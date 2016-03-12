import mytwtr.Account

class BootStrap {

    def init = { servletContext ->

        def foobar = new Account(handlename: 'foobar', name: 'Mr. Foo Bar', password: 'pSDsfsfsFD2', email: 'foo_bar@gmail.com').save()
        def darthVader = new Account(handlename: 'darthVader', name: 'Mr. Darth Vader', password: 'd8RTHV8der1', email: 'darth_vader@gmail.com').save()
        def kkardashian = new Account(handlename: 'kkardashian', name: 'Kim Kardashian', password: 'kimkdshAn1', email: 'kim_kardashaian@gmail.com').save()

        // both foobar and darthvader following kkardashian
        foobar.addToFollowing(kkardashian).save(flush: true, failOnError: true)
        kkardashian.addToFollowedBy(foobar).save(flush: true, failOnError: true)

        darthVader.addToFollowing(kkardashian).save(flush: true, failOnError: true)
        kkardashian.addToFollowedBy(darthVader).save(flush: true, failOnError: true)
        
    }
    def destroy = {
    }
}
