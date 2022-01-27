package repository.modelrepository.modelservices.deleteentitystratege.postgresqlstrategy;

import java.sql.Connection;
import java.sql.SQLException;

public interface DeleteEntityPSQL {
    void removeEntity (int id, Connection connection) throws SQLException;
}
