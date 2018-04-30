(function () {

    'use strict';

    const app = angular.module('ubtsApplSystem');

    app.controller('loginCtrl', function ($rootScope, $http, $location, authService) {

        this.credentials = {};
        this.error = false;

        this.login = () => {
            authService.login(this.credentials, () => $location.path("/students"), () => this.error = true);
        };

    });

})();
