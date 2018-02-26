(function () {

    'use strict';

    var app = angular.module('ubtsApplSystem');

    app.controller('studentListCtrl', function ($mdDialog, downloadService, $timeout) {
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
        this.groupList = true;
        this.currentNavItem = 'groupList';

        downloadService.getYears(function (years) {
            self.years = years;
            var currentYear = moment().year();
            self.years.forEach(function (year) {
                if (year.value === currentYear) {
                    year.enabled = true;
                } else {
                    year.enabled = false;
                }
            });
            self.onYearsChange();
        }, function () {
            var alert = $mdDialog.alert().title('Помилка').textContent('Не вдалося завантажити список').ok('Закрити');
            $mdDialog.show(alert);
        });

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

        this.onYearsChange = function () {
            self.isSpinnerVisible = true;
            var years = [];
            self.years.forEach(function (year) {
                if (year.enabled) {
                    years.push(year.value);
                }
            });
            if (years.length === 0) {
                $timeout(function () {
                    self.students = [];
                    self.form.students = [];
                    self.search();
                    self.isSpinnerVisible = false;
                }, 300);
                return;
            }
            downloadService.getStudentList(years, function (studentList) {
                self.students = studentList;
                self.form.students = self.students.slice();
                self.search();
                self.isSpinnerVisible = false;
            }, function () {
                var alert = $mdDialog.alert().title('Помилка').textContent('Не вдалося завантажити список').ok('Закрити');
                $mdDialog.show(alert);
                self.isSpinnerVisible = false;
            });
        }

    });

})();
