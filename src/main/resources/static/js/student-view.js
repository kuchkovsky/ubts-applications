(function () {

   'use strict';

    var app = angular.module('ubtsApplSystem');

<<<<<<< HEAD
    app.controller('studentViewCtrl', function ($http, $stateParams) {
        var self = this;
        $http.get("/api/students/" + $stateParams.studentId).then(function (response) {
            self.data = response.data;
=======
    app.controller('studentViewCtrl', function ($scope, $http, $stateParams) {
        this.studentId = $stateParams.studentId;
        $http.get("/api/students/" + $stateParams.studentId).then(function (response) {
            $scope.firstName=response.data.firstName;
            $scope.middleName=response.data.middleName;
            $scope.lastName=response.data.lastName;
            $scope.program = response.data.program;
            $scope.birthDate = response.data.birthDate;
            $scope.phone1 = response.data.phone1;
            $scope.phone2 = response.data.phone2;
            $scope.email = response.data.email;
            $scope.residence = response.data.residence;
            $scope.education = response.data.education;
            $scope.maritalData = response.data.maritalData;
            $scope.isChurchMember = response.data.isChurchMember;
            $scope.churchData = response.data.churchData;
            $scope.churchMinistry = response.data.churchMinistry;
            $scope.job = response.data.job;
            $scope.healthData = response.data.healthData;
            $scope.donationAmount = response.data.donationAmount;
            $scope.financeComments = response.data.financeComments;
            $scope.financeComments = response.data.financeComments;
            $scope.reasonsToStudy = response.data.reasonsToStudy;
            $scope.howCameToGod = response.data.howCameToGod;
>>>>>>> 36a9e5e93fb339742c661d0725cfcbe28d830ecd
        });
    });

})();
