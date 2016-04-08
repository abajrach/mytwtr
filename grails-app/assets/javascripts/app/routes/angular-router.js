app.config(function ($routeProvider) {
    $routeProvider
        .when('/login', {
            templateUrl: '/app/login.html',
            controller: 'mainController'
        })
        .when('/about', {
            templateUrl: '/app/about.html',
            controller: 'aboutController'
        })
        .when('/logout', {
            templateUrl: '/app/login.html',
            controller: 'mainController'
        })
        .when('/account', {
            templateUrl: '/app/account.html',
            controller: 'accountController'
        })
        .otherwise({
            redirectTo: '/login'
        });
});