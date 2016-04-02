app.config(function ($routeProvider) {
    $routeProvider
        .when('/home', {
            templateUrl: 'angular-router/partials/home.html',
            controller: 'mainController'
        })
        .when('/about', {
            templateUrl: 'angular-router/partials/about.html',
            controller: 'aboutController'
        })
        //.when('/contact', {
        //    templateUrl: 'angular-router/partials/contact.html',
        //    controller: 'contactController'
        //})
        //.when('/attendee/:action?/:id?', {
        //    templateUrl: 'angular-router/partials/attendee.html'
        //})
        .when('/accounts', {
            templateUrl: 'angular-router/partials/accounts',
            controller: 'accountController'
        })
        .otherwise({
            redirectTo: '/home'
        });
});