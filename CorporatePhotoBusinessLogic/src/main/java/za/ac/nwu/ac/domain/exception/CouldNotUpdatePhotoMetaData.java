package za.ac.nwu.ac.domain.exception;

public class CouldNotUpdatePhotoMetaData extends RuntimeException {

    public CouldNotUpdatePhotoMetaData(){
        super("Could not update photo metadata");
    }

    public CouldNotUpdatePhotoMetaData(String message){
        super(message);
    }

    public CouldNotUpdatePhotoMetaData(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotUpdatePhotoMetaData(Throwable cause) {
        super(cause);
    }

    public CouldNotUpdatePhotoMetaData(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
