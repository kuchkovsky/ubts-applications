(function () {

    'use strict';

    const app = angular.module('ubtsApplSystem');

    app.service('studentService', function ($http) {

        this.deleteStudent = (student, onSuccess, onError) => {
            $http({
                url: '/api/students/' + student.id,
                method: 'DELETE'
            }).then(onSuccess, onError);
        };

    });

})();
