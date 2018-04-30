(function () {

    'use strict';

    const app = angular.module('ubtsApplSystem');

    app.controller('studentApplicationCtrl', function ($scope, $state, $http, $mdDialog, uploadService,
                                                       registrationService) {
        this.isRegistrationOpen = false;
        this.isLoading = true;

        registrationService.checkRegistrationStatus(() => {
            this.isLoading = false;
            this.isRegistrationOpen = true;
        }, () => this.isLoading = false);

        this.radios = {
            program: {
                bps: 'Пасторське служіння. Бакалавр. 4 роки',
                bcsOnc: 'Організація нових церков. Бакалавр. 4 роки',
                bcsOrcs: 'Розвиток церковних служінь. Бакалавр. 4 роки',
                bcsJs: 'Жіноче служіння',
                bmMm: 'Міжнародна місія',
                bms: 'Музичне служіння. Бакалавр. 4 роки',
                prml: 'Програма розвитку молодих лідерів. Сертифікат. 2 роки'
            },
            programInfo: {
                bcsJs: {
                    lviv2y: 'Львів. Сертифікат. 2 роки',
                    lviv4y: 'Львів. Бакалавр. 4 роки',
                    irpin2y: 'Ірпінь. Сертифікат. 2 роки',
                    irpin4y: 'Ірпінь. Бакалавр. 4 роки'
                },
                bm: {
                    twoYears: 'Сертифікат. 2 роки',
                    fourYears: 'Бакалавр. 4 роки'
                },
                prml: {
                    lviv: 'Львів',
                    volyn: 'Волинь',
                    zhytomyr: 'Житомир',
                    ivanofrankivsk: 'Івано-Франківськ'
                },
                bms: {
                    choir: 'Хоровий напрямок',
                    stage: 'Естрадний напрямок'
                },
                bcsOrcs: {
                    lviv: 'Львів',
                    irpin: 'Ірпінь'
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
                uah600: "600 грн.",
                uah700: "700 грн.",
                uah800: "800 грн.",
                uah900: "900 грн."
            },
            healthStatus: {
                excellent: "Відмінний",
                good: "Добрий",
                satisfactory: "Задовільний",
                unsatisfactory: "Незадовільний"
            },
            howFindOut: {
                leader: "Від керівника програми",
                pastor: "Від пастора",
                student: "Від студента",
                social: "Соціальні мережі",
                site: "Сайт УБТС",
                employee: "Від працівника семінарії",
                conference: "На конференції",
                other: "Інше"
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
            donationAmount: this.radios.donationAmount.uah700,
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
                    isSpouseApproveSeminary: true,
                    childrenNumber: 0
                },
                isChurchMember: true,
                churchData: {
                    membersNumber: this.radios.membersNumber.lessThen10
                },
                churchMinistry: {
                    type: this.radios.ministryType.pastor
                },
                howFindOut: this.radios.howFindOut.leader,
                isOrdained: true,
                otherMinistryType: null,
                isWorking: true
            },
            progressBar: {
                isVisible: false,
                counter: 0
            }
        };
        this.radios.programInfo.bcsJsTerm = this.radios.programInfo.bcsJs.lviv2y;
        this.radios.programInfo.bmTerm = this.radios.programInfo.bm.twoYears;
        this.radios.programInfo.bmsType = this.radios.programInfo.bms.choir;
        this.radios.programInfo.prmlRegion = this.radios.programInfo.prml.lviv;
        this.radios.programInfo.bcsOrcsRegion = this.radios.programInfo.bcsOrcs.lviv;
        this.minDate = new Date('1900');
        this.maxDate = new Date();
        this.years = [];
        for (let i = this.maxDate.getFullYear(); i >= 1920; i--) {
            this.years.push(i);
        }
        this.files = {};
        this.submitLock = false;

        $scope.$watch('ctrl.student.program.name', newValue => {
            if (newValue !== this.radios.program.prml) {
                $state.current.data.pageTitle = 'Анкета абітурієнта УБТС';
                this.form.logo = "img/ubts.png";
                this.student.donationAmount = this.radios.donationAmount.uah700;
            } else {
                $state.current.data.pageTitle = 'Анкета абітурієнта ПРМЛ';
                this.form.logo = "img/prml.png";
                this.student.donationAmount = this.radios.donationAmount.uah600;
            }
        });

        this.submit = () => {
            if (this.submitLock) {
                return;
            }
            this.submitLock = true;
            this.student.birthDate = moment(this.form.student.birthDate).format('YYYY-MM-DD');
            this.student.entryYear = moment().year();
            switch (this.student.program.name) {
                case this.radios.program.bcsOrcs:
                    this.student.program.info = this.radios.programInfo.bcsOrcsRegion;
                    break;
                case this.radios.program.bcsJs:
                    this.student.program.info = this.radios.programInfo.bcsJsTerm;
                    break;
                case this.radios.program.bmMm:
                    this.student.program.info = this.radios.programInfo.bmTerm;
                    break;
                case this.radios.program.bms:
                    this.student.program.info = this.radios.programInfo.bmsType;
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
                    this.student.maritalData.spouseChurchMinistry = this.form.student.maritalData.spouseChurchMinistry
                } else {
                    delete this.student.maritalData.spouseChurchMinistry;
                }
            } else {
                this.student.maritalData = {status: this.student.maritalData.status};
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
                this.form.student.churchData = {membersNumber: this.radios.membersNumber.lessThen10};
                this.form.student.churchMinistry = {type: this.radios.ministryType.pastor};
            }
            this.student.howFindOut = this.form.student.howFindOut;
            if (this.form.student.howFindOut === this.radios.howFindOut.other) {
                this.student.howFindOut = this.form.student.otherHowFindOutName;
            }

            function DialogController($scope, $mdDialog, links) {
                $scope.links = links;
                $scope.hide = () => $mdDialog.hide();
            }

            function showError(text) {
                const alert = $mdDialog.alert().title('Помилка реєстрації').textContent(text).ok('Закрити');
                $mdDialog.show(alert);
            }

            uploadService.sendStudentData(this.student, this.files, this.form.progressBar, status => {
                switch (status) {
                    case 201:
                        $mdDialog.show({
                            controller: DialogController,
                            templateUrl: 'templates/success-alert.html',
                            parent: angular.element(document.body),
                            clickOutsideToClose: true,
                            locals: {
                                links: this.student.program.name !== this.radios.program.prml ?
                                    this.links.default : this.links.prml
                            }
                        });
                        break;
                    case 409:
                        showError('Користувач з даним іменем вже зареєстрований в системі.');
                        break;
                    default:
                        showError('Не вдалося надіслати дані.');
                        break;
                }
                this.submitLock = false;
            });
        }

    });

})();
