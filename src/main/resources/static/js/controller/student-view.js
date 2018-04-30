(function () {

   'use strict';

    const app = angular.module('ubtsApplSystem');

    app.controller('studentViewCtrl', function ($rootScope, $stateParams, $mdDialog, downloadService) {

        $rootScope.studentId = $stateParams.studentId;
        $rootScope.isMainSpinnerVisible = true;

        downloadService.getStudent($stateParams.studentId, student => {
            this.student = student;
            $rootScope.isMainSpinnerVisible = false;
        }, () => {
            const alert = $mdDialog.alert().title('Помилка')
                .textContent('Не вдалося завантажити дані абітурієнта').ok('Закрити');
            $mdDialog.show(alert);
            $rootScope.isMainSpinnerVisible = false;
        });

    });

})();
