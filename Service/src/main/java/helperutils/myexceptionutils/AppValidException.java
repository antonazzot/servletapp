package helperutils.myexceptionutils;

public class AppValidException extends RuntimeException{
    public AppValidException() {
        super();
    }

    public AppValidException(String message) {
        super(message);
    }

    public AppValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppValidException(Throwable cause) {
        super(cause);
    }
}
