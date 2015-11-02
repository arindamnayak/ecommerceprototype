/**
 * Created by AsishS on 21-10-2015.
 */
Parse.initialize("qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX", "OgDuxM1kk6T9QCKqEOCDg0Bq0UPBkfz5FP9T6raS");
var app = angular.module('app.controllers.cartDetails', ['ionic'])
app.controller('cartCtrl',function ($scope,$rootScope) {
  console.log("Inside cart")
  init();
  $scope.refresh= function() {
    init();
    console.log("Initilizing");
  }
  function init() {
    var cart = Parse.Object.extend("Cart");
    var query = new Parse.Query(cart);

    console.log("Cart to be searched for User: " + Parse.User.current().get("username"));
    console.log("Cart to be searched for User with id " + Parse.User.current().id);

    query.equalTo("user", Parse.User.current());
    query.find({
      success: function (results) {
        console.log("No of carts items " + results.length);
        $scope.items = results;
        for (var i = 0; i < results.length; i++) {
          console.log(results[i].get("qty") /*+ " " + results[i].get("user")*/ );
          console.log(results[i].get("user").id /*+ " " + results[i].get("user")*/ );
          console.log("productName "+ results[i].get("product").get("url"))
       /*
           var obj={product: results[i].get("product").get("productName"),
           price: results[i].get("product").get("unitPrice")};
           arr.push(obj);
       */
        }
        /*console.log("Returning product is: " + results[1].get("productName"))*/
        console.log("No of cart items in finalresult " + results.length);
        $scope.calculateTotal();
      }, error: function (error) {
        alert("Error: " + error.code + " " + error.message);
      }
    })
  }

  $scope.removeItem = function (index) {
    console.log("Id for the cart to be removed is: " + $scope.items[index].get("product").id)
    console.log("Id for the cart to be removed is: " + $scope.items[index].id)
    var cart = Parse.Object.extend("Cart");
    var cartToBeDeleted = new cart();
    cartToBeDeleted.id = $scope.items[index].id;
    $scope.items.splice(index, 1);
    cartToBeDeleted.destroy({
      success: function (myObject) {
        console.log("Successfully deleted");
        $scope.calculateTotal();
      },
      error: function (myObject, error) {
        console.log("Failure in deletion");
      }
    });
  }
    $scope.calculateTotal = function () {
      var total = 0;
      console.log("$scope.items.length " + $scope.items.length);
      for (var i = 0; i < $scope.items.length; i++) {
        var item = $scope.items[i];
        var qty = parseInt(item.get("qty"));
        var price = parseInt(item.get("product").get("unitPrice"));
        console.log("QTY " + qty);
        console.log("Price " + price);
        total += price * qty;
      }
      $scope.calcSum = total;
      $rootScope.calcSum=total;
      //return total;
    };

    $scope.checkOut = function (items) {
      console.log("Hiiii");
      $rootScope.cartItems = items;
      console.log("No of Quantities: " + items.length)
    }
    /* function cartCtrl() {
     var vm = this,
     defaultNumberObj = {
     value: 0
     };
     angular.extend(this, {
     numbers: [angular.copy(defaultNumberObj)],
     calcSum: function () {
     console.log("Inside calcSum fn");
     var sum = 0;
     angular.forEach(vm.numbers, function (number) {
     sum += parseInt(number.value) || 0;
     });
     return sum;
     }
     });
     }*/


})
