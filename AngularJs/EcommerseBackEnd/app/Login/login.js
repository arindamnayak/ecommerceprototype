'use strict';

Parse.initialize("qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX", "OgDuxM1kk6T9QCKqEOCDg0Bq0UPBkfz5FP9T6raS");
var app=angular.module('myApp.Login', ['ngRoute']);
app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/Login', {
    templateUrl: 'Login/login.html',
    controller: 'LoginCtrl'
  });
    $routeProvider.when('/Help', {
        templateUrl: 'Help/help.html',
     controller: 'HelpCtrl'
    });
}]);
app.controller('LoginCtrl', ['$scope',function( $scope) {
    console.log("Welcome");

    $scope.logIn=function() {
        /* alert("Signing In...");*/
        var name=$scope.loginName;
        var password=$scope.loginPassword;
        Parse.User.logIn(name, password, {
            success: function (user) {
                console.log("Successfully Logged In...");
                location.href = "#/Views";
            }, error: function (user, error) {
                console.log(error.message);
            }
        });
    };
     $scope.logOut=function(event) {
        console.log("Logging out...");
        Parse.User.logOut();
    };
}]);
app.controller('HelpCtrl', ['$scope',function( $scope) {
    console.log("Opening Help Content File...");
    /* Parse.initialize("qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX", "OgDuxM1kk6T9QCKqEOCDg0Bq0UPBkfz5FP9T6raS");
     $scope.logOut=function(event) {
        console.log("Logging out...");
        Parse.User.logOut();
    };*/
}]);