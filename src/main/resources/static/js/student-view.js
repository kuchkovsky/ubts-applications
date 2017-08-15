(function () {

   'use strict';

    var app = angular.module('ubtsApplSystem');

    app.controller('studentViewCtrl', function ($stateParams) {
        this.studentId = $stateParams.studentId;
    });

})();
