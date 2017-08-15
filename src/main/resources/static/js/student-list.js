(function () {

    'use strict';

    var app = angular.module('ubtsApplSystem');

    app.service('downloadService', function ($http) {
        this.getStudentList = function (onSuccess, onError) {
            $http.get('/api/students/list').then(function (response) {
                onSuccess(response.data);
            }, function () {
                onError();
            });
        };
    });

    app.controller('studentListCtrl', function ($mdDialog, downloadService) {
        var self = this;

        this.students = [];
        this.form = {};

        this.search = function () {
            if (!self.form.query) {
                return;
            }
            self.form.students = [];
            var query = self.form.query.toLowerCase();
            self.students.forEach(function (student) {
                if (query === '' || student.name.toLowerCase().indexOf(query) !== -1) {
                    self.form.students.push(student);
                }
            });
        };

        this.isSpinnerVisible = true;
        downloadService.getStudentList(function (studentList) {
            self.students = studentList;
            self.form.students = self.students.slice();
            self.isSpinnerVisible = false;
        }, function () {
            var alert = $mdDialog.alert().title('Помилка').textContent('Не вдалося завантажити список').ok('Закрити');
            $mdDialog.show(alert);
            self.isSpinnerVisible = false;
        });

    });

})();
