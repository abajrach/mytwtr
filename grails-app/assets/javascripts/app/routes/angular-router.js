angular.module('app')

    .config(function ($routeProvider) {
        $routeProvider
            .when('/login', {
                templateUrl: '/app/login.html',
                controller: 'loginLogoutController'
            })
            .when('/home', {
                templateUrl: '/app/login.html',
                controller: 'loginLogoutController'
            })
            .when('/about', {
                templateUrl: '/app/about.html',
                controller: 'aboutController'
            })
            .when('/logout', {
                templateUrl: '/app/login.html',
                controller: 'loginLogoutController'
            })
            .when('/account/', {
                templateUrl: '/app/account.html',
                controller: 'accountDetailsController'
            })
            .when('/account/:handle?', {
                templateUrl: '/app/account.html',
                controller: 'accountDetailsController'
            })
            .otherwise({
                redirectTo: '/login'
            })
      })

      // Protect all routes other than login
      .run(function ($rootScope, $location, loginLogoutService) {
        $rootScope.$on('$routeChangeStart', function (event, next) {
          if (next.$$route && next.$$route.originalPath != '/login') {
            if (!loginLogoutService.getCurrentUser()) {
              $location.path('/login');
            }
          }
        });
      });
