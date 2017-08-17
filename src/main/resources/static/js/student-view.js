(function () {

   'use strict';

    var app = angular.module('ubtsApplSystem');

    app.controller('studentViewCtrl', function ($http, $stateParams) {
        var self = this;
        $http.get("/api/students/" + $stateParams.studentId).then(function (response) {
            self.data = response.data;
        });
    });

})();
