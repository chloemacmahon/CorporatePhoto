package za.ac.nwu.ac.domain.exception;

public class FailedToDeleteUser extends RuntimeException{
    public FailedToDeleteUser() {
    }

    public FailedToDeleteUser(String message) {
        super(message);
    }

    public FailedToDeleteUser(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToDeleteUser(Throwable cause) {
        super(cause);
    }

    public FailedToDeleteUser(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
