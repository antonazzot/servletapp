package repository.modelrepository.modelservices.deleteentitystratege.postgresqlstrategy;

import helperutils.closebaseconnection.PostgresSQLUtils;
import helperutils.myexceptionutils.MySqlException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class DeleteUserPSQLImpl implements DeleteEntityPSQL {
    @Override
    public void removeEntity(int id, Connection connection) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("DELETE FROM persons where id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (MySqlException e) {
            log.info("removeUser exception = {}", e.getMessage());
            e.printStackTrace();
        } finally {
            PostgresSQLUtils.closeQuietly(ps);
        }
    }
}
