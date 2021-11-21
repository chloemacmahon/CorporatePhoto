package za.ac.nwu.ac.domain.exception;

public class PhotoMetaDataDoesNotExistException extends RuntimeException {

    public PhotoMetaDataDoesNotExistException(){
        super("Photo MetaData does not exists");
    }

    public PhotoMetaDataDoesNotExistException(String message){
        super(message);
    }
}
