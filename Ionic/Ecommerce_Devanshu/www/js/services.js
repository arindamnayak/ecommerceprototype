angular.module('app.services', [])

    .factory('Utils', [function () {
        var service = {
            isUndefinedOrNull: function (obj) {
                return !angular.isDefined(obj) || obj === null;
            },
            logout: function () {
                Parse.User.logOut();
            },
            Order: function () {
                return Math.random().toString(36).substring(7);
            }
        }

        return service;
    }])

    .service('MyCategoryService', [function () {
        var savedData = {};

        function set(data) {
            if (data)
                savedData = data;

        }

        function get() {
            var value = savedData;
            // savedData={};
            console.log(savedData);
            return value;
        }

        return {
            set: set,
            get: get
        }
    }])
    .service('MyProductService', [function () {
        var savedData = {};

        function set(data) {
            if (data)
                savedData = data;

        }

        function get() {

            var value = savedData;
            // savedData={};
            console.log(savedData);
            return value;
        }

        return {
            set: set,
            get: get
        }

    }])


    .service('Payment1', [function () {
        var savedData = {};
        var products = {};

        function set(data) {
            if (data)
                savedData = data;

        }

        function setItems(items) {
            if (items)
                products = items;

        }

        function get() {

            var value = savedData;
            // savedData={};
            console.log(savedData);
            return value;
        }

        function getItems() {

            return products;
        }

        return {
            setItems: setItems,
            getItems: getItems,
            set: set,
            get: get
        }

    }])

    .factory('CheckOut', function () {

        var items = Parse.Object.extend("Cart");
        var query = new Parse.Query(items);
        query.equalTo('user', Parse.User.current());
        var promise = query.find({
            success: function (Cart) {
                console.log("Items in cart " + Cart.length);
                return Cart;
            }, error: function (error) {
                alert("Error: " + error.code + " " + error.message);
            }
        });

        return promise;
    })

    .factory('RestAPI', function ($http) {

        var service = {
            getPayment: function () {
                return $http.get('http://172.19.24.34:8080/api/v1/display', {});
            }
            ,

            updateOrder: function (Order) {
                return $http.post('http://172.19.24.34:8080/api/v1/update', Order, {
                    headers: {'Content-Type': 'application/json'}
                });
            }
        }
        return service;
    })


;

