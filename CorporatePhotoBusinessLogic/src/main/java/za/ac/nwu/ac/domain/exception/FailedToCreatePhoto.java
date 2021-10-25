package za.ac.nwu.ac.domain.exception;

public class FailedToCreatePhoto extends RuntimeException{

    public FailedToCreatePhoto() {
        super("Failed to create photo");
    }

    public FailedToCreatePhoto(String message) {
        super(message);
    }

    public FailedToCreatePhoto(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToCreatePhoto(Throwable cause) {
        super(cause);
    }

    public FailedToCreatePhoto(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
