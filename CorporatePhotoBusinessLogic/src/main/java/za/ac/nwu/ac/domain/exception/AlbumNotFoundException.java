package za.ac.nwu.ac.domain.exception;

public class AlbumNotFoundException extends RuntimeException{
    public AlbumNotFoundException() {
        super("Album not found");
    }

    public AlbumNotFoundException(String message) {
        super(message);
    }

    public AlbumNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlbumNotFoundException(Throwable cause) {
        super(cause);
    }

    public AlbumNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
