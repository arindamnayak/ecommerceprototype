/**
 * Created by AsishS on 21-10-2015.
 */
Parse.initialize("qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX", "OgDuxM1kk6T9QCKqEOCDg0Bq0UPBkfz5FP9T6raS");
var app=angular.module('app.controllers.categories', ['ionic'])
app.controller('categoriesCtrl', function($scope,$rootScope) {
  //alert("Inside Categories")
  $scope.options={};
  var category = Parse.Object.extend("Category");
  var category= new category();
  var categoryQ = new Parse.Query(category);
  categoryQ.find({
    success: function(categories) {
      $scope.categories=categories;
    },
    error: function(object, error) {
      console.log(error);
    }
  });
  $scope.display=function(options){
    console.log("Display clicked");
    console.log("options clicked is "+ options.selectedCategory.get('Type'));
    }

  $scope.findProducts=function(options){
    console.log("findProducts clicked");
    if(options.selectedCategory !=null)
    {
      var categorySelected=options.selectedCategory.get('Type');
      console.log("categorySelected is: "+ categorySelected);
    }
    else if(options.searchCategory != null)
    {
      var categorySelected=options.searchCategory;
      console.log("categorySelected is: "+ angular.uppercase(categorySelected));
    }
    var product = Parse.Object.extend("Product");
    var query = new Parse.Query(product);
    query.contains("productDesc", angular.uppercase(categorySelected));

    query.find({
      success: function (results) {
        console.log("No of Products " + results.length);
       // $scope.products= results;
        $rootScope.products=results;
       /* $rootScope.skus=results;*/

        for (var i = 0; i < results.length; i++) {
          console.log(results[i].get("productName") + " " + results[i].get("modelNo") + " " + results[i].get("productDesc"));
        }
      }, error: function (error) {
        alert("Error: " + error.code + " " + error.message);
      }
    });
  }
})
