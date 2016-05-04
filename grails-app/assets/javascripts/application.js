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
//= require angular-bootstrap/ui-bootstrap-tpls
//= require ../bower/angular-resource/angular-resource.js
//= require ../bower/angular-route/angular-route.js
//= require ../bower/angular-webstorage/angular-webstorage.js
//= require ../bower/foo/bar.js
//= require_self
//= require_tree app

angular.module('app', ['ngRoute', 'ngResource', 'webStorageModule', 'ui.bootstrap']);
