angular.module('app.routes', [])

.config(function($stateProvider, $urlRouterProvider) {

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
      url: '/signUp',
      templateUrl: 'templates/signup.html',
      controller: 'signupCtrl'
    })

    .state('categories', {
      url: '/categories',
      templateUrl: 'templates/categories.html',
      controller: 'categoriesCtrl'
    })

    .state('products', {
      url: '/products',
      templateUrl: 'templates/products.html',
      controller: 'productsCtrl'
    })

    .state('orders', {
      url: '/orders',
      templateUrl: 'templates/orders.html',
      controller: 'ordersCtrl'
    })

    .state('checkOut', {
      url: '/checkOut',
      templateUrl: 'templates/checkOut.html',
      controller: 'checkOutCtrl'
    })

    .state('productDetails', {
      url: '/productDetails',
      templateUrl: 'templates/productDetails.html',
      controller: 'productsCtrl'
    })

    .state('cartDetails', {
      url: '/cartDetails',
      templateUrl: 'templates/cartDetails.html',
      controller: 'cartCtrl'
    })


    ;

  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/login');

});
