package repository.modelrepository.modelfunction.functionpostgress;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;
import repository.RepositoryDatasourse;
import repository.modelrepository.modelfunction.RoleIDParametrCheker;
import repository.threadmodelrep.ThreadRepositoryImpl;
import users.Role;
import users.Trainer;
import users.UserImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class TrainerFunctionPostgres {

    public static RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();

    public static HashMap<Integer, UserImpl> getallTrainer() {
        HashMap<Integer, UserImpl> trainers = new HashMap<>();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("select * from users where role_id=" + RoleIDParametrCheker.userGetRoleForDB(Role.TRAINER));
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (!rs.wasNull() || !rs.getString("login").equals("")) {
                        Trainer trainer = new Trainer();
                        Integer trainerId = rs.getInt("id");
                        if (!rs.rowDeleted())
                            trainers.put(trainerId,
                                    trainer
                                            .withId(trainerId)
                                            .withRole(RoleIDParametrCheker.checkRole(rs.getInt("role_id")))
                                            .withLogin(rs.getString("login"))
                                            .withPassword(rs.getString("password"))
                                            .withName(rs.getString("name"))
                                            .withAge(rs.getInt("age")));
                    }
                }
            } catch (MySqlException e) {
                log.info("AllTrainer exception = {}", e.getMessage());
                e.printStackTrace();
            } finally {
                PostgresSQLUtils.closeQuietly(ps);
                PostgresSQLUtils.closeQuietly(rs);
            }
        } catch (SQLException e) {
            log.info("AllTrainer connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
        return trainers;
    }

    public static HashMap<Integer, UserImpl> freeTrainer() {
        if (ThreadRepositoryImpl.getInstance().allGroup().isEmpty())
            return getallTrainer();
        else {
            try (Connection connection = datasourse.getConnection()) {
                PreparedStatement ps = null;
                List<UserImpl> busyTrainer = new ArrayList<>();
                try {
                    ps = connection.prepareStatement("select *" +
                            "from \"group\"");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        busyTrainer.add(UsersFunctionPostgres.getUserById(rs.getInt("trainer_id")));
                    }
                } catch (MySqlException e) {
                    log.info("FreeTrainer exception = {}", e.getMessage());
                    e.printStackTrace();
                } finally {
                    PostgresSQLUtils.closeQuietly(ps);
                }
                return freeTrainerexecute((ArrayList<UserImpl>) busyTrainer);

            } catch (SQLException e) {
                log.info("FreeTrainer connection exception = {}", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }

    private static HashMap<Integer, UserImpl> freeTrainerexecute(ArrayList<UserImpl> busyTrainer) {
        HashMap<Integer, UserImpl> result = new HashMap<>(getallTrainer());
        for (UserImpl alltrainer :
                getallTrainer().values()) {
            for (UserImpl busyTr : busyTrainer) {
                if (alltrainer.getId() == busyTr.getId())
                    result.remove(busyTr.getId());
            }
        }
        return result;
    }
}
