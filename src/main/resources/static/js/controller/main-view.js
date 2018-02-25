(function () {

    'use strict';

    var app = angular.module('ubtsApplSystem');

    app.controller('mainViewCtrl', function ($timeout, registrationService) {

        var self = this;
        self.isRegistrationOpen = false;
        self.isLoading = true;

        registrationService.checkRegistrationStatus(function () {
            self.isRegistrationOpen = true;
            self.isLoading = false;
        }, function(){
            self.isLoading = false;
        });

        this.onChange = function () {
            self.isLoading = true;
            if (self.isRegistrationOpen) {
                registrationService.openRegistration(function () {
                    self.isLoading = false;
                }, function(){
                    $timeout(function () {
                        self.isLoading = false;
                        self.isRegistrationOpen = false;
                    }, 1000);
                });
            } else {
                registrationService.closeRegistration(function () {
                    self.isLoading = false;
                }, function(){
                    $timeout(function () {
                        self.isLoading = false;
                        self.isRegistrationOpen = true;
                    }, 1000);
                });
            }
        };

    });

})();
