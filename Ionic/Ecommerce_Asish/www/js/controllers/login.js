/**
 * Created by AsishS on 21-10-2015.
 */
Parse.initialize("qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX", "OgDuxM1kk6T9QCKqEOCDg0Bq0UPBkfz5FP9T6raS");
var app = angular.module('app.controllers.login', ['ionic'])
app.controller('loginCtrl', function ($scope) {
  $scope.user = {};
  console.log("Welcome");
  $scope.logIn = function () {

    //alert($scope.user.userName);
    var name = $scope.user.userName;

    var password = $scope.user.userPassword;
    //alert($scope.user.userPassword);
    Parse.User.logIn(name, password, {
      success: function (user) {
        console.log("Successfully Logged In...");
        $scope.user.isLoggedIn = true;
        location.href = "#/categories";
      }, error: function (user, error) {
        console.log(error.message);
      }
    });
  };
  $scope.logOut = function (event) {
    console.log("Logging out...");
    Parse.User.logOut();
    $scope.user.isLoggedIn = false;
    $scope.user.userName = null;
    $scope.user.userPassword = null;
  };
})
