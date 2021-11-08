package repository.threadmodelrep.threadfunction.updategroupstratagy;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@Slf4j
public class UpdateGroupStratagyImplTheamDelete implements UpdateGroupStratagy {
    @Override
    public void updateGroup(int groupId, int[] entytiIdforact, Connection connection) throws SQLException {
        PreparedStatement ps = null;
        for (int item : entytiIdforact) {
            try {
                ps = connection.prepareStatement
                        ("delete from theam_group where theam_id = ? and group_id =?");
                ps.setInt(1, item);
                ps.setInt(2, groupId);
                ps.executeUpdate();
            }
            catch (MySqlException e) {
                log.info("UpdateGroup delete theam exception = {}" , e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }
        }
    }
}
