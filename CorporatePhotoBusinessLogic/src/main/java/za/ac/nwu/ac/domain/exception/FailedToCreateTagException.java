package za.ac.nwu.ac.domain.exception;

public class FailedToCreateTagException extends RuntimeException{

    public FailedToCreateTagException() {
        super("Failed to create tag");
    }

    public FailedToCreateTagException(String message) {
        super(message);
    }

    public FailedToCreateTagException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToCreateTagException(Throwable cause) {
        super(cause);
    }

    public FailedToCreateTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
