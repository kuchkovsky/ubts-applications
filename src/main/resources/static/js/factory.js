(function () {

    'use strict';

    const app = angular.module('ubtsApplSystem');

    app.factory('authInterceptor', function ($rootScope, $q, $window, $location) {
        return {
            request: function (config) {
                config.headers = config.headers || {};
                if ($window.localStorage.token) {
                    config.headers.Authorization = 'Bearer ' + $window.localStorage.token;
                }
                return config;
            },
            responseError: function (rejection) {
                if (rejection.status === 401 || rejection.status === 403) {
                    if ($window.localStorage.token) {
                        delete $window.localStorage.token;
                        $rootScope.isAuthenticated = false;
                        $rootScope.isAdmin = false;
                        location.reload();
                    }
                    $location.path("/login");
                }
                return $q.reject(rejection);
            }
        };
    });

})();
