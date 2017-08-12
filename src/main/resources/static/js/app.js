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
                templateUrl : 'templates/student.html',
                data : { pageTitle: 'Анкета абітурієнта УБТС' },
                resolve: resolveDelay
            });
    });

    app.run(['$rootScope', '$state', '$stateParams', function ($rootScope, $state, $stateParams) {
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;
    }]);

    app.run(function($transitions, $rootScope) {
        $transitions.onStart({ }, function(trans) {
            $rootScope.isSpinnerVisible = true;
        });
        $transitions.onFinish({ }, function(trans) {
            $rootScope.isSpinnerVisible = false;
        });
    });

    app.run(function($mdSidenav, $rootScope) {
        $rootScope.toggleSidebar = function () {
            $mdSidenav('left').toggle();
        };
    });

})();
