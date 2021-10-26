package za.ac.nwu.ac.domain.exception;

public class FailedToCreatePhotoException extends RuntimeException{

    public FailedToCreatePhotoException() {
        super("Failed to create photo");
    }

    public FailedToCreatePhotoException(String message) {
        super(message);
    }

    public FailedToCreatePhotoException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToCreatePhotoException(Throwable cause) {
        super(cause);
    }

    public FailedToCreatePhotoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
