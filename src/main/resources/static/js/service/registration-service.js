(function () {

    'use strict';

    const app = angular.module('ubtsApplSystem');

    app.service('registrationService', function ($http) {

        this.openRegistration = (onSuccess, onFailure) => {
            $http.post('/api/registration/students').then(onSuccess, onFailure);
        };

        this.closeRegistration = (onSuccess, onFailure) => {
            $http.delete('/api/registration/students').then(onSuccess, onFailure);
        };

        this.checkRegistrationStatus = (onSuccess, onFailure) => {
            $http.head('/api/registration/students').then(onSuccess, onFailure);
        };

    });

})();
