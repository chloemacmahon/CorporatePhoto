package za.ac.nwu.ac.domain.exception;

public class CouldNotUpdateAlbum extends RuntimeException{

    public CouldNotUpdateAlbum() {
        super("Could not update album");
    }

    public CouldNotUpdateAlbum(String message) {
        super(message);
    }

    public CouldNotUpdateAlbum(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotUpdateAlbum(Throwable cause) {
        super(cause);
    }

    public CouldNotUpdateAlbum(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
