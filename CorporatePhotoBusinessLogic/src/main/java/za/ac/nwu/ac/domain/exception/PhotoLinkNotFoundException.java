package za.ac.nwu.ac.domain.exception;

public class PhotoLinkNotFoundException extends RuntimeException{
    public PhotoLinkNotFoundException() {
        super("Photo link could not be found");
    }

    public PhotoLinkNotFoundException(String message) {
        super(message);
    }
}
