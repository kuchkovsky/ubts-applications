(function () {

    'use strict';

    var app = angular.module('ubtsApplSystem');

    app.controller('loginCtrl', function ($rootScope, $http, $location, $httpParamSerializerJQLike) {
        var self = this;
        this.credentials = {};
        this.error = false;

        this.login = function() {
            $http({
                url: 'auth/login',
                method: 'POST',
                data: $httpParamSerializerJQLike(self.credentials),
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(function (res) {
                $rootScope.authenticated = true;
                $location.path("/list/students");
                self.error = false;
            }).catch(function () {
                $rootScope.authenticated = false;
                $location.path("/login");
                self.error = true;
            });
        };

    });

})();
