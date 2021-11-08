package repository.threadmodelrep.threadfunction.updategroupstratagy;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@Slf4j
public class UpdateGroupStratagyImplTrainerChange implements UpdateGroupStratagy {
    @Override
    public void updateGroup(int groupId, int[] entytiIdforact, Connection connection) throws SQLException {
        PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement
                        ("update \"group\" set trainer_id = ? where id = ? ");
                ps.setInt(1, entytiIdforact[0]);
                ps.setInt(2, groupId);
                ps.executeUpdate();
            }
            catch (MySqlException e) {
                log.info("UpdateGroup trainer change exception = {}" , e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }

    }
}
