/**
 * Created by Yaroslav on 14.07.2017.
 */
(function () {

    'use strict';

    var app = angular.module('ubtsApplSystem',
        ['ngMaterial', 'ngMessages', 'ngAnimate', 'lfNgMdFileInput', 'duScroll', 'ui.router']);

    app.config(function($mdThemingProvider) {
        $mdThemingProvider.theme('default')
            .primaryPalette('blue')
            .accentPalette('pink');
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
                url: '/applications/student',
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
