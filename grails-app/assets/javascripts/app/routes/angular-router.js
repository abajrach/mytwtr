app.config(function ($routeProvider) {
    $routeProvider
        .when('/home', {
            templateUrl: '/app/home.html',
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
        .when('/accounts', {
            templateUrl: '/app/accounts.html',
            controller: 'accountController'
        })
        .otherwise({
            redirectTo: '/home'
        });
});