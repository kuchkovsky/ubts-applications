(function () {

    'use strict';

    var app = angular.module('ubtsApplSystem');

    app.controller('studentListCtrl', function ($mdDialog, downloadService) {
        var self = this;

        this.students = [];
        this.form = {};
        this.programMap = {};

        this.search = function () {
            if (!self.form.query) {
                self.form.students = self.students.slice();
                return;
            }
            self.form.students = [];
            var query = self.form.query.toLowerCase();
            self.students.forEach(function (student) {
                if (student.name.toLowerCase().indexOf(query) !== -1
                    || student.program.name.toLowerCase().indexOf(query) !== -1
                    || self.programMap[query] === student.program.name) {
                    self.form.students.push(student);
                }
            });
        };

        this.isSpinnerVisible = true;
        downloadService.getStudentList(function (studentList) {
            self.students = studentList;
            self.form.students = self.students.slice();
            self.isSpinnerVisible = false;
            downloadService.getPrograms(function (programs) {
                self.programs = programs;
                self.programs.forEach(function (program) {
                    var words = program.name.split(' ');
                    var key = '';
                    words.forEach(function (word) {
                        key += word.substring(0, 1).toLowerCase();
                    });
                    self.programMap[key] = program.name;
                });
            }, function () {
                var alert = $mdDialog.alert().title('Помилка')
                    .textContent('Не вдалося завантажити список груп').ok('Закрити');
                $mdDialog.show(alert);
            });
        }, function () {
            var alert = $mdDialog.alert().title('Помилка').textContent('Не вдалося завантажити список').ok('Закрити');
            $mdDialog.show(alert);
            self.isSpinnerVisible = false;
        });

    });

})();
