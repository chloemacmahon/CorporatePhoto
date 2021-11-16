package za.ac.nwu.ac.domain.exception;

public class FailedToRemoveAccess extends RuntimeException{

    public FailedToRemoveAccess() {
    }

    public FailedToRemoveAccess(String message) {
        super(message);
    }

    public FailedToRemoveAccess(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToRemoveAccess(Throwable cause) {
        super(cause);
    }

    public FailedToRemoveAccess(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
