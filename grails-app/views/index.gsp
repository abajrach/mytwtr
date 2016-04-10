<!doctype html>
<html ng-app="app">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <asset:javascript src="application.js"/>
    <asset:stylesheet src="application.css"/>
</head>

<body ng-controller="loginLogoutController">
    <nav class="navbar navbar-inverse navbar-static-top">
        <div class="container">
            <div class="navbar-header">
                <a href="#" class="navbar-brand">My Awesome Twitter!!!</a>
            </div>


            <ul class="nav navbar-nav navbar-right">

                <li ng-class="{ active: isActive('/home')}"><a href="#home">Home</a></li>
                <li ng-class="{ active: isActive('/about')}"><a href="#about">About</a></li>
                <li ng-class="{ active: isActive('/logout')}">
                    <p class="navbar-btn" ng-click="logout()">
                        <a href="#logout" class="btn btn-link">Logout</a>
                    </p>
                </li>
            </ul>
        </div>
    </nav>

    <div ng-view></div>

    <div class="navbar navbar-default navbar-fixed-bottom">
        <div class="container">
            <p class="navbar-text pull-left">Site Built by Arbindra</p>
            <a class="navbar-btn btn-danger btn pull-right">This twitter is awesome!!!</a>
        </div>
    </div>

</body>
</html>

<!-- <input type="submit" class="btn btn-block btn-lg btn-primary" value="Login" ng-click="login()"> -->
