(function () {

    'use strict';

    const app = angular.module('ubtsApplSystem');

    app.controller('studentListCtrl', function ($mdDialog, $timeout, downloadService, studentService, $rootScope) {

        this.students = [];
        this.form = {};
        this.programMap = {};

        this.search = () => {
            if (!this.form.query) {
                this.form.students = this.students.slice();
                return;
            }
            this.form.students = [];
            const query = this.form.query.toLowerCase();
            this.students.forEach(student => {
                if (student.name.toLowerCase().indexOf(query) !== -1
                    || student.program.name.toLowerCase().indexOf(query) !== -1
                    || this.programMap[query] === student.program.name) {
                    this.form.students.push(student);
                }
            });
        };

        this.isSpinnerVisible = true;
        this.groupList = true;
        this.currentNavItem = 'groupList';

        downloadService.getYears(years => {
            this.years = years;
            const currentYear = moment().year();
            this.years.forEach(year => year.enabled = year.value === currentYear);
            this.onYearsChange();
        }, () => {
            const alert = $mdDialog.alert().title('Помилка').textContent('Не вдалося завантажити список').ok('Закрити');
            $mdDialog.show(alert);
        });

        downloadService.getPrograms(programs => {
            this.programs = programs;
            this.programs.forEach(program => {
                const words = program.name.split(' ');
                let key = '';
                words.forEach(word => key += word.substring(0, 1).toLowerCase());
                this.programMap[key] = program.name;
            });
        }, () => {
            const alert = $mdDialog.alert().title('Помилка')
                .textContent('Не вдалося завантажити список груп').ok('Закрити');
            $mdDialog.show(alert);
        });

        this.onYearsChange = () => {
            this.isSpinnerVisible = true;
            const years = [];
            this.years.forEach(year => year.enabled && years.push(year.value));
            if (years.length === 0) {
                $timeout(() => {
                    this.students = [];
                    this.form.students = [];
                    this.search();
                    this.isSpinnerVisible = false;
                }, 300);
                return;
            }
            downloadService.getStudentList(years, studentList => {
                this.students = studentList;
                $rootScope.years = years;
                this.form.students = this.students.slice();
                this.search();
                this.isSpinnerVisible = false;
            }, () => {
                const alert = $mdDialog.alert().title('Помилка')
                    .textContent('Не вдалося завантажити список').ok('Закрити');
                $mdDialog.show(alert);
                this.isSpinnerVisible = false;
            });
        };

        this.deleteStudent = student => {
            const confirm = $mdDialog.confirm()
                .title('Підтвердження операції')
                .textContent('Ви дійсно бажаєте видалити анкету абітурієнта?')
                .ok('Так')
                .cancel('Ні');
            $mdDialog.show(confirm).then(() => {
                studentService.deleteStudent(student, () => {
                    this.students = this.students.filter(s => s.id !== student.id);
                    this.form.students = this.form.students.filter(s => s.id !== student.id);
                }, () => {
                    const alert = $mdDialog.alert().title('Помилка')
                        .textContent('Не вдалося видалити анкету абітурієнта').ok('Закрити');
                    $mdDialog.show(alert);
                });
            });
        };

    });

})();
