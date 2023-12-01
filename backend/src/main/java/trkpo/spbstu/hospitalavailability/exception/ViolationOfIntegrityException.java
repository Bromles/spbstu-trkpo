package trkpo.spbstu.hospitalavailability.exception;

public class ViolationOfIntegrityException extends RuntimeException{

    public ViolationOfIntegrityException(String message) {
        super(message);
    }

    public ViolationOfIntegrityException(String message, Throwable e) {
        super(message, e);
    }
}
