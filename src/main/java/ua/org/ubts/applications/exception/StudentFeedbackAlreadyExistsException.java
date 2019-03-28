package ua.org.ubts.applications.exception;

public class StudentFeedbackAlreadyExistsException extends ConflictException {

    public StudentFeedbackAlreadyExistsException(String message) {
        super(message);
    }

}
