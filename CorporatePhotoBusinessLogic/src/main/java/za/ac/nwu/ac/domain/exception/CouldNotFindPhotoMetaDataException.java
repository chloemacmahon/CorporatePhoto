package za.ac.nwu.ac.domain.exception;

public class CouldNotFindPhotoMetaDataException extends RuntimeException {

    public CouldNotFindPhotoMetaDataException() {
        super("Could not find photo metadata");
    }

    public CouldNotFindPhotoMetaDataException(String message) {
        super(message);
    }

    public CouldNotFindPhotoMetaDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotFindPhotoMetaDataException(Throwable cause) {
        super(cause);
    }

    public CouldNotFindPhotoMetaDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
