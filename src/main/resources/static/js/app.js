/**
 * Created by Yaroslav on 14.07.2017.
 */
(function () {

    'use strict';

    var app = angular.module('ubtsApplSystem',
        ['ngMaterial', 'ngMessages', 'ngAnimate', 'lfNgMdFileInput', 'duScroll', 'ui.router']);
    app.config(function($mdThemingProvider) {
        $mdThemingProvider.theme('default')
            .primaryPalette('blue')
            .accentPalette('pink');
    });

    app.config(function($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/');
        $stateProvider
            .state('home', {
                url: '/',
                templateUrl : 'templates/main.html',
                data : { pageTitle: 'UBTS Applications System' }
            })
            .state('studentApplication', {
                url: '/applications/student',
                templateUrl : 'templates/student.html',
                data : { pageTitle: 'Анкета абітурієнта УБТС' },
                resolve: {
                    delay: function($state, $timeout) {
                        if ($state.current.name) {
                            return $timeout(function(){}, 300);
                        }
                    }
                }
            });
    });

    app.run(['$rootScope', '$state', '$stateParams', function ($rootScope, $state, $stateParams) {
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;
    }]);

    app.run(function($transitions, $rootScope) {
        $transitions.onStart({ }, function(trans) {
            $rootScope.spinner = true;
        });
        $transitions.onFinish({ }, function(trans) {
            $rootScope.spinner = false;
        });
    });

    app.controller('mainCtrl', function($http, $mdDialog, $document, $mdSidenav) {
        var self = this;
        this.toggleSidebar = function () {
            $mdSidenav('left').toggle();
        };
        this.prmlApplication = function(state)  {
            var top = angular.element(document.getElementById('main-content-top'));
            top.scrollTop(0, 800);
            state.current.data.pageTitle = 'Анкета абітурієнта ПРМЛ';
            self.form.logo = "img/prml.png";
        };
        this.radios = {
            program: {
                bps: 'Бакалавр пасторського служіння',
                bcsOnc: 'Бакалавр церковного служіння. Спеціалізація - організація нових церков',
                bcsOrcs: 'Бакалавр церковного служіння. Спеціалізація - організація та розвиток церковних служінь',
                bcsJs: 'Бакалавр церковного служіння. Спеціалізація - жіноче служіння',
                bmMm: 'Бакалавр місіології. Міжнародне місіонерство',
                bms: 'Бакалавр музичного служіння',
                prml: 'Програма розвитку молодих лідерів'
            },
            programInfo: {
                bcsJs: {
                    lviv2y: '2 роки - Львів',
                    lviv4y: '4 роки - Львів',
                    irpin2y: '2 роки - Ірпінь',
                    irpin4y: '4 роки - Ірпінь'
                },
                bm: {
                    twoYears: '2 роки',
                    fourYears: '4 роки'
                },
                prml: {
                    lviv: 'Львівська область',
                    rivne: 'Рівненська область',
                    khmel: 'Хмельницька область'
                }
            },
            education: {
                secondaryEducation: 'Середня',
                vocationalEducation: 'Середньо-спеціальна',
                incompleteHigherEducation: 'Незакінчена вища',
                higherEducation: 'Вища'
            },
            maritalStatus: {
                notMarried: "Неодружений/незаміжня",
                married: "Одружений/заміжня",
                widow: "Вдівець/вдова",
                divorced: "Розлучений/розлучена"
            },
            answers: {
                yes: "Так",
                no: "Ні"
            },
            membersNumber: {
                lessThen10: "до 10",
                from10to20: "10-20",
                from20to100: "20-100",
                from100to300: "100-300",
                greaterThen300: "більше 300"
            },
            ministryType: {
                pastor: "Пастор",
                evangelist: "Благовісник",
                smallGroupHead: "Керівник малої групи",
                deacon: "Диякон",
                musicMinistry: "Музичне служіння",
                sisterMinistry: "Сестринське служіння",
                missionary: "Місіонер",
                preacher: "Проповідник",
                youthLeader: "Лідер молоді",
                childMinistry: "Дитяче служіння",
                other: "Інше"
            },
            donationAmount: {
                uah500: "500 грн.",
                uah600: "600 грн.",
                uah700: "700 грн.",
                uah800: "800 грн."
            },
            healthStatus: {
                excellent: "Відмінний",
                good: "Добрий",
                satisfactory: "Задовільний",
                unsatisfactory: "Незадовільний"
            }
        };
        this.links = {
            default: {
                link1: "goo.gl/ifXbBG",
                link2: "goo.gl/FyL9qv",
                link3: "goo.gl/quHa4b"
            },
            prml: {
                link1: "goo.gl/vfDfJH",
                link2: "goo.gl/Eh3WKQ",
                link3: "goo.gl/7UxO2l"
            }
        };
        this.optionsArray = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
        this.student = {
            education: this.radios.education.secondaryEducation,
            donationAmount: this.radios.donationAmount.uah600,
            healthData: {
                isDrugAddicted: true,
                healthStatus: this.radios.healthStatus.excellent,
                isTakingMedicine: true,
                hasStudyProblems: true
            }
        };
        this.student.residence = {};
        this.student.program = {
            name: this.radios.program.bps
        };
        this.student.maritalData = {
            status: this.radios.maritalStatus.notMarried
        };
        this.form = {
            logo: "img/ubts.png",
            student: {
                maritalData: {
                    isSpouseChurchMember: true,
                    isSpouseApproveSeminary : true,
                    childrenNumber : 0
                },
                isChurchMember : true,
                churchData: {
                    membersNumber: this.radios.membersNumber.lessThen10
                },
                churchMinistry: {
                    type: this.radios.ministryType.pastor
                },
                isOrdained: true,
                otherMinistryType: null,
                isWorking: true
            }
        };
        this.radios.programInfo.bcsJsTerm = this.radios.programInfo.bcsJs.lviv2y;
        this.radios.programInfo.bmTerm = this.radios.programInfo.bm.twoYears;
        this.radios.programInfo.prmlRegion = this.radios.programInfo.prml.lviv;
        this.minDate = new Date('1900');
        this.maxDate = new Date();
        this.years = [];
        for (var i = this.maxDate.getFullYear(); i >= 1920; i--) {
            this.years.push(i);
        }
        this.files = {};
        this.submit = function () {
            this.student.birthDate = moment(this.form.student.birthDate).format('YYYY-MM-DD');
            switch (this.student.program.name) {
                case this.radios.program.bcsJs:
                    this.student.program.info = this.radios.programInfo.bcsJsTerm;
                    break;
                case this.radios.program.bmMm:
                    this.student.program.info = this.radios.programInfo.bmTerm;
                    break;
                case this.radios.program.prml:
                    this.student.program.info = this.radios.programInfo.prmlRegion;
                    break;
                default:
                    delete this.student.program.info;
                    break;
            }
            if (this.student.maritalData.status === this.radios.maritalStatus.married) {
                this.student.maritalData.isSpouseChurchMember = this.form.student.maritalData.isSpouseChurchMember;
                this.student.maritalData.isSpouseApproveSeminary = this.form.student.maritalData.isSpouseApproveSeminary;
                this.student.maritalData.childrenNumber = this.form.student.maritalData.childrenNumber;
                this.student.maritalData.marriageDate
                    = moment(this.form.student.maritalData.marriageDate).format('YYYY-MM-DD');
                if (this.form.student.maritalData.isSpouseChurchMember) {
                    this.student.maritalData.spouseChurchService = this.form.student.maritalData.spouseChurchService
                } else {
                    delete this.student.maritalData.spouseChurchService;
                }
            } else {
                this.student.maritalData = { status: this.student.maritalData.status };
            }
            if (this.form.student.isChurchMember) {
                if (!this.form.student.isOrdained) {
                    delete this.form.student.churchMinistry.ordinationDate;
                }
                this.student.churchData = this.form.student.churchData;
                this.student.churchMinistry = this.form.student.churchMinistry;
                if (this.form.student.churchMinistry.type === this.radios.ministryType.other) {
                    this.student.churchMinistry.type = this.form.student.otherMinistryType;
                }
            } else {
                delete this.student.churchData;
                delete this.student.churchMinistry;
                this.form.student.churchData = { membersNumber: this.radios.membersNumber.lessThen10 };
                this.form.student.churchMinistry = { type: this.radios.ministryType.pastor };
            }
            function DialogController($scope, $mdDialog, links) {
                $scope.links = links;
                $scope.hide = function() {
                    $mdDialog.hide();
                };
            }
            function showSuccess() {
                $mdDialog.show({
                    controller: DialogController,
                    templateUrl: 'templates/success.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose:true,
                    locals: {
                        links: self.student.program.name !== self.radios.program.prml ?
                            self.links.default : self.links.prml
                    }
                });
            }
            function showError(text) {
                var alert = $mdDialog.alert().title('Помилка реєстрації').textContent(text).ok('Закрити');
                $mdDialog.show(alert);
            }
            function sendFiles(postJsonStatus) {
                var formData = new FormData();
                formData.append('firstName', self.student.firstName);
                formData.append('middleName', self.student.middleName);
                formData.append('lastName', self.student.lastName);
                formData.append('photo', self.files.photo[0].lfFile);
                formData.append('passport1', self.files.passport1[0].lfFile);
                formData.append('passport2', self.files.passport2[0].lfFile);
                formData.append('passport3', self.files.passport3[0].lfFile);
                formData.append('idNumber', self.files.idNumber[0].lfFile);
                formData.append('diploma1', self.files.diploma1[0].lfFile);
                formData.append('diploma2', self.files.diploma2[0].lfFile);
                formData.append('medicalReference', self.files.medicalReference[0].lfFile);
                console.log('Posting files...');
                $http.post('/api/files/student', formData, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).then(function(response){
                    console.log('Response: ' + response.status);
                    showSuccess();
                },function(response){
                    var alertText = null;
                    if (response.status === 409 && postJsonStatus === 409) {
                        alertText = 'Користувач з даним іменем вже зареєстрований в системі.';
                    } else {
                        alertText = 'Не вдалося надіслати дані.';
                    }
                    showError(alertText);
                });
            }
            console.log('Posting data: ' + JSON.stringify(this.student));
            $http.post('/api/students', this.student).then(function(response) {
                console.log('Response: ' + response.status);
                sendFiles(response.status);
            }, function(response) {
                console.log('Error: ' + JSON.stringify(response.data));
                if (response.status === 409) {
                    sendFiles(response.status);
                } else {
                    var alertText = 'Не вдалося надіслати дані.';
                    showError(alertText);
                }
            });
        }
    });

})();
