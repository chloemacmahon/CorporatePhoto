package za.ac.nwu.ac.domain.exception;

public class CouldNotDeletePhotoMetaDataException extends RuntimeException {

    public CouldNotDeletePhotoMetaDataException(){
        super("Could not delete photo metadata");
    }

    public CouldNotDeletePhotoMetaDataException(String message){
        super(message);
    }

    public CouldNotDeletePhotoMetaDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotDeletePhotoMetaDataException(Throwable cause) {
        super(cause);
    }

    public CouldNotDeletePhotoMetaDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
