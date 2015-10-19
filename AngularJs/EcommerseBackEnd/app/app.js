'use strict';

// Declare app level module which depends on views, and components
var app=angular.module('myApp', [
  'ngRoute',
  'myApp.Login',
  'myApp.Views',
  'myApp.ProductDetails',
  'myApp.Orders',
  'myApp.version',
  'Parse'
]);
    /*app.run(function(Parse) {
    return Parse.auth.resumeSession();
    });*/
    /*app.config(['$routeProvider',function(ParseProvider,$routeProvider) {*/
   app.config(['$logProvider','$routeProvider',function($logProvider,$routeProvider) {
      /* $routeProvider.when('/Login',
            {
                templateUrl:'app/Login/Login.html',
                controller:'MyFirstController'
            });*/
       $logProvider.debugEnabled(true);
   $routeProvider.otherwise({redirectTo: '/Login'});
    }]);
