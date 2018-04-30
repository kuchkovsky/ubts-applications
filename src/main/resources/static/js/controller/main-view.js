(function () {

    'use strict';

    const app = angular.module('ubtsApplSystem');

    app.controller('mainViewCtrl', function ($timeout, registrationService) {

        this.isRegistrationOpen = false;
        this.isLoading = true;

        registrationService.checkRegistrationStatus(() => {
            this.isRegistrationOpen = true;
            this.isLoading = false;
        }, () => this.isLoading = false);

        this.onChange = () => {
            this.isLoading = true;
            if (this.isRegistrationOpen) {
                registrationService.openRegistration(() => this.isLoading = false, () => {
                    $timeout(() => {
                        this.isLoading = false;
                        this.isRegistrationOpen = false;
                    }, 1000);
                });
            } else {
                registrationService.closeRegistration(() => this.isLoading = false, () => {
                    $timeout(() => {
                        this.isLoading = false;
                        this.isRegistrationOpen = true;
                    }, 1000);
                });
            }
        };

    });

})();
