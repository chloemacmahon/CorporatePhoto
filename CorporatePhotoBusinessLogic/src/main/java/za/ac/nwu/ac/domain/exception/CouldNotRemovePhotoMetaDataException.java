package za.ac.nwu.ac.domain.exception;

public class CouldNotRemovePhotoMetaDataException extends RuntimeException {

    public CouldNotRemovePhotoMetaDataException(){
        super("Could not remove photo metadata");
    }

    public CouldNotRemovePhotoMetaDataException(String message){
        super(message);
    }

    public CouldNotRemovePhotoMetaDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotRemovePhotoMetaDataException(Throwable cause) {
        super(cause);
    }

    public CouldNotRemovePhotoMetaDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
