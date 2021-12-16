package repository.threadmodelrep.threadfunction.updategroupstratagy.postgressupdatestratage;

import helperutils.myexceptionutils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;
import repository.threadmodelrep.threadfunction.updategroupstratagy.UpdateGroupStratagyPostgress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@Slf4j
public class UpdateGroupStratagyStudentDelete implements UpdateGroupStratagyPostgress {
    @Override
    public void updateGroup(int groupId, int[] entytiIdforact, Connection connection) throws SQLException {
        PreparedStatement ps = null;
        for (int item : entytiIdforact) {
            try {
                ps = connection.prepareStatement
                        ("delete from student_group where student_id = ? and group_id =?");
                ps.setInt(1, item);
                ps.setInt(2, groupId);
                ps.executeUpdate();
            }
           catch (MySqlException e) {
             log.info("UpdateGroup delete student exception = {}" , e.getMessage());
           }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }
        }
    }
}
