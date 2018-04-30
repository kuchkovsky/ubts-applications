package ua.org.ubts.applications.exception;

public class FilesAlreadyUploadedException extends FileOperationException {

    public FilesAlreadyUploadedException() {
    }

    public FilesAlreadyUploadedException(String message) {
        super(message);
    }

}
