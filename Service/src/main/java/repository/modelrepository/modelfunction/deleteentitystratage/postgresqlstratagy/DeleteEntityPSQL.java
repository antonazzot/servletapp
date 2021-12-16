package repository.modelrepository.modelfunction.deleteentitystratage.postgresqlstratagy;

import java.sql.Connection;
import java.sql.SQLException;

public interface DeleteEntityPSQL {
    void removeEntity (int id, Connection connection) throws SQLException;
}
