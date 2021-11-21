package za.ac.nwu.ac.domain.exception;

public class CouldNotDeletePhotoException extends RuntimeException{

    public CouldNotDeletePhotoException() {
        super("Could not delete photo");
    }

    public CouldNotDeletePhotoException(String message) {
        super(message);
    }

    public CouldNotDeletePhotoException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotDeletePhotoException(Throwable cause) {
        super(cause);
    }

    public CouldNotDeletePhotoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
