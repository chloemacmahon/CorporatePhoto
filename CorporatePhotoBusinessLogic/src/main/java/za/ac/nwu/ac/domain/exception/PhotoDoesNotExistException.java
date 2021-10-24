package za.ac.nwu.ac.domain.exception;

public class PhotoDoesNotExistException extends RuntimeException {
    public PhotoDoesNotExistException() {
        super("Photo does not exist");
    }

    public PhotoDoesNotExistException(String message) {
        super(message);
    }
}
