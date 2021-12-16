package repository.threadmodelrep.threadfunction.updategroupstratagy;

import java.sql.Connection;
import java.sql.SQLException;

public interface UpdateGroupStratagyPostgress {
    void updateGroup (int groupId, int[] entytiIdforact, Connection connection) throws SQLException;
}
