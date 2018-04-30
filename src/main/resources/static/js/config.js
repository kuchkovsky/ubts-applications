(function () {

    'use strict';

    const app = angular.module('ubtsApplSystem');

    app.config(function ($httpProvider) {
        if (window.navigator.appVersion.indexOf('wkhtmltopdf') === -1) {
            $httpProvider.interceptors.push('authInterceptor');
        }
    });


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
        const resolveDelay = {
            delay: function($state, $timeout) {
                if ($state.current.name) {
                    return $timeout(() => {}, 300);
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
                url: '/students',
                templateUrl : 'templates/student-list.html',
                data : { pageTitle: 'Список абітурієнтів УБТС' },
                resolve: resolveDelay
            })
            .state('studentView', {
                url: "/students/{studentId}",
                templateUrl: 'templates/student-view.html',
                data: {pageTitle: 'Інформація про абітурієнта УБТС'},
                resolve: resolveDelay
            })
            .state('studentPrintView', {
                url: "/students/{studentId}/print",
                templateUrl: 'templates/student-view.html',
                data: {pageTitle: 'Інформація про абітурієнта УБТС'}
            })
            .state('login', {
                url: "/login",
                templateUrl: 'templates/login.html',
                data: {pageTitle: 'Вхід в систему УБТС'},
                resolve: resolveDelay
            });
    });

})();
