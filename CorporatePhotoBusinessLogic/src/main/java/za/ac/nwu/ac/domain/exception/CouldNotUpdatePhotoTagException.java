package za.ac.nwu.ac.domain.exception;

public class CouldNotUpdatePhotoTagException extends RuntimeException {

    public CouldNotUpdatePhotoTagException(){
        super("Could not update photo tag");
    }

    public CouldNotUpdatePhotoTagException(String message){
        super(message);
    }

    public CouldNotUpdatePhotoTagException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotUpdatePhotoTagException(Throwable cause) {
        super(cause);
    }

    public CouldNotUpdatePhotoTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
