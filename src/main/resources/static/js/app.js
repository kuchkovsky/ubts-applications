/**
 * Created by Yaroslav on 14.07.2017.
 */
(function () {

    'use strict';

    var app = angular.module('ubtsApplSystem',
        ['ngMaterial', 'ngMessages', 'ngAnimate', 'lfNgMdFileInput', 'duScroll', 'ui.router']);

    app.config(function($mdThemingProvider) {
        $mdThemingProvider.definePalette('ubtsBrownOrange', {
            '50': 'fbe9e7',
            '100': 'ffccbc',
            '200': 'ffab91',
            '300': 'ff8a65',
            '400': 'ff7043',
            '500': 'ff5722',
            '600': 'f4511e',
            '700': 'e64a19',
            '800': 'bf5e3b',
            '900': 'bf360c',
            'A100': 'ff9e80',
            'A200': 'ff6e40',
            'A400': 'ff3d00',
            'A700': 'dd2c00'
        });
        $mdThemingProvider.theme('default')
            .primaryPalette('ubtsBrownOrange')
            .accentPalette('red');
    });

    app.config(function($stateProvider, $urlRouterProvider) {
        var resolveDelay = {
            delay: function($state, $timeout) {
                if ($state.current.name) {
                    return $timeout(function(){}, 300);
                }
            }
        };
        $urlRouterProvider.otherwise('/');
        $stateProvider
            .state('home', {
                url: '/',
                templateUrl : 'templates/main.html',
                data : { pageTitle: 'UBTS Applications System' },
                resolve: resolveDelay
            })
            .state('studentApplication', {
                url: '/signup/student',
                templateUrl : 'templates/student-application.html',
                data : { pageTitle: 'Анкета абітурієнта УБТС' },
                resolve: resolveDelay
            })
            .state('studentList', {
                url: '/list/students',
                templateUrl : 'templates/student-list.html',
                data : { pageTitle: 'Список абітурієнтів УБТС' },
                resolve: resolveDelay
            })
            .state('studentView', {
                url: "/view/student/{studentId}",
                templateUrl: 'templates/student-view.html',
                data: {pageTitle: 'Інформація про абітурієнта УБТС'},
                resolve: resolveDelay
            })
            .state('login', {
                url: "/login",
                templateUrl: 'templates/login.html',
                data: {pageTitle: 'Вхід в систему УБТС'},
                resolve: resolveDelay
            });
    });

    app.run(['$rootScope', '$state', '$stateParams', '$http', '$location',
        function ($rootScope, $state, $stateParams, $http, $location) {
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;
            $rootScope.logout = function () {
                $http.post('auth/logout', {}).finally(function() {
                    $rootScope.authenticated = false;
                    $location.path("/");
                });
            };
            $http({method: 'GET', url: 'auth/refresh'}).then(function(response) {
                if (response.data.access_token !== null) {
                    $rootScope.authenticated = true;
                }
            });
    }]);

    app.run(function($transitions, $rootScope) {
        $transitions.onStart({ }, function(trans) {
            $rootScope.isMainSpinnerVisible = true;
        });
        $transitions.onFinish({ }, function(trans) {
            $rootScope.isMainSpinnerVisible = false;
        });
    });

    app.run(function($mdSidenav, $rootScope) {
        $rootScope.toggleSidebar = function () {
            $mdSidenav('left').toggle();
        };
    });

})();
