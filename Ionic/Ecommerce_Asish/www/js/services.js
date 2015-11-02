Parse.initialize("qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX", "OgDuxM1kk6T9QCKqEOCDg0Bq0UPBkfz5FP9T6raS");
angular.module('app.services', [])
.factory('ProductService', function($rootScope,$http,PARSE_CREDENTIALS) {
    var finalresult = {
      findProduct: function(productDesc) {
        var product = Parse.Object.extend("Product");
        var query = new Parse.Query(product);
        query.contains("productDesc", productDesc);
        query.find({
          success: function (results) {
            console.log("No of Products " + results.length);
            for (var i = 0; i < results.length; i++) {
              console.log(results[i].get("productName") + " " + results[i].get("modelNo") + " " + results[i].get("productDesc"));
            }
            console.log("Returning product is: " + results[1].get("productName"))
            console.log("No of Products in finalresult " + finalresult.length);
            return results;
          }, error: function (error) {
            alert("Error: " + error.code + " " + error.message);
          }
        })
      }
    }
    return finalresult;

    /*return{
      findProduct: function(productDesc){
        var product = Parse.Object.extend("Product");
        var query = new Parse.Query(product);
        query.contains("productDesc", productDesc);
        var finalresult;
        query.find({
          success: function (results) {
            console.log("No of Products " + results.length);
            for (var i = 0; i < results.length; i++) {
              console.log(results[i].get("productName") + " " + results[i].get("modelNo") + " " + results[i].get("productDesc"));
            }
            console.log("Returning product is: "+results[1].get("productName"))
            finalresult= results;
            console.log("No of Products in finalresult " + finalresult.length);
            return finalresult;
          }, error: function (error) {
            alert("Error: " + error.code + " " + error.message);
          }
        });
        /!*console.log("No of Products in finalresult " + finalresult);*!/
      }
    };*/
 //   return{
      /*findProduct=function (productDesc) {*/


   /* function findProduct(productDesc) {
        var product = Parse.Object.extend("Product");
        var query = new Parse.Query(product);
        query.contains("productDesc", productDesc);
        query.find({
          success: function (results) {
            console.log("No of Products " + results.length);
            $rootScope.products=results;
            for (var i = 0; i < results.length; i++) {
              console.log(results[i].get("productName") + " " + results[i].get("modelNo") + " " + results[i].get("productDesc"));
            }
            console.log("Returning product is: " + results[1].get("productName"))
            console.log("No of Products in finalresult " + results.length);
           /!* return results;*!/
            finalresult=results;
          }, error: function (error) {
            alert("Error: " + error.code + " " + error.message);
            finalresult={};
          }
        });
         return finalresult;
      }


        return {
          findProduct: findProduct
        }*/


  //  }
})
  .factory('Utils', function() {
  var service = {
    isUndefinedOrNull: function(obj) {
      return !angular.isDefined(obj) || obj===null;
    },
    gerOrderNo : function(){
      return Math.random().toString(36).substring(7);
    }
  }

  return service;
})
  .factory('RestAPI', function($http,PARSE_CREDENTIALS) {
    var service = {
      getPayment: function() {
        return $http.get('http://localhost:8080/api/v1/display',{
        /*return $http.get('http://localhost:8080/display',{*/
          /*headers:{
            'X-Parse-Application-Id': PARSE_CREDENTIALS.APP_ID,
            'X-Parse-REST-API-Key':PARSE_CREDENTIALS.REST_API_KEY,
          }*/
        });
      },
      updateOrder: function(OrderInput) {
        return $http.post('http://localhost:8080/api/v1/update',OrderInput,{
        /*return $http.post('http://localhost:8080/update',data,{*/
          /*headers:{
           'X-Parse-Application-Id': PARSE_CREDENTIALS.APP_ID,
           'X-Parse-REST-API-Key':PARSE_CREDENTIALS.REST_API_KEY,
           }*/
          headers:{
            'Content-Type':'application/json'
          }
        });

        /*return $http({
          method: 'POST',
          url: 'http://localhost:8080/api/v1/update',
          data: OrderInput,
          headers: {
            "Content-Type": "application/json"
          }
        });*/

      }

    }

    return service;
  })
.service('productService1', [function(){
    this.findProduct=function(productDesc){
      var product = Parse.Object.extend("Product");
      var query = new Parse.Query(product);
      query.contains("productDesc", productDesc);
      query.find({
        success: function (results) {
          console.log("No of Products " + results.length);
            for (var i = 0; i < results.length; i++) {
             console.log(results[i].get("productName") + " " + results[i].get("modelNo") + " " + results[i].get("productDesc"));
          }
          console.log("Returning product is: "+results[1].get("productName"))
          return results;
        }, error: function (error) {
          alert("Error: " + error.code + " " + error.message);
        }
      });
    }

}])
.value('PARSE_CREDENTIALS',{
  APP_ID: 'qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX',
  REST_API_KEY:'OgDuxM1kk6T9QCKqEOCDg0Bq0UPBkfz5FP9T6raS'
});

