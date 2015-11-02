/**
 * Created by AsishS on 21-10-2015.
 */
Parse.initialize("qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX", "OgDuxM1kk6T9QCKqEOCDg0Bq0UPBkfz5FP9T6raS");
var app=angular.module('app.controllers.checkout', ['ionic'])
app.controller('checkOutCtrl', function($scope,$rootScope,Utils,RestAPI) {
  console.log("Inside checkOut")
  /*reload();*/
  var  totalQtyOrdered=0;
  var orderDetails;
  $scope.refresh= function() {
   reload();
   console.log("reloading");
  }
  function reload(){
    console.log("Reloaded");
    console.log("No of Quantities: "+ $rootScope.cartItems.length)
    console.log("Order No is: "+ Utils.gerOrderNo());
    $rootScope.orderDetails=$rootScope.cartItems;
    orderDetails=$rootScope.cartItems;
    $scope.calcSum=$rootScope.calcSum;
  }

  $scope.checkOut=function(items){
   console.log("No of Quantities: "+ items.length)
  }

  $scope.payment=function(orderDetails){
    console.log("No of items in OrderDetails: "+orderDetails.length)
    var productList=[];
    for(var i=0;i<orderDetails.length;i++){
      console.log("i is: "+i);
      productList[i]= { "qty":orderDetails[i].get("qty"),"productId":orderDetails[i].get("product").id}
    }
    var OrderInput={"orderId":'xyz',"productList":productList};
    RestAPI.updateOrder(OrderInput).success(function(result){
      /*  $scope.items=data.results;*/
      /*console.log("Data from Rest API is: "+ data.get("status"));*/
      console.log("Data from Rest API is: "+ result.status);
      console.log("Data from Rest API is: "+ result.message);
    }).error(
      function(error){
        console.log("Error is: "+ error.message);
      }
    )
    createOrder(orderDetails);
  }

  function createOrder(orderDetails){
    var order= Parse.Object.extend("Order");
    var newOrder= new order();

    var orderItems= Parse.Object.extend("Order_Items");
    var newOrderItems;

    for (var i = 0; i < orderDetails.length; i++) {
      console.log("orderDetails Length"+orderDetails.length);
      console.log("orderDetails ProductName"+orderDetails[i].get("product").get("productName"));
      console.log("orderDetails Price"+orderDetails[i].get("product").get("unitPrice"));
      console.log("Total Qty Ordered is: "+orderDetails[i].get("qty"));
      totalQtyOrdered=totalQtyOrdered+ orderDetails[i].get("qty");

    }
    newOrder.set("OrderNo",Utils.gerOrderNo());
    newOrder.set("TotalQty",totalQtyOrdered);
    newOrder.set("TotalAmount",parseInt($scope.calcSum));
    newOrder.set("TotalTaxAmt",parseInt(0));
    newOrder.set("TotalDiscountAmt",parseInt(0));
    newOrder.set("SubTotal",parseInt($scope.calcSum));
    newOrder.set("Status","In Progress");

    var newOrderACL = new Parse.ACL();
    newOrderACL.setRoleWriteAccess("Administrator",true);
    newOrderACL.setRoleReadAccess("Administrator",true);
    newOrderACL.setPublicReadAccess(true);
    newOrderACL.setPublicWriteAccess(false);
    newOrderACL.setReadAccess(Parse.User.current().id, true);
    newOrderACL.setWriteAccess(Parse.User.current().id, true);
    newOrder.setACL(newOrderACL);


    var product = Parse.Object.extend("Product");
    var newProduct;

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
    newOrder.save(null,{
      success: function(newOrder) {
        newOrder.set("currency",newCurrency);
        newOrder.set("user",Parse.User.current());  //Setting current user here.
        newOrder.save(null,{
          success: function(newOrder) {
               console.log("New Order Created successfully");
                alert("Order Placed Successfully...");
            /*
             Fethcing the UserAddress
             */
            var userAddress= Parse.Object.extend("UserAddress");
            var query= new Parse.Query(userAddress);
            query.equalTo("user", Parse.User.current());
            console.log("Fetching User Address");
            query.find({
              success: function (userAddress) {
                if (userAddress.length == 0) {
                  alert("No UserAddress found");
                }
                /*
                 Creating Shipment for the Order.
                 */
                console.log("userAddress Found " + userAddress[0].id);
                var shipment = Parse.Object.extend("Shipment");
                var newShipment= new shipment();
                newShipment.set("status","InProgress");
                newShipment.save(null,{
                  success: function(newshipment){
                    newshipment.set("user",Parse.User.current());
                    newshipment.set("order",newOrder);
                    newshipment.set("userAddress",userAddress[0]);
                    newShipment.save(null,{
                      success: function(newShipment){
                        console.log("Shipment added successfully");
                      },
                      error: function (objec, error) {
                        console.log("Error Found" + error.message);
                      }

                    });
                  },
                  error: function (objec, error) {
                    console.log("Error Found" + error.message);
                  }

                });

              }, error: function (error) {
                alert("Error: " + error.code + " " + error.message);
              }
            });

                for (var i = 0; i < orderDetails.length; i++) {
                    console.log("orderDetails.length "+orderDetails.length);
                     newOrderItems= new orderItems();
                     newOrderItems.set("totalQty",1);

                  (function (i) {
                    newOrderItems.save(null, {
                      success: function (orderItems) {
                        newProduct = new product();
                        console.log("i is: " + i);
                        console.log("orderDetails[i]  " + i + "  " + orderDetails[i].get("product").id);
                        newProduct = orderDetails[i].get("product");
                        orderItems.set("product", newProduct);
                        orderItems.set("order", newOrder);
                        orderItems.save(null, {
                          success: function (orderItem) {

                          },
                          error: function (objec, error) {
                            console.log("Error Found" + error.message);
                          }
                        });
                      },
                      error: function (objec, error) {
                        console.log("Error Found" + error.message);
                      }
                    })
                  }(i));

              /*
              Test Code to use Bind
               */
              /*angular.bind(self, newOrderItems.save(null, {
                success: function (orderItems) {
                  newProduct = new product();
                  console.log("i is: " + i);
                  console.log("orderDetails[i]  " + i + "  " + orderDetails[i].get("product").id);
                  newProduct = orderDetails[i].get("product");
                  orderItems.set("product", newProduct);
                  orderItems.set("order", newOrder);
                  orderItems.save(null, {
                    success: function (orderItem) {
                      alert("OrderItems created Successfully...");
                    },
                    error: function (objec, error) {
                      console.log("Error Found" + error.message);
                    }
                  });
                },
                error: function (objec, error) {
                  console.log("Error Found" + error.message);
                }
              }),i);*/

                 }
                    alert("OrderItems created Successfully...");
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

})

