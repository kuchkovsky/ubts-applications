(function () {

   'use strict';

    var app = angular.module('ubtsApplSystem');

    app.controller('studentViewCtrl', function ($rootScope, $stateParams, downloadService) {
        var self = this;
        $rootScope.studentId = $stateParams.studentId;
        $rootScope.isMainSpinnerVisible = true;
        downloadService.getStudent($stateParams.studentId, function (student) {
            self.student = student;
            $rootScope.isMainSpinnerVisible = false;
        }, function () {
            var alert = $mdDialog.alert().title('Помилка')
                .textContent('Не вдалося завантажити дані абітурієнта').ok('Закрити');
            $mdDialog.show(alert);
            $rootScope.isMainSpinnerVisible = false;
        });

    });

})();
