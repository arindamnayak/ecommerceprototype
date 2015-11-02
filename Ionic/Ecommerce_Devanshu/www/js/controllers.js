angular.module('app.controllers', ['ngCookies'])

  .controller('loginCtrl', ['$scope', function ($scope) {

    $scope.login = function (User) {
      var username = User.username;
      var password = User.password;
      Parse.User.logIn(username, password, {
        success: function (user) {
          location.href = "#/home";
        }, error: function (user, error) {
          console.log(error.message);
        }
      });
    };
  }])

  .controller('signupCtrl', function ($scope) {

    $scope.signUp = function (data) {
      var user = new Parse.User();
      user.set("username", data.username);
      user.set("password", data.password);
      user.set("email", data.email);

      user.signUp(null, {
        success: function (User) {
          location.href = "#/home";
        },
        error: function (user, error) {
          alert("Error: " + error.code + " " + error.message);
        }
      });

    };
  })

  .controller('homeCtrl', function ($scope, MyCategoryService, Utils,$cookies,$rootScope) {

    var category = Parse.Object.extend("Category");
    var query = new Parse.Query(category);
    query.find({
      success: function (Categorys) {
        $scope.categorys = Categorys;
        /*for(i=0;i<Categorys.length;i++){
         console.log(Categorys[i].get('Type'));
         }*/
      }, error: function (error) {
        alert("Error: " + error.code + " " + error.message);
      }
    });

    $scope.select = function (category) {
      console.log(category.get('Type'));
      var product = Parse.Object.extend("Product");
      var query = new Parse.Query(product);
      query.equalTo('category', category);
      query.find({
        success: function (Products) {
          MyCategoryService.set(Products);
          //$cookieStore.put('products',Products);
          $cookies.prod = Products;
          for (i = 0; i < Products.length; i++) {
            console.log(Products[i].get('productName'));
          }
          location.href = "#/products";
        }, error: function (error) {
          alert("Error: category" + error.code + " " + error.message);
        }
      });
    }

    $scope.search = function (data) {
      console.log("you are searching the term " + data);
      var product = Parse.Object.extend("Product");
      var query = new Parse.Query(product);
      query.contains("productDesc", data);
      query.find({
        success: function (Products) {
          for (i = 0; i < Products.length; i++) {
            console.log("Product " + Products[i].get("productName"));
          }
          MyCategoryService.set(Products);
          location.href = "#/products";

        }, error: function (error) {
          console.log("Error " + error);
        }
      })
    };

    $scope.logout = function () {
      Utils.logout();
    }

  })

  .controller('productsCtrl', function ($scope, MyCategoryService, MyProductService, Utils,$cookies,$rootScope) {
    //console.log(MyCategoryService.get());
  // $scope.products = MyCategoryService.get();
     // $scope.products = $cookieStore.get('products');
      $scope.products = $cookies.prod;
    $scope.select = function (product) {
      console.log(product.get('productName'));
      var productDetails = Parse.Object.extend("Product_Details");
      var query = new Parse.Query(productDetails);

      query.equalTo('product', product);
      query.find({
        success: function (Products) {
          if (Products[0] != null) {
            MyProductService.set(Products[0]);
            //$cookieStore.put('selectedProduct',Products[0]);
            $cookies.selectedProd = Products[0];
            console.log(Products[0]);
            location.href = "#/cart";
          } else {
            alert("Details not available");
          }
        }, error: function (error) {
          alert("Error: " + error.code + " " + error.message);
        }
      });


    };
    $scope.logout = function () {
      Utils.logout();
    }

  })

  .controller('cartCtrl', function ($scope,$rootScope, MyProductService, Utils,CheckOut,$cookies,$timeout) {
       // var productDetails = MyProductService.get();
      //var productDetails =$cookieStore.get('selectedProduct');
      var productDetails = $cookies.selectedProd;
      console.log("product details"+productDetails);
      $scope.url=productDetails.get('product').get('url');
      $scope.productName=productDetails.get('product').get('productName');
      $scope.brand=productDetails.get('Product_Brand');
      $scope.price=productDetails.get('product').get('unitPrice');

      $scope.addToCart =function () {

      var Cart = Parse.Object.extend('Cart');
      var cart = new Cart();
      cart.set("product", productDetails.get('product'));
      cart.set("user", Parse.User.current());
      cart.set("qty", 1);
      cart.save(null, {
        success: function (Cart) {
        $scope.cart= Cart;
          /*$rootScope.products.push($scope.cart);*/
          //CheckOut.then(
          //    function(result){
          //      $rootScope.products=result;
          //      console.log("item added to cart"+result.length);
          //    });

         /* var items = Parse.Object.extend("Cart");
          var query = new Parse.Query(items);
          query.equalTo('user', Parse.User.current());
          query.find({
            success: function (art) {
              console.log("Items in cart "+art.length);
              $rootScope.products=art;
            }, error: function (error) {
              alert("Error: " + error.code + " " + error.message);
            }
          });*/
          alert("Added To Cart");
        },
        error: function (Cart, error) {
          // Execute any logic that should take place if the save fails.
          // error is a Parse.Error with an error code and message.
          alert('Failed ' + error.message);
        }
      });

      /* var Order = Parse.Object.extend("Cart");
       var order = new Order();

       order.set("Status", "In Progress");
       order.set("user", Parse.User.current());
       // order.set("cheatMode", false);
       console.log(Parse.User.current());
       order.save(null, {
       success: function (Order) {

       alert('Added to Cart');
       },
       error: function (Order, error) {
       // Execute any logic that should take place if the save fails.
       // error is a Parse.Error with an error code and message.
       alert('Failed ' + error.message);
       }
       });*/



    };

    $scope.logout = function () {
      Utils.logout();
    };

  })

  .controller('checkOutCtrl',function ($rootScope,$scope, Payment1, Utils,CheckOut,cartItem) {

       $scope.qty=[];
      $rootScope.products=[];
          $rootScope.products = cartItem;//$rootScope.CartItem1;

         var total = 0;
         for (i = 0; i < $rootScope.products.length; i++) {
         $scope.qty[i] = $rootScope.products[i].get('qty');
         total = total + ($scope.qty[i] *
         $rootScope.products[i].get('product').get('unitPrice'));
         }
         $scope.total = total;
        console.log('total '+total);


       $scope.sum1 = function (qty, index) {
      $scope.products[index].set('qty', +qty);
      var TotalPrice = 0;
      for (i = 0; i < $scope.products.length; i++) {

        TotalPrice = TotalPrice + (($scope.products[i].get('product').get('unitPrice')) * +$scope.products[i].get('qty'));
      }

      console.log("Total Price" + TotalPrice);
      $scope.total = TotalPrice;

    }

       $scope.delete = function (index) {
      var items = Parse.Object.extend("Cart");
      var query = new Parse.Query(items);
      var object = $scope.products[index].id;
      var total=0;

      console.log("objectId to deleted from cart " + object);
         $rootScope.products.splice(index, 1);
      for(i =0 ;i<$rootScope.products.length;i++){
        total = total + $rootScope.products[i].get('qty')* $rootScope.products[i].get('product').get('unitPrice');
      }
      $scope.total = total;
      query.get(object, {

        success: function (obj) {
          obj.destroy({});
        }, error: function (obj, error) {
          alert("Error " + error);
        }
      });


    }

       $scope.save = function () {


      for (i = 0; i < $rootScope.products.length; i++) {
        var newItems = $rootScope.products;

        var cart = Parse.Object.extend("Cart");
        var query = new Parse.Query(cart);
        query.equalTo("objectId", $rootScope.products[i].id);
        (function (i) {
          query.first({
            success: function (obj) {

              obj.set('qty', $rootScope.products[i].get('qty'));
              obj.save({
                success: function (o) {

                },
                error: function (o, e) {

                }

              })
            },
            error: function (obj, error) {
              console.log("error " + error.code + "message " + error.message);
            }
          });
        }(i))


      }


      console.log("Setting total  " + $scope.total);
      Payment1.set($scope.total);
      Payment1.setItems($rootScope.products);
      location.href = "#/payment";

    }

       $scope.$watch(CheckOut, function () {
         $rootScope.products = cartItem;
        });

      $scope.refresh = function(){
        CheckOut.then(function(restult){
          $rootScope.products = restult;
        })
      }
       $scope.logout = function () {
      Utils.logout();
    };

  })

  .controller('paymentCtrl', function ($scope, Payment1, Utils) {

    $scope.products = Payment1.getItems();
    $scope.total = Payment1.get();
    $scope.placeOrder = function () {
      console.log("Order placed successfully");
      var qtySum = 0;
      for (i = 0; i < $scope.products.length; i++) {
        qtySum = qtySum + $scope.products[i].get('qty');
      }

      var Order = Parse.Object.extend('Order');
      var order = new Order();

      order.set('Status', "In Progress");
      order.set('SubTotal', $scope.total);
      order.set('TotalAmount', $scope.total);
      order.set('TotalQty', qtySum);
      order.set('TotalDiscountAmt', 0);
      order.set('user', Parse.User.current());
      order.set('OrderNo', Utils.Order());
      order.set('currency', $scope.products[0].get('product').get('currency'));
      order.set('TotalTaxAmt',0);
      order.save(
        {success: function(obj){
          alert("Order placed successfully, your Order Id is "+obj.get('OrderNo'));
          for(i=0;i<$scope.products.length;i++){
            var OrderItem = Parse.Object.extend("Order_Items");
            var orderItem = new OrderItem();
            orderItem.set('order',obj);
            orderItem.set('product',$scope.products[i].get('product'));
            orderItem.set('totalQty',$scope.products[i].get('qty'));
            (function (i) {orderItem.save({
              success: function(){

              }
              ,
              error: function(obj,error){
                console.log(error.message);
              }
            })}(i));

          }
        }
          , error: function(obj,error){

          }

        }
      );

      var Cart = Parse.Object.extend("Cart");
      var cart = new Parse.Query(Cart);
      cart.equalTo('user',Parse.User.current());
      cart.find({success: function(list) {
        for(j=0;j<list.length;j++){
          list[j].destroy({});
        }
      }
      });


    }
    $scope.logout = function () {
      Utils.logout();
    };

    $scope.$watch(
      function ($scope) {
      }, function () {
      }
    );
  })
