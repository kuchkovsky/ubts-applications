package ua.org.ubts.applications.exception;

public class DatabaseItemNotFoundException extends ServiceException {

    public DatabaseItemNotFoundException() {
    }

    public DatabaseItemNotFoundException(String message) {
        super(message);
    }

}
