package za.ac.nwu.ac.domain.exception;

public class FailedToShareAlbum extends RuntimeException{
    public FailedToShareAlbum() {
    }

    public FailedToShareAlbum(String message) {
        super(message);
    }

    public FailedToShareAlbum(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToShareAlbum(Throwable cause) {
        super(cause);
    }

    public FailedToShareAlbum(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
