package repository.threadmodelrep.threadfunction.updategroupstratagy;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@Slf4j
public class UpdateGroupStratagyImplTheamAdd implements UpdateGroupStratagy {
    @Override
    public void updateGroup(int groupId, int[] entytiIdforact, Connection connection) throws SQLException {
        PreparedStatement ps = null;
        for (int item : entytiIdforact) {
            try {
                ps = connection.prepareStatement
                        ("insert into theam_group (theam_id, group_id) values (?, ?) ");
                ps.setInt(1, item);
                ps.setInt(2, groupId);
                ps.executeUpdate();
            }
            catch (MySqlException e) {
                log.info("UpdateGroup add theam exception = {}" , e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }
        }
    }
}
