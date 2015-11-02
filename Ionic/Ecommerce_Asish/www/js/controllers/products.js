/**
 * Created by AsishS on 21-10-2015.
 */
Parse.initialize("qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX", "OgDuxM1kk6T9QCKqEOCDg0Bq0UPBkfz5FP9T6raS");
var app = angular.module('app.controllers.products', ['ionic'])
app.controller('productsCtrl', function ($scope, ProductService, $rootScope, Utils,RestAPI) {
  console.log("Inside products")

  $scope.refresh = function () {
    console.log("Calling RestAPIs");

   /* var OrderInput={
      "orderId":"d47rWnbZ7q",
      "productList":[
        { "qty":40,
          "productId":"5qOzqTqaeg"
        }
      ]
    };*/

    /*var OrderInput={
      "orderId":"",
      "productList":[
        { "qty":'',
          "productId":""
        }
      ]
    }*/

  /*  function OrderInput(orderId, productList){
        this.orderId = orderId;
        this.productList = productList;
    }
    function productList(qty,productId){
      this.qty = qty;
      this.productId = productId;
    }*/

    var productList=[];
    for(var i=0;i<2;i++)
    {
      /*productList[i]= new productList(i,"JEGsLZgcCd")*/
      productList[i]= { "qty":i,"productId":"JEGsLZgcCd"}
    }

    /*var OrderInput= new OrderInput('ujBbyUJJOz',productList);*/
    var OrderInput={"orderId":'ujBbyUJJOz',"productList":productList};

   /* var OrderInput={
      "orderId":"1234567",
      "productList":productList
    };*/

    console.log("OrderInput is: "+ OrderInput.toString);
   /* var OrderInput;
    var productList;*/

    /*RestAPI.updateOrder(OrderInput).success(function(result){
      /!*  $scope.items=data.results;*!/
      /!*console.log("Data from Rest API is: "+ data.get("status"));*!/
      console.log("Data from Rest API is: "+ result.status);
      console.log("Data from Rest API is: "+ result.message);
    }).error(
      function(error){
        console.log("Error is: "+ error.message);
      }
    )*/

    /*RestAPI.getPayment().success(function(data){
      /!*  $scope.items=data.results;*!/
      /!*console.log("Data from Rest API is: "+ data.get("status"));*!/
      console.log("Data from Rest API is: "+ data.status);
      console.log("Data from Rest API is: "+ data.message);
    });*/
    /* $scope.SearchProducts($scope.Product.searchProduct);*/
  }
  $scope.select = function (product) {
    console.log("product selected is " + product.get("productName"));
    console.log("product selected is " + product.id);
    console.log("available Qty is " + product.get("availableQty"));
    $rootScope.availableQty = product.get("availableQty");
    var indexId = product.id;  //tempIndex stores the product selected
    console.log("productId is " + indexId);
    var productDetails = Parse.Object.extend("Product_Details");
    var query = new Parse.Query(productDetails);
    var product = Parse.Object.extend("Product");
    var productPointer = new product();
    productPointer.id = indexId;
    query.equalTo("product", productPointer);
    query.find({
      success: function (results) {
        if (results.length == 0) {
          alert("No Product Details found");
        }
        console.log("No of ProductDetailss " + results.length);
        $rootScope.entireProductDetails = results;
        /*$scope.noOfQtyOrdered=2;*/
        for (var i = 0; i < results.length; i++) {
          console.log(results[i].get("Product_Name") + " " + results[i].get("Product_Manufacturer") + " " + results[i].get("Product_Brand"));
        }
      }, error: function (error) {
        alert("Error: " + error.code + " " + error.message);
      }
    });
    /*location.href = "#/productDetails";*/
  }

  $scope.SearchProducts = function (Product) {
    //console.log("retrieving value from Service: " + ProductService.findProduct(Product.searchProduct));

    if (Utils.isUndefinedOrNull(Product)) {
      alert("product Desc is null");
    }

    if (!Utils.isUndefinedOrNull(Product)) {
      var product = Parse.Object.extend("Product");
      var query = new Parse.Query(product);
      var productDesc = Product.searchProduct;
      console.log("Products to be searched: " + productDesc);
      query.contains("productDesc", productDesc);
      query.find({
        success: function (results) {
          console.log("No of Products " + results.length);
          $rootScope.products = results;

          for (var i = 0; i < results.length; i++) {
            console.log(results[i].get("productName") + " " + results[i].get("modelNo") + " " + results[i].get("productDesc"));
          }
          /*console.log("Returning product is: " + results[1].get("productName"))*/
          console.log("No of Products in finalresult " + results.length);
        }, error: function (error) {
          alert("Error: " + error.code + " " + error.message);
        }
      })
    }

  }

  $scope.addToCart= function (Product) {
    console.log("product selected is " + Product.get("productName"));

    var cart = Parse.Object.extend("Cart");
    var product = Parse.Object.extend("Product");
    var productPointer = new product();
    productPointer.id=Product.id;
    var newCart= new cart();
    newCart.set("qty",1);
    newCart.save(null,{
      success: function(newCart) {
        newCart.set("product",productPointer);
        newCart.set("user",Parse.User.current());
        newCart.save(null,{
          success: function(newCart) {
            console.log("Cart Added successfully");
            alert("Cart Added successfully");
            location.href = "#/cartDetails";
          },
          error:function(object, error) {
            console.log("Error Found"+ error.message);
            alert("Failure !! Could not be added to Cart");
          }
        });
      },
      error: function(object, error) {
        console.log(error.message);
        alert("Failure !! Could not be added to Cart");
      }
    });

  }

  $scope.addProducts=function(product){
    console.log("Updating product");

    /*product.set("availableQty","40");
    var sku = Parse.Object.extend("Sku");
    var sku1= new sku();
    var sku2= new sku();
    sku1.id="bRCUgZFwvS";
    sku1.color="RED";
    sku2.size="12";
    /!*sku1.set("size","18");
    sku1.set("size","22");*!/
    sku2.id="DW0sIYkoXC";
    sku2.color="GREEN";
    sku2.size="12";
    /!*sku2.set("size","18");
    sku2.set("size","22");*!/
    product.add("skus",sku1);
    product.add("skus",sku2);
    product.save(null,{
      success: function(newproduct){
        console.log("Product updated successfully");
      },
      error:function(object,error){
        console.log(error.message);
      }
    });*/

   /* var skus=product.get("skus");
    console.log("color is "+ sku.id);
    console.log("color is "+ sku.get("color"));
    console.log("color is "+ product.get("sku").get("color"));
    var newSkus=[{"color":'',"size":''}];
    var sku= Parse.Object.extend("Sku")
    var query = new Parse.Query(sku);

    for(var i=o;i<skus.length;i++){

    }*/
   /*
    var sku= Parse.Object.extend("Sku")
    var newSku= new sku();
    console.log("No of items in skus: "+skus.length);
   for( var i=0; i<skus.length;i++ )
    {
        newSku=skus[i];
        console.log("i is: "+i);
        console.log(skus[i].id);
        console.log(newSku.get("color"));
    }*/



  }
})
