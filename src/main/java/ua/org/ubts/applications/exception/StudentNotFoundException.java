package ua.org.ubts.applications.exception;

public class StudentNotFoundException extends DatabaseItemNotFoundException {

    public StudentNotFoundException(String message) {
        super(message);
    }

}
