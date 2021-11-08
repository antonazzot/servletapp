package helperutils.closebaseconnection;

import helperutils.MyExceptionUtils.MySqlException;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class PostgresSQLUtils {

    private PostgresSQLUtils() {
    }

    public static void closeQuietly (Statement statement) {
        try {
            if (statement != null)
            statement.close();
        }
        catch (SQLException e) {
            log.info("Statement close exception= {}", e.getMessage() );
            e.printStackTrace();
        }
    }

    public static void closeQuietly (ResultSet resultSet) {
        try {
            if (resultSet != null)
            resultSet.close();
        }
        catch (SQLException e) {
            log.info("ResultSet close exception= {}", e.getMessage() );
            e.printStackTrace();
        }
    }

}
