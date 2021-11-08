package helperutils.MyExceptionUtils;

import java.sql.SQLException;

public class MySqlException extends SQLException {
    public MySqlException() {
        super();
    }

    public MySqlException(String message) {
        super(message);
    }

    public MySqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public MySqlException(Throwable cause) {
        super(cause);
    }

}
