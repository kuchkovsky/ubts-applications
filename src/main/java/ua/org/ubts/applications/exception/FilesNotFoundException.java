package ua.org.ubts.applications.exception;

public class FilesNotFoundException extends FileOperationException {

    public FilesNotFoundException() {
    }

    public FilesNotFoundException(String message) {
        super(message);
    }

}
