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

    $scope.login = function () {

        var credentials = new Object();
        credentials.usrName = $scope.usrName;
        credentials.pwd = $scope.pwd;

        loginService.authenticate(credentials)
            .then(function (response) {
                    console.log("success");
                    console.log(response.status);
                    loginService.setToken(response.data.access_token);
                    $location.path("/account");
                    //console.log(response.data);
                },
                function (response) {
                    console.log("failed");
                    console.log(response.status);
                });
    }
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