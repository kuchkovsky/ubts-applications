(function () {

    'use strict';

    var app = angular.module('ubtsApplSystem');

    app.service('authService', function ($rootScope, $window, $http, $location) {

        this.checkLoginStatus = function () {
            $rootScope.isAuthenticated = !!$window.localStorage.token;
        };

        this.login = function (login, password, onSuccess, onFailure) {
            var data = {};
            data.login = login;
            data.password = password;
            $http({
                url: '/api/auth/login',
                method: 'POST',
                data: data
            }).then(function (res) {
                $window.localStorage.token = res.headers('Authorization').replace("Bearer ", "");
                $rootScope.isAuthenticated = true;
                onSuccess();
            }, function () {
                delete $window.localStorage.token;
                $rootScope.isAuthenticated = false;
                onFailure();
            });
        };

        this.logout = function () {
            $rootScope.isAuthenticated = false;
            delete $window.localStorage.token;
            $location.path("/login");
        };

    });

    app.service('registrationService', function ($http) {

        this.openRegistration = function (onSuccess, onFailure) {
            $http.put('/api/registration/open').then(function () {
                onSuccess();
            }, function () {
                onFailure();
            });
        };

        this.closeRegistration = function (onSuccess, onFailure) {
            $http.delete('/api/registration/open').then(function () {
                onSuccess();
            }, function () {
                onFailure();
            });
        };

        this.checkRegistrationStatus = function (onSuccess, onFailure) {
            $http.head('/api/registration/open').then(function () {
                onSuccess();
            }, function () {
                onFailure();
            });
        };

    });

    app.service('downloadService', function ($http) {

        function download(url, onSuccess, onError) {
            $http.get(url).then(function (response) {
                onSuccess(response.data);
            }, function () {
                onError();
            });
        }

        this.getStudentList = function (years, onSuccess, onError) {
            $http.get('/api/students/list', {
                params: {
                    year: years
                }
            }).then(function (response) {
                onSuccess(response.data);
            }, function () {
                onError();
            });
        };

        this.getStudent = function (id, onSuccess, onError) {
            download('/api/students/' + id, onSuccess, onError);
        };

        this.getPrograms = function (onSuccess, onError) {
            download('/api/programs', onSuccess, onError);
        };

        this.getYears = function (onSuccess, onError) {
            download('/api/years', onSuccess, onError);
        };

    });

    app.service('postService', function ($http) {
        this.sendStudentData = function (student, files, progressBar, callback) {
            if (typeof callback !== 'function') {
                return;
            }
            function sendFiles(postJsonStatus) {
                console.log('Checking existence of user files on server...');
                $http.head('/api/students/files/exist', {
                    params: {
                        first_name: student.firstName,
                        middle_name: student.middleName,
                        last_name: student.lastName
                    }
                }).then(function(response) {
                    console.log('Response: ' + response.status);
                    console.log('Error: user files already uploaded');
                    progressBar.isVisible = false;
                    callback(409);
                }, function(response) {
                    console.log('Response: ' + response.status);
                    var formData = new FormData();
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
                    $http.post('/api/students/files', formData, {
                        transformRequest: angular.identity,
                        headers: { 'Content-Type': undefined },
                        uploadEventHandlers: {
                            progress: function (e) {
                                if (e.lengthComputable) {
                                    progressBar.counter = (e.loaded / e.total) * 100;
                                }
                            }
                        }
                    }).then(function(response) {
                        console.log('Response: ' + response.status);
                        progressBar.isVisible = false;
                        callback(response.status);
                    }, function(response) {
                        progressBar.isVisible = false;
                        callback(response.status === 409 && postJsonStatus === 409 ? response.status : postJsonStatus);
                    });
                });
            }
            progressBar.counter = 0;
            progressBar.isVisible = true;
            var application = angular.element(document.getElementById('main-content-top'));
            application.scrollTop(application[0].scrollHeight, 500);
            console.log('Posting data: ' + JSON.stringify(student));
            $http.post('/api/students', student).then(function (response) {
                console.log('Response: ' + response.status);
                sendFiles(response.status);
            }, function (response) {
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
