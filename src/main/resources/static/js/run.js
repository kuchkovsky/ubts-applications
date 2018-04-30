(function () {

    'use strict';

    const app = angular.module('ubtsApplSystem');

    app.run(function (authService) {
        if (window.navigator.appVersion.indexOf('wkhtmltopdf') === -1) {
            authService.checkLoginStatus();
        }
    });

    app.run(function ($rootScope, $state, $stateParams) {
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
    });

    app.run(function($transitions, $rootScope) {
        $transitions.onStart({}, function() {
            $rootScope.isMainSpinnerVisible = true;
        });
        $transitions.onFinish({}, function() {
            $rootScope.isMainSpinnerVisible = false;
        });
    });

    app.run(function($mdSidenav, $rootScope, $state, $http, $mdDialog, authService, downloadService) {
        $rootScope.toggleSidebar = () => $mdSidenav('left').toggle();
        $rootScope.logout = () => authService.logout();
        $rootScope.fabOpen = true;
        $rootScope.exportToCloud = () => {
            let url;
            if ($state.current.name === 'studentView') {
                url = '/api/export/students/' + $rootScope.studentId + '/cloud';
            } else {
                url = '/api/export/students/cloud';
            }
            $rootScope.exportState = {
                inProgress: true,
                success: false
            };
            function DialogController($scope, $mdDialog, exportState) {
                $scope.exportState = exportState;
                $scope.hide = () => $mdDialog.hide();
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
            $http.post(url).then(() => {
                $rootScope.exportState.inProgress = false;
                $rootScope.exportState.success = true;
            }, () => {
                $rootScope.exportState.inProgress = false;
                $rootScope.exportState.success = false;
            });
        };


        $rootScope.exportToExcel = () => {
            const url = '/api/export/students/cloud/excel';
            $rootScope.exportState = {
                inProgress: true,
                success: false
            };
            function DialogController($scope, $mdDialog, exportState) {
                $scope.exportState = exportState;
                $scope.hide = () => $mdDialog.hide();
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
            $http.post(url).then(() => {
                $rootScope.exportState.inProgress = false;
                $rootScope.exportState.success = true;
            }, () => {
                $rootScope.exportState.inProgress = false;
                $rootScope.exportState.success = false;
            });
        };

        $rootScope.getStudentFiles = studentId => {
            downloadService.getStudentFiles(studentId, () => {
                const alert = $mdDialog.alert().title('Помилка').textContent('Не вдалося завантажити файл').ok('Закрити');
                $mdDialog.show(alert);
            });
        }

    });

})();
