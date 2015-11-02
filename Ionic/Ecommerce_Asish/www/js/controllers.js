/*Parse.initialize("qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX", "OgDuxM1kk6T9QCKqEOCDg0Bq0UPBkfz5FP9T6raS");*/
angular.module('app.controllers', ['ionic'])
  .constant('FORECASTIO_KEY', 'add-your-forecastio-key-here')
/*.controller('loginCtrl',['$scope',function($scope) {*/
 /* .controller('loginCtrl',function($scope) {
    $scope.user = {};
    console.log("Welcome");
    $scope.logIn=function() {

      alert($scope.user.userName);
      var name=$scope.user.userName;

      var password=$scope.user.userPassword;
      alert($scope.user.userPassword);
      Parse.User.logIn(name, password, {
        success: function (user) {
          console.log("Successfully Logged In...");
          location.href = "#/categories";
        }, error: function (user, error) {
          console.log(error.message);
        }
      });
    };
    $scope.logOut=function(event) {
      console.log("Logging out...");
      Parse.User.logOut();
    };
  /!*}])*!/
  })
*/
.controller('signupCtrl', function($scope) {

})

/*.controller('categoriesCtrl', function($scope) {

})*/

/*
.controller('productsCtrl', function($scope) {

})
*/

.controller('ordersCtrl', function($scope) {

})

.controller('checkOutCtrl', function($scope) {

})



