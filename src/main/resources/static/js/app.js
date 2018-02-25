/**
 * Created by Yaroslav on 14.07.2017.
 */
(function () {

    'use strict';

    var app = angular.module('ubtsApplSystem',
        ['ngMaterial', 'ngMessages', 'ngAnimate', 'lfNgMdFileInput', 'duScroll', 'ui.router']);

    app.service('authService', function ($rootScope, $window, $http, $location) {

        this.checkLoginStatus = function () {
            $rootScope.isAuthenticated = !!$window.localStorage.token;
        };

        this.login = function (login, password, onSuccess, onFailure) {
            var data = {};
            data.login = login;
            data.password = password;
            $http({
                url: '/api/auth/login',
                method: 'POST',
                data: data
            }).then(function (res) {
                $window.localStorage.token = res.headers('Authorization').replace("Bearer ", "");
                $rootScope.isAuthenticated = true;
                onSuccess();
            }, function () {
                delete $window.localStorage.token;
                $rootScope.isAuthenticated = false;
                onFailure();
            });
        };

        this.logout = function () {
            $rootScope.isAuthenticated = false;
            delete $window.localStorage.token;
            $location.path("/login");
        };

    });

    app.run(function (authService) {
        if (window.navigator.appVersion.indexOf('wkhtmltopdf') === -1) {
            authService.checkLoginStatus();
        }
    });

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
                    delete $window.localStorage.token;
                    $rootScope.isAuthenticated = false;
                    $location.path("/login");
                }
                return $q.reject(rejection);
            }
        };
    });

    app.config(function ($httpProvider) {
        if (window.navigator.appVersion.indexOf('wkhtmltopdf') === -1) {
            $httpProvider.interceptors.push('authInterceptor');
        }
    });

    app.service('downloadService', function ($http) {

        function download(url, onSuccess, onError) {
            $http.get(url).then(function (response) {
                onSuccess(response.data);
            }, function () {
                onError();
            });
        }

        this.getStudentList = function (onSuccess, onError) {
            download('/api/students/list/current', onSuccess, onError);
        };

        this.getStudent = function (id, onSuccess, onError) {
            download('/api/students/' + id, onSuccess, onError);
        };

        this.getPrograms = function (onSuccess, onError) {
            download('/api/programs', onSuccess, onError);
        };

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
            .state('studentPrintView', {
                url: "/view/student/{studentId}/print",
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

    app.run(['$rootScope', '$state', '$stateParams', '$http', '$location',
        function ($rootScope, $state, $stateParams) {
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;
    }]);

    app.run(function($transitions, $rootScope) {
        $transitions.onStart({ }, function(trans) {
            $rootScope.isMainSpinnerVisible = true;
        });
        $transitions.onFinish({ }, function(trans) {
            $rootScope.isMainSpinnerVisible = false;
        });
    });

    app.run(function($mdSidenav, $rootScope, $state, $http, $mdDialog, authService) {
        $rootScope.toggleSidebar = function () {
            $mdSidenav('left').toggle();
        };
        $rootScope.logout = function () {
            authService.logout();
        };
        $rootScope.fabOpen = true;
        $rootScope.exportToCloud = function() {
            if ($state.current.name === 'studentView') {
                var url = '/api/student/' + $rootScope.studentId + '/export/cloud';
            } else {
                var url = '/api/student/export/cloud';
            }
            $rootScope.exportState = {
                inProgress: true,
                success: false
            };
            function DialogController($scope, $mdDialog, exportState) {
                $scope.exportState = exportState;
                $scope.hide = function() {
                    $mdDialog.hide();
                };
            }
            $mdDialog.show({
                controller: DialogController,
                templateUrl: 'templates/export-alert.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                locals: {
                    exportState: $rootScope.exportState
                }
            });
            $http.post(url).then(function (response) {
                $rootScope.exportState.inProgress = false;
                $rootScope.exportState.success = true;
            }, function () {
                $rootScope.exportState.inProgress = false;
                $rootScope.exportState.success = false;
            });
        };
    });

})();
