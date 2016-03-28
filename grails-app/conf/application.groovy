grails.plugin.springsecurity.filterChain.chainMap = [

        //Stateless chain
        [
                pattern: '/**',
                filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
        ]

]

grails.plugin.springsecurity.rest.token.storage.useGorm = true
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = 'mytwtr.AuthenticationToken'
grails.plugin.springsecurity.rest.token.validation.useBearerToken = false
grails.plugin.springsecurity.rest.token.validation.headerName = 'X-Auth-Token'

grails.plugin.springsecurity.userLookup.userDomainClassName = 'mytwtr.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'mytwtr.UserRole'
grails.plugin.springsecurity.authority.className = 'mytwtr.Role'

grails.plugin.springsecurity.securityConfigType = 'InterceptUrlMap'
grails.plugin.springsecurity.interceptUrlMap = [
        [
                [pattern: '/**', access: ['ROLE_READ']]
        ]
]