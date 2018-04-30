package ua.org.ubts.applications.exception;

public class StudentAlreadyExistsException extends ConflictException {

    public StudentAlreadyExistsException() {
    }

    public StudentAlreadyExistsException(String message) {
        super(message);
    }

}
