package helperutils.myexceptionutils;

public class MyJpaException extends Exception {
    public MyJpaException() {
        super();
    }

    public MyJpaException(String message) {
        super(message);
    }

    public MyJpaException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyJpaException(Throwable cause) {
        super(cause);
    }

    protected MyJpaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
