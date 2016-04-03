app.controller('headerController', function ($scope, $location) {
    $scope.message = 'I manage the header, meaning the nav tabs';
    $scope.isActive = function (viewLocation) {
        return viewLocation == $location.path();
    };
});

/**
 * change this to loginController
 */
app.controller('mainController', function ($scope, $location, $http, loginService) {

    //var usrNamePassword = new Object();
    //usrNamePassword.usrName = $scope.usrName;
    //usrNamePassword.pwd = $scope.pwd;

    //$scope.login = loginService.authenticate(usrNamePassword);
    $scope.login = function() {
        console.log("I am inside mainController in controller code");
        loginService.authenticate($scope.usrName, $scope.pwd);
    }

/*    $scope.login = function () {

        console.log($scope.usrName);
        console.log($scope.pwd);

        $http.defaults.headers.post["Content-Type"] = "application/json";

        $http({
            url: '/api/login',
            method: "POST",
            data: {'username': $scope.usrName, 'password': $scope.pwd}
        })
            .then(function successCallback(response) {
                    // success
                    console.log("success");
                    console.log(response.status);
                    console.log(response.data);
                },
                function errorCallback(response) {
                    // failed
                    console.log("failed");
                    console.log(response.status);
                });
    }*/
});

app.controller('aboutController', function ($scope) {
    $scope.message = 'MSSE 5199 Class Project';
});

app.controller('contactController', function ($scope) {
    $scope.message = 'Arbindra Bajracharya, Jason Nordlund';
});

app.controller('attendeeController', function ($scope, $location, $routeParams, attendeeService) {
    $scope.attendee = {};
    $scope.mode = 'Add';
    if ('edit' == $routeParams.action) {
        $scope.mode = 'Edit';
        var id = $routeParams.id;
        var attendees = attendeeService.getAttendees();
        for (i = 0; i < attendees.length; i++) {
            if (attendees[i].id == id) {
                $scope.attendee = attendees[i];
            }
        }
    }

    $scope.saveCurrentAttendee = function () {
        if ($scope.attendee.first && $scope.attendee.last) {
            if ($scope.attendee.id) {
                attendeeService.updateAttendee($scope.attendee);
            } else {
                attendeeService.addAttendee($scope.attendee);
            }
            attendeeService.attendee = {};
            $location.path("/home");
        }
    };

    $scope.message = 'Wire up controller in html (Not really good practice)';

    $scope.return = function () {
        $location.path("/home");
    };
});