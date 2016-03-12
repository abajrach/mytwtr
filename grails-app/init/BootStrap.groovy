import mytwtr.Account

class BootStrap {

    def init = { servletContext ->

        def foobar = new Account(handlename: 'foobar', name: 'Mr. Foo Bar', password: 'pSDsfsfsFD2', email: 'foo_bar@gmail.com').save()
        def darthVader = new Account(handlename: 'darthVader', name: 'Mr. Darth Vader', password: 'd8RTHV8der1', email: 'darth_vader@gmail.com').save()
        def darthVader2 = new Account(handlename: 'darthVader2', name: 'Mr. Darth Vader', password: 'd8RTHV8der1', email: 'darth_vader2@gmail.com').save()
        def darthVader3 = new Account(handlename: 'darthVader3', name: 'Mr. Darth Vader', password: 'd8RTHV8der1', email: 'darth_vader3@gmail.com').save()
        def darthVader4 = new Account(handlename: 'darthVader4', name: 'Mr. Darth Vader', password: 'd8RTHV8der1', email: 'darth_vader4@gmail.com').save()

        def kkardashian = new Account(handlename: 'kkardashian', name: 'Kim Kardashian', password: 'kimkdshAn1', email: 'kim_kardashaian@gmail.com').save()

        // both foobar and darthvader following kkardashian
        foobar.addToFollowing(kkardashian).save(flush: true, failOnError: true)
        kkardashian.addToFollowedBy(foobar).save(flush: true, failOnError: true)

        darthVader.addToFollowing(kkardashian).save(flush: true, failOnError: true)
        kkardashian.addToFollowedBy(darthVader).save(flush: true, failOnError: true)

        darthVader2.addToFollowing(kkardashian).save(flush: true, failOnError: true)
        kkardashian.addToFollowedBy(darthVader2).save(flush: true, failOnError: true)

        darthVader3.addToFollowing(kkardashian).save(flush: true, failOnError: true)
        kkardashian.addToFollowedBy(darthVader3).save(flush: true, failOnError: true)

        darthVader4.addToFollowing(kkardashian).save(flush: true, failOnError: true)
        kkardashian.addToFollowedBy(darthVader4).save(flush: true, failOnError: true)


    }
    def destroy = {
    }
}
