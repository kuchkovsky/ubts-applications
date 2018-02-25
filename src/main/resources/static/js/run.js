(function () {

    'use strict';

    var app = angular.module('ubtsApplSystem');

    app.run(function (authService) {
        if (window.navigator.appVersion.indexOf('wkhtmltopdf') === -1) {
            authService.checkLoginStatus();
        }
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
