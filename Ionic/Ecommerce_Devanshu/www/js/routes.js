angular.module('app.routes', [])

  .config(function ($stateProvider, $urlRouterProvider) {

    // Ionic uses AngularUI Router which uses the concept of states
    // Learn more here: https://github.com/angular-ui/ui-router
    // Set up the various states which the app can be in.
    // Each state's controller can be found in controllers.js
    $stateProvider


      .state('login', {
        url: '/login',
        templateUrl: 'templates/login.html',
        controller: 'loginCtrl'
      })


      .state('signup', {
        url: '/signup',
        templateUrl: 'templates/signup.html',
        controller: 'signupCtrl'
      })


      .state('home', {
        url: '/home',
        templateUrl: 'templates/home.html',
        controller: 'homeCtrl'
      })


      .state('products', {
        url: '/products',
        templateUrl: 'templates/products.html',
        controller: 'productsCtrl'
      })


      .state('checkOut', {
        url: '/checkOut',
        templateUrl: 'templates/checkOut.html',
        controller: 'checkOutCtrl',
        resolve    :{
          cartItem:function ($q,$timeout, $rootScope,CheckOut,$interval) {
            var deferred = $q.defer();


            var items = Parse.Object.extend("Cart");
            var query = new Parse.Query(items);
            query.equalTo('user', Parse.User.current());
            var promise =query.find({
              success: function (Cart) {
                console.log("Items in cart "+Cart.length);
                if(Cart.length==0){
                  alert("Cart is empty");
                }

                var items = Parse.Object.extend("Product");
                var query = new Parse.Query(items);
                for (i = 0; i < Cart.length; i++) {
                  (function (i) {
                    query.get(Cart[i].get('product').id, {
                      success: function (obj) {

                        if (i == Cart.length - 1) {
                          console.log('resolved');
                          deferred.resolve(Cart);
                        }
                      },
                      error: function (obj, error) {
                        console.log("error in resolve " + error.message);
                      }
                    });
                  }(i))

                }
              }, error: function (error) {
                alert("Error: " + error.code + " " + error.message);
              }
            });
        /*    CheckOut.then(function (Cart){
              if(Cart.length==0){
                deferred.resolve([]);
              }else {
               var items = Parse.Object.extend("Product");
                var query = new Parse.Query(items);
                for (i = 0; i < Cart.length; i++) {
                  (function (i) {
                    query.get(Cart[i].get('product').id, {
                      success: function (obj) {
                        if (i == Cart.length - 1) {
                          console.log('resolved');
                          deferred.resolve(Cart);
                        }
                      },
                      error: function (obj, error) {
                        console.log("error in resolve " + error.message);
                      }
                    });
                  }(i))

                }
              }

            });*/

            return deferred.promise;
          }
        },


      })


      .state('cart', {
        url: '/cart',
        templateUrl: 'templates/cart.html',
        controller: 'cartCtrl'
      })


      .state('payment', {
        url: '/payment',
        templateUrl: 'templates/payment.html',
        controller: 'paymentCtrl'
      })

    ;

    // if none of the above states are matched, use this as the fallback
    $urlRouterProvider.otherwise('/login');

  });
