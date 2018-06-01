(function () {

    'use strict';

    const app = angular.module('ubtsApplSystem');

    app.service('uploadService', function ($http) {
        this.sendStudentData = (student, files, progressBar, callback) => {
            if (typeof callback !== 'function') {
                return;
            }
            function sendFiles(postJsonStatus) {
                console.log('Checking existence of user files on server...');
                $http.head('/api/files/students', {
                    params: {
                        first_name: student.firstName,
                        middle_name: student.middleName,
                        last_name: student.lastName
                    }
                }).then(response => {
                    console.log('Response: ' + response.status);
                    console.log('Error: user files already uploaded');
                    progressBar.isVisible = false;
                    callback(409);
                }, response => {
                    console.log('Response: ' + response.status);
                    const formData = new FormData();
                    formData.append('firstName', student.firstName);
                    formData.append('middleName', student.middleName);
                    formData.append('lastName', student.lastName);
                    formData.append('photo', files.photo[0].lfFile);
                    formData.append('passport1', files.passport1[0].lfFile);
                    formData.append('passport2', files.passport2[0].lfFile);
                    formData.append('passport3', files.passport3[0].lfFile);
                    formData.append('idNumber', files.idNumber[0].lfFile);
                    formData.append('diploma1', files.diploma1[0].lfFile);
                    formData.append('diploma2', files.diploma2[0].lfFile);
                    formData.append('medicalReference',files.medicalReference[0].lfFile);
                    console.log('Posting files...');
                    $http.post('/api/files/students', formData, {
                        transformRequest: angular.identity,
                        headers: { 'Content-Type': undefined },
                        uploadEventHandlers: {
                            progress: function (e) {
                                if (e.lengthComputable) {
                                    progressBar.counter = (e.loaded / e.total) * 100;
                                }
                            }
                        }
                    }).then(response => {
                        console.log('Response: ' + response.status);
                        progressBar.isVisible = false;
                        callback(response.status);
                    }, response => {
                        progressBar.isVisible = false;
                        callback(response.status);
                    });
                });
            }
            progressBar.counter = 0;
            progressBar.isVisible = true;
            const application = angular.element(document.getElementById('main-content-top'));
            application.scrollTop(application[0].scrollHeight, 500);
            console.log('Posting data: ' + JSON.stringify(student));
            $http.post('/api/students', student).then(response => {
                console.log('Response: ' + response.status);
                sendFiles(response.status);
            }, response => {
                console.log('Response: ' + response.status);
                console.log('Error: ' + JSON.stringify(response.data));
                if (response.status === 409) {
                    sendFiles(response.status)
                } else {
                    progressBar.isVisible = false;
                    callback(response.status);
                }
            });
        };

    });

})();
