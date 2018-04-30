(function () {

    'use strict';

    const app = angular.module('ubtsApplSystem');

    app.service('authService', function ($rootScope, $window, $http, $location) {

        function url_base64_decode(str) {
            let output = str.replace('-', '+').replace('_', '/');
            switch (output.length % 4) {
                case 0:
                    break;
                case 2:
                    output += '==';
                    break;
                case 3:
                    output += '=';
                    break;
                default:
                    throw 'Illegal base64url string!';
            }
            return window.atob(output);
        }

        function isAdmin() {
            const token = $window.localStorage.token;
            if (token) {
                const encodedProfile = token.split('.')[1];
                const profile = JSON.parse(url_base64_decode(encodedProfile));
                return profile.roles.includes('ROLE_ADMIN');
            }
            return false;
        }

        this.checkLoginStatus = () => {
            $rootScope.isAuthenticated = !!$window.localStorage.token;
            $rootScope.isAdmin = isAdmin();
        };

        this.login = (credentials, onSuccess, onFailure) => {
            $http({
                url: '/api/auth/login',
                method: 'POST',
                data: credentials
            }).then(res => {
                $window.localStorage.token = res.headers('Authorization').replace('Bearer ', '');
            $rootScope.isAuthenticated = true;
            $rootScope.isAdmin = isAdmin();
            onSuccess();
        }, () => {
                delete $window.localStorage.token;
                $rootScope.isAuthenticated = false;
                $rootScope.isAdmin = false;
                onFailure();
            });
        };

        this.logout = () => {
            delete $window.localStorage.token;
            $rootScope.isAuthenticated = false;
            $rootScope.isAdmin = false;
            $location.path("/login");
        };

    });

})();
