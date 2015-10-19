/**
 * Created by AsishS on 15-10-2015.
 */

var app=angular.module('myApp.Orders', ['ngRoute']);

app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/Orders', {
        templateUrl: 'Views/orders.html',
        controller: 'OrderCtrl'
    });
}]);

app.controller('OrderCtrl', ['$scope',function( $scope) {
    $scope.back=function(){
        console.log("Order Details page loaded");
        location.href = "#/productDetails";
    };
    $scope.refreshPage=function(){
        console.log("Refreshing Content");
        $scope.init();
    }
    var OrderNo= Math.random().toString(36).substring(7);
    var totalAmount;
    $scope.init= function(){
        console.log("Inside Order table loading");
        console.log("no Of qty Ordered: "+$scope.qtyOrdered);
        console.log("modelNo : "+$scope.modelNo);
        console.log("ProductName : "+$scope.ProductName);
        console.log("amount : "+$scope.amount);
        totalAmount=$scope.qtyOrdered*$scope.amount;

        console.log("Reverse String: " + OrderNo);

        $scope.neworderDetails=[{
            Product_Name:$scope.ProductName,
            Model_No:$scope.modelNo,
            OrderNo: OrderNo,
            TotalQty: $scope.qtyOrdered,
            TotalAmount: $scope.amount,
            TotalTaxAmt: "0",
            TotalDiscountAmt: "0",
            SubTotal: totalAmount,
            Status: "In Progress"
        }];
    };

    /*
     Creating an Order entry for the corresponding order.
     */
    $scope.checkOutOrder=function(order){
       console.log("checking Out Order");
          console.log("OrderNo is "+order.OrderNo);

        var order = Parse.Object.extend("Order");
        var newOrder= new order();
        newOrder.set("OrderNo",OrderNo);
        newOrder.set("TotalQty",$scope.qtyOrdered);
        newOrder.set("TotalAmount",parseInt($scope.amount));
        newOrder.set("TotalTaxAmt",parseInt(0));
        newOrder.set("TotalDiscountAmt",parseInt(0));
        newOrder.set("SubTotal",parseInt(totalAmount));
        newOrder.set("Status","In Progress");

        var newOrderACL = new Parse.ACL();
        newOrderACL.setRoleWriteAccess("Administrator",true);
        newOrderACL.setRoleReadAccess("Administrator",true);
        newOrderACL.setPublicReadAccess(true);
        newOrderACL.setPublicWriteAccess(false);
        newOrderACL.setReadAccess(Parse.User.current().id, true);
        newOrderACL.setWriteAccess(Parse.User.current().id, true);
        newOrder.setACL(newOrderACL);

        var currency = Parse.Object.extend("Currency");
        var newCurrency= new currency();
        var currencyQ = new Parse.Query(currency);
        /*currency.get($scope.currency.id,{*/
        currencyQ.get("1aGtLF5vRm",{   /* Currently setting Currency as INR. So hard coded it.*/
            success: function(currency) {
                newCurrency=currency;
            },
            error: function(object, error) {
                console.log(error);
            }
        });

        var product = Parse.Object.extend("Product");
        var newProduct= new product();
        var productQ = new Parse.Query(product);
        /*currency.get($scope.currency.id,{*/
        console.log("Product Id: "+$scope.productIndex.id);
        productQ.get($scope.productIndex.id,{
            success: function(product) {
                newProduct=product;
            },
            error: function(object, error) {
                console.log(error);
            }
        });

        newOrder.save(null,{
            success: function(newOrder) {
                newOrder.set("product",newProduct);
                newOrder.set("currency",newCurrency);
                newOrder.set("user",Parse.User.current());  //Setting current user here.
                newOrder.save(null,{
                    success: function(newOrder) {
                        console.log("New Order Created successfully");
                        alert("Order Placed Successfully...");
                    },
                    error:function(object, error) {
                        console.log("Error Found"+ error.message);
                    }
                });
            },
            error: function(object, error) {
                console.log(error.message);
            }
        });
    }
}]);