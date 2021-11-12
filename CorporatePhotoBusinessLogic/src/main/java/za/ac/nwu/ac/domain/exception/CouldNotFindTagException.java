package za.ac.nwu.ac.domain.exception;

public class CouldNotFindTagException extends RuntimeException {

    public CouldNotFindTagException(){
        super("Could not find photo tag");
    }

    public CouldNotFindTagException(String message){
        super(message);
    }

    public CouldNotFindTagException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotFindTagException(Throwable cause) {
        super(cause);
    }

    public CouldNotFindTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
