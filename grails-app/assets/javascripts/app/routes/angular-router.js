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
        .when('/contact', {
            templateUrl: '/app/contact.html',
            controller: 'contactController'
        })
        //.when('/attendee/:action?/:id?', {
        //    templateUrl: 'angular-router/partials/attendee.html'
        //})
        .when('/account', {
            templateUrl: '/app/account.html',
            controller: 'accountController'
        })
        .otherwise({
            redirectTo: '/login'
        });
});