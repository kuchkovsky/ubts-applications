(function () {

    'use strict';

    var app = angular.module('ubtsApplSystem');

    app.controller('loginCtrl', function ($rootScope, $http, $location, authService) {
        var self = this;
        this.credentials = {};
        this.error = false;

        this.login = function() {
            authService.login(this.credentials.login, this.credentials.password,
                function () {
                    $location.path("/list/students");
                    self.error = false;
                },
                function () {
                    $location.path("/login");
                    self.error = true;
                });
        };

    });

})();
