// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= encoding UTF-8
//= require jquery-2.2.0.min.js
//= require ../bower/bootstrap/bootstrap.js
//= require ../bower/angular/angular.js
//= require ../bower/angular-route/angular-route.js
//= require ../bower/foo/bar.js
//= require_self
//= require_tree app

/*if (typeof jQuery !== 'undefined') {
    (function($) {
        $('#spinner').ajaxStart(function() {
            $(this).fadeIn();
        }).ajaxStop(function() {
            $(this).fadeOut();
        });
    })(jQuery);
}*/

var app = angular.module('app', ['ngRoute']);
