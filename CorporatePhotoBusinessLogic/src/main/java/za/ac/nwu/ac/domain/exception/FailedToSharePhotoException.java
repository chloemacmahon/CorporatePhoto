package za.ac.nwu.ac.domain.exception;

public class FailedToSharePhotoException extends RuntimeException{
    public FailedToSharePhotoException() {
        super("Failed to share photo");
    }

    public FailedToSharePhotoException(String message) {
        super(message);
    }

    public FailedToSharePhotoException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToSharePhotoException(Throwable cause) {
        super(cause);
    }

    public FailedToSharePhotoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
