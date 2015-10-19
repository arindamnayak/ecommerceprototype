/**
 * Created by AsishS on 14-10-2015.
 */
var app=angular.module('myApp.ProductDetails', ['ngRoute']);

app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/productDetails', {
        templateUrl: 'Views/productDetails.html',
        controller: 'ProductDetailsCtrl'
    });
}]);

app.controller('ProductDetailsCtrl', ['$scope','$rootScope',function( $scope,$rootScope) {
    $scope.back=function(){
        console.log("Product Details page loaded");
        //console.log("$rootScope.index.id "+$scope.tempIndex.id);
        /*console.log("items.index.id "+items.length);*/
        location.href = "#/Views";
    };
    $scope.init= function(){
    console.log("Inside table loading");
    var indexId=$scope.productIndex.id;  //tempIndex stores the product selected
    console.log("$rootScope.index.id "+$scope.productIndex.id);
    var productDetails = Parse.Object.extend("Product_Details");
    var query = new Parse.Query(productDetails);
        var product = Parse.Object.extend("Product");
        var productPointer= new product();
        productPointer.id=indexId;
        query.equalTo("product",productPointer);

      query.find({
            success: function (results) {
                if(results.length==0)
                {
                    alert("No Product Details found");
                }
                console.log("No of ProductDetailss " + results.length);
                $scope.entireProductDetails=results;
                $scope.availableQty=$scope.availableQuantity;
                $scope.noOfQtyOrdered=2;
                for (var i = 0; i < results.length; i++) {
                    console.log(results[i].get("Product_Name") + " " + results[i].get("Product_Manufacturer") + " " + results[i].get("Product_Brand"));
                }
            }, error: function (error) {
                alert("Error: " + error.code + " " + error.message);
            }
        });
    };
    $scope.refreshPage=function(){
        console.log("Refreshing Content");
        $scope.init();
    }
    $scope.addToCartProductDetails=function(index){
        //alert("Qty Ordered is: "+ $scope.entireProductDetails[1].noOfQtyOrdered);
        console.log("Index is "+index.id);
        console.log("Quanity to be ordered from scope is: "+ $scope.noOfQtyOrdered);
        $rootScope.qtyOrdered=$scope.noOfQtyOrdered;
        console.log("Product details chosen is "+index.get("Product_Brand"));
        location.href = "#/Orders";
    }

}]);




