package ua.org.ubts.applicationssystem.model;

import org.springframework.web.multipart.MultipartFile;

public class StudentFilesUploadModel {

    private String firstName;

    private String middleName;

    private String lastName;

    private MultipartFile photo;

    private MultipartFile passport1;

    private MultipartFile passport2;

    private MultipartFile passport3;

    private MultipartFile idNumber;

    private MultipartFile diploma1;

    private MultipartFile diploma2;

    private MultipartFile medicalReference;

    public StudentFilesUploadModel() {}

    public StudentFilesUploadModel(String firstName, String middleName, String lastName, MultipartFile photo,
                                   MultipartFile passport1, MultipartFile passport2, MultipartFile passport3,
                                   MultipartFile idNumber, MultipartFile diploma1, MultipartFile diploma2,
                                   MultipartFile medicalReference) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.photo = photo;
        this.passport1 = passport1;
        this.passport2 = passport2;
        this.passport3 = passport3;
        this.idNumber = idNumber;
        this.diploma1 = diploma1;
        this.diploma2 = diploma2;
        this.medicalReference = medicalReference;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public MultipartFile getPassport1() {
        return passport1;
    }

    public void setPassport1(MultipartFile passport1) {
        this.passport1 = passport1;
    }

    public MultipartFile getPassport2() {
        return passport2;
    }

    public void setPassport2(MultipartFile passport2) {
        this.passport2 = passport2;
    }

    public MultipartFile getPassport3() {
        return passport3;
    }

    public void setPassport3(MultipartFile passport3) {
        this.passport3 = passport3;
    }

    public MultipartFile getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(MultipartFile idNumber) {
        this.idNumber = idNumber;
    }

    public MultipartFile getDiploma1() {
        return diploma1;
    }

    public void setDiploma1(MultipartFile diploma1) {
        this.diploma1 = diploma1;
    }

    public MultipartFile getDiploma2() {
        return diploma2;
    }

    public void setDiploma2(MultipartFile diploma2) {
        this.diploma2 = diploma2;
    }

    public MultipartFile getMedicalReference() {
        return medicalReference;
    }

    public void setMedicalReference(MultipartFile medicalReference) {
        this.medicalReference = medicalReference;
    }

}
