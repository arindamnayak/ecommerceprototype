'use strict';
Parse.initialize("qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX", "OgDuxM1kk6T9QCKqEOCDg0Bq0UPBkfz5FP9T6raS");
var app=angular.module('myApp.Views', ['ngRoute'])
app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/Views', {
    templateUrl: 'Views/product.html',
    controller: 'ProductCtrl'
  });
   $routeProvider.when('/AddProducts', {
            templateUrl: 'Views/AddProducts.html',
            controller: 'AddProductCtrl'
        });
    $routeProvider.when('/addProductBack', {
            templateUrl: 'Views/product.html',
            controller: 'ProductCtrl'
        });

        /*$routeProvider.when('/productDetails', {
            templateUrl: 'Views/productDetails.html',
            controller: 'ProductDetailsCtrl'
        });*/
}])
    /*
    Controller to handle functionality for products
    */
app.controller('ProductCtrl', ['$scope','$rootScope',function( $scope,$rootScope) {
      console.log("Populating Product table..");
      var product = Parse.Object.extend("Product");
      var query = new Parse.Query(product);


       $scope.search=function() {
           if($scope.searchProduct != null) {
               console.log("Product to be searched: " + $scope.searchProduct);
               query.contains("productDesc", $scope.searchProduct);
           }
           query.find({
               success: function (results) {
                   console.log("No of Products " + results.length);
                   $scope.products = results;

                   for (var i = 0; i < results.length; i++) {
                      console.log(results[i].get("productName") + " " + results[i].get("modelNo") + " " + results[i].get("productDesc"));
                   }
               }, error: function (error) {
                   alert("Error: " + error.code + " " + error.message);
               }
           });
       }
        /*
        When addProduct button is clicked, it should navigate to AddProduct Page.
         */
        $scope.addProduct=function(){
            location.href = "#/AddProducts";
        }

        $scope.selectProduct=function(index){
        console.log("index is "+index.id);
            console.log("available Qty is "+index.get("availableQty"));
            //console.log("Available Qty: "+$scope.products[index].get("availableQty"));
        /*console.log("U selected : "+$scope.products.indexOf(index));*/
            console.log("U selected : "+$scope.products.indexOf(index));
             /* showProductDetails(index);*/
            $rootScope.availableQuantity=index.get("availableQty");
            $rootScope.modelNo=index.get("modelNo");
            $rootScope.ProductName=index.get("productName");
            $rootScope.productIndex=index;
            $rootScope.amount=index.get("unitPrice");
            location.href = "#/productDetails";

        }
    }]);
    /*
    Trying to use service, but it's not working here.
    */
    app.factory('items', function($scope) {
    var items = $scope.index;
     //  var itemsService = {};

    /*itemsService.add = function(item) {
        items.push(item);
    };
    itemsService.list = function() {
        return items;
    };*/
    return items;
    });

    /*
    Controller to handle the functionality for adding new products.
    */
   app.controller('AddProductCtrl', ['$scope',function( $scope) {
        console.log("Reading ADDProduct table..");
        $scope.addProduct=function(){
            var product = Parse.Object.extend("Product");
            var sku = Parse.Object.extend("Sku");
            var category = Parse.Object.extend("Category");
            var currency = Parse.Object.extend("Currency");
            var newProduct= new product();

            /*
            Setting Product attributes to the new product created
             */
            newProduct.set("productName",$scope.product.productName);
            newProduct.set("productDesc",$scope.product.productDesc);
            newProduct.set("modelNo",$scope.product.modelNo);
            newProduct.set("unitPrice",$scope.product.unitPrice);
            newProduct.set("availableQty",$scope.product.availableQty);


            /*
            Assigning Access Controls to the new the new products
             */
            var newProductACL = new Parse.ACL();
            newProductACL.setRoleWriteAccess("Administrator",true);
            newProductACL.setRoleReadAccess("Administrator",true);
            newProductACL.setPublicReadAccess(true);
            newProductACL.setPublicWriteAccess(false);
            newProductACL.setReadAccess(Parse.User.current().id, true);
            newProductACL.setWriteAccess(Parse.User.current().id, true);
            newProduct.setACL(newProductACL);

            /*
            Sample data
             */
            /* newProduct.set("productName","HP Laptop");
            newProduct.set("productDesc","HP Laptops");
            newProduct.set("modelNo","HP123456");
            newProduct.set("unitPrice","3600");
            newProduct.set("availableQty","25");*/

            var newSku = new sku();
            var newCurrency= new currency();
            var newCategory= new category();

            var skuQuery = new Parse.Query(sku);
            skuQuery.get($scope.sku.id,{
            /*skuQuery.get("bRCUgZFwvS",{*/
                success: function(sku1) {
                    newSku.id=sku1.id;
                  },
                error: function(object, error) {
                    alert("user needs to fill the sku details for adding new sku");
                    var sku= new sku();
                    sku.set("color",$scope.sku.color);
                    sku.set("size",$scope.sku.size);
                    sku.set("description",$scope.sku.description);
                    newProduct.set("sku",sku);
                    console.log(error.message);
                }
            });
            var categoryQuery = new Parse.Query(category);
            categoryQuery.get($scope.category.id,{
            /*categoryQuery.get("a6irSxnlUP",{*/
                success: function(category) {
                    newCategory=category;
                    console.log("category found " + newCategory.id);
                },
                error: function(object, error) {
                    console.log(error);
                }
            });

            var currency = new Parse.Query(currency);
            currency.get($scope.currency.id,{
            /*currency.get("1aGtLF5vRm",{*/
                success: function(currency) {
                    newCurrency=currency;
                    console.log("newcurrency found " + newCurrency.id);
                },
                error: function(object, error) {
                    console.log(error);
                }
            });
            newProduct.save(null,{
                success: function(newProduct) {
                    console.log("sku found " + newSku.id);
                    /*
                    var relation = newProduct.relation("sku");
                    relation.add(newSku);
                    var relation1 = newProduct.relation("currency");
                    relation1.add(newCurrency);*/
                    newProduct.set("sku",newSku);
                    newProduct.set("currency",newCurrency);
                    newProduct.set("category",newCategory);


                    newProduct.save(null,{
                        success: function(newProduct) {
                            console.log("New Product Created successfully");
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
        /*
        When Back pressed, it should go back to the previous page.
         */
        $scope.addProduct.back=function(){
            console.log("back clicked");
            location.href = "#/addProductBack";
        }
    }]);

