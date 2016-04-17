grails.plugin.springsecurity.filterChain.chainMap = [

        //Stateless chain
        [
                pattern: '/accounts/**',
                filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
        ],
        [
                pattern: '/messages/**',
                filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
        ]

]

grails.plugin.springsecurity.rest.token.storage.useGorm = true
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = 'mytwtr.AuthenticationToken'
grails.plugin.springsecurity.rest.token.validation.useBearerToken = false
grails.plugin.springsecurity.rest.token.validation.headerName = 'X-Auth-Token'

grails.plugin.springsecurity.userLookup.userDomainClassName = 'mytwtr.Account'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'mytwtr.AccountRole'
grails.plugin.springsecurity.authority.className = 'mytwtr.Role'
grails.plugin.springsecurity.userLookup.usernamePropertyName = 'handlename'
grails.plugin.springsecurity.userLookup.passwordPropertyName = 'password'

grails.plugin.springsecurity.securityConfigType = 'InterceptUrlMap'
grails.plugin.springsecurity.interceptUrlMap = [
        [
                [pattern: '/accounts/**', access: ['ROLE_READ']]
        ],
        [
            [pattern: '/messages/**', access: ['ROLE_READ']]
        ]
]