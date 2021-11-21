package za.ac.nwu.ac.domain.exception;

public class PhotoNotInAlbumException extends RuntimeException{

    public PhotoNotInAlbumException() {
        super("Photo is not in album");
    }

    public PhotoNotInAlbumException(String message) {
        super(message);
    }

    public PhotoNotInAlbumException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhotoNotInAlbumException(Throwable cause) {
        super(cause);
    }

    public PhotoNotInAlbumException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
