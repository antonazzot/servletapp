package repository.modelrepository.modelfunction.functionpostgress;

import helperutils.closebaseconnection.PostgresSQLUtils;
import helperutils.myexceptionutils.MySqlException;
import lombok.extern.slf4j.Slf4j;
import repository.RepositoryDatasourse;
import repository.modelrepository.modelfunction.RoleIDParametrCheker;
import repository.threadmodelrep.ThreadRepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryImplPostgres;
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
import java.util.Map;

@Slf4j
public class TrainerFunctionPostgres {

    public static RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();

    public static Map<Integer, UserImpl> getallTrainer() {
        Map<Integer, UserImpl> trainers = new HashMap<>();
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

    public static Map<Integer, UserImpl> freeTrainer() {
        if (ThreadRepositoryFactory.getRepository().allGroup().isEmpty())
            return getallTrainer();
        else {
            try (Connection connection = datasourse.getConnection()) {
                PreparedStatement ps = null;
                List<UserImpl> busyTrainer = new ArrayList<>();
                try {
                    ps = connection.prepareStatement("select *" +
                            "from \"gro_up\"");
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
                return freeTrainerexecute(busyTrainer);

            } catch (SQLException e) {
                log.info("FreeTrainer connection exception = {}", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }

    private static Map<Integer, UserImpl> freeTrainerexecute(List<UserImpl> busyTrainer) {
        Map<Integer, UserImpl> result = new HashMap<>(getallTrainer());
        for (UserImpl alltrainer :
                getallTrainer().values()) {
            for (UserImpl busyTr : busyTrainer) {
                if (alltrainer.getId() == busyTr.getId())
                    result.remove(busyTr.getId());
            }
        }
        return result;
    }

    public static Trainer getTrainerById(Integer id) {
        Trainer trainer = new Trainer();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("SELECT * FROM users where id= ? and role_id = ?");
                ps.setInt(1, id);
                ps.setInt(2, RoleIDParametrCheker.userGetRoleForDB(Role.TRAINER));
                rs = ps.executeQuery();
                while (rs.next()) {
                    trainer = (Trainer) trainer
                            .withId(rs.getInt("id"))
                            .withLogin(rs.getString("login"))
                            .withPassword(rs.getString("password"))
                            .withName(rs.getString("name"))
                            .withAge(rs.getInt("age"))
                            .withRole(RoleIDParametrCheker.checkRole(rs.getInt("role_id")));
                }
            } catch (MySqlException e) {
                log.info("GetUserByID exception = {}", e.getMessage());
                e.printStackTrace();
            } finally {
                PostgresSQLUtils.closeQuietly(ps);
                PostgresSQLUtils.closeQuietly(rs);
            }
        } catch (SQLException e) {
            log.info("GetUserByID connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
        return trainer;
    }
}
