package za.ac.nwu.ac.domain.exception;

public class InvalidSurnameException extends RuntimeException{
    public InvalidSurnameException() {
        super("Surname is invalid");
    }

    public InvalidSurnameException(String message) {
        super(message);
    }

    public InvalidSurnameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSurnameException(Throwable cause) {
        super(cause);
    }

    public InvalidSurnameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
