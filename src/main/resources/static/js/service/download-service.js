(function () {

    'use strict';

    const app = angular.module('ubtsApplSystem');

    app.service('downloadService', function ($http) {

        function download(url, onSuccess, onError) {
            $http.get(url).then(response => onSuccess(response.data), onError);
        }

        this.getStudentList = (years, onSuccess, onError) => {
            $http.get('/api/students/list', {
                params: {
                    year: years
                }
            }).then(response => onSuccess(response.data), onError);
        };

        this.getStudent = (id, onSuccess, onError) => {
            download('/api/students/' + id, onSuccess, onError);
        };

        this.getPrograms = (onSuccess, onError) => {
            download('/api/programs', onSuccess, onError);
        };

        this.getYears = (onSuccess, onError) => {
            download('/api/years', onSuccess, onError);
        };

        function secureStudentFilesDownload(studentId, onError) {
            download('/api/tokens/files/students/' + studentId, data => {
                const anchor = angular.element('<a/>');
                anchor.attr({
                    href: 'api/files/students/' + studentId + '?token=' + data.token,
                })[0].click();
                anchor.remove();
            }, onError);
        }

        this.getStudentFiles = (studentId, onError) => secureStudentFilesDownload(studentId, onError);

    });

})();
