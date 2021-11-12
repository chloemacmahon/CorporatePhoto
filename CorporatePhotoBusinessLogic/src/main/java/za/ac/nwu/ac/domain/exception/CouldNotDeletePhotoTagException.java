package za.ac.nwu.ac.domain.exception;

public class CouldNotDeletePhotoTagException extends RuntimeException {

    public CouldNotDeletePhotoTagException(){
        super("Could not delete photo tag");
    }

    public CouldNotDeletePhotoTagException(String message){
        super(message);
    }

    public CouldNotDeletePhotoTagException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotDeletePhotoTagException(Throwable cause) {
        super(cause);
    }

    public CouldNotDeletePhotoTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
