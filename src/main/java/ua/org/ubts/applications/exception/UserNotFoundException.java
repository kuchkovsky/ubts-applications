package ua.org.ubts.applications.exception;

public class UserNotFoundException extends DatabaseItemNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
