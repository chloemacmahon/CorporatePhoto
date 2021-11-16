package za.ac.nwu.ac.domain.exception;

public class AccessNotSufficientException extends RuntimeException{

    public AccessNotSufficientException() {
        super("Insufficient rights to perform this action");
    }

    public AccessNotSufficientException(String message) {
        super(message);
    }

    public AccessNotSufficientException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessNotSufficientException(Throwable cause) {
        super(cause);
    }

    public AccessNotSufficientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
