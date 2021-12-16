package repository.modelrepository.modelfunction.functionpostgress;

import helperutils.closebaseconnection.PostgresSQLUtils;
import helperutils.myexceptionutils.MySqlException;
import lombok.extern.slf4j.Slf4j;
import repository.RepositoryDatasourse;
import repository.modelrepository.modelfunction.RoleIDParametrCheker;
import repository.modelrepository.modelfunction.deleteentitystratage.postgresqlstratagy.DeleteEntityPSQL;
import repository.modelrepository.modelfunction.deleteentitystratage.postgresqlstratagy.DeleteGroupPSQLImpl;
import repository.modelrepository.modelfunction.deleteentitystratage.postgresqlstratagy.DeleteTheamPSQLImpl;
import repository.modelrepository.modelfunction.deleteentitystratage.postgresqlstratagy.DeleteUserPSQLImpl;
import users.Role;
import users.UserImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class UsersFunctionPostgres {
    public static RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();

    public static Map<Integer, UserImpl> allUser() {
        Map<Integer, UserImpl> users = new HashMap<>();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("select * from users;");
                rs = ps.executeQuery();
                while (rs.next()) {
                    Integer userId = rs.getInt("id");
                    if (!rs.rowDeleted())
                        users.put(userId,
                                new UserImpl()
                                        .withId(userId)
                                        .withRole(RoleIDParametrCheker.checkRole(rs.getInt("role_id")))
                                        .withLogin(rs.getString("login"))
                                        .withPassword(rs.getString("password"))
                                        .withName(rs.getString("name"))
                                        .withAge(rs.getInt("age"))
                        );
                }
            } catch (MySqlException e) {
                log.info("AllUserGet exception = {}", e.getMessage());
                e.printStackTrace();
            } finally {
                PostgresSQLUtils.closeQuietly(ps);
                PostgresSQLUtils.closeQuietly(rs);
            }
        } catch (SQLException e) {
            log.info("AllUserGet connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public static UserImpl getUserById(Integer id) {
        UserImpl user = new UserImpl();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("SELECT * FROM users where id= ?");
                ps.setInt(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    user = user
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
        return user;
    }

    public static int saveUser(UserImpl user) {
        int userId = 0;
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("INSERT INTO users (name, login, password, age, role_id) " +
                        "Values (?, ?, ?, ?, ?) returning id");
                ps.setString(1, user.getName());
                ps.setString(2, user.getLogin());
                ps.setString(3, user.getPassword());
                ps.setInt(4, user.getAge());
                ps.setInt(5, RoleIDParametrCheker.userGetRoleForDB(user.getRole()));
                rs = ps.executeQuery();
                while (rs.next()) {
                    userId = rs.getInt("id");
                }
            } catch (MySqlException e) {
                log.info("saveUser exception = {}", e.getMessage());
                e.printStackTrace();
            } finally {
                PostgresSQLUtils.closeQuietly(ps);
                PostgresSQLUtils.closeQuietly(rs);
            }
        } catch (SQLException e) {
            log.info("saveUser connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
        if (RoleIDParametrCheker.userGetRoleForDB(user.getRole()) == RoleIDParametrCheker.userGetRoleForDB(Role.TRAINER)) {
            addTrainerToSalaryTable(userId);
        }
        return userId;
    }

    public static UserImpl updateUser(UserImpl user) {
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement("update users set age = ?, " +
                        "login = ?, name= ?, password =?, role_id = ? " +
                        "where id = ? ");
                ps.setInt(1, user.getAge());
                ps.setString(2, user.getLogin());
                ps.setString(3, user.getName());
                ps.setString(4, user.getPassword());
                ps.setInt(5, RoleIDParametrCheker.userGetRoleForDB(user.getRole()));
                ps.setInt(6, user.getId());
                ps.executeUpdate();
            } catch (MySqlException e) {
                log.info("updateUser exception = {}", e.getMessage());
                e.printStackTrace();
            } finally {
                PostgresSQLUtils.closeQuietly(ps);
            }

        } catch (SQLException e) {
            log.info("User update fail = {}", e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    public static Optional<UserImpl> removeUser(Integer id, String entity) {
        // cascade delete for entity
        DeleteEntityPSQL deleteEntityPSQL = changeStratagyForDeleteEntity(entity);
        if (entity.equals("student") || entity.equals("trainer") || entity.equals("administrator")) {
            entity = "user";
        }
        log.info("In remove method ={}", "ID: " + id + " Entity: " + entity);
        try (Connection connection = datasourse.getConnection()) {
            try {
                deleteEntityPSQL.removeEntity(id, connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.info("removeUser exception = {}", e.getMessage());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    private static DeleteEntityPSQL changeStratagyForDeleteEntity(String entity) {
        if (entity.equals("student") || entity.equals("trainer") || entity.equals("administrator")) {
            entity = "user";
        }
        Map<String, DeleteEntityPSQL> entityPSQLMap = Map.of(
                "user", new DeleteUserPSQLImpl(),
                "group", new DeleteGroupPSQLImpl(),
                "theam", new DeleteTheamPSQLImpl()
        );
        return entityPSQLMap.get(entity);
    }

    private static void addTrainerToSalaryTable(int trainerId) {
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(
                        "insert into salary (trainer_id)" +
                                "values (?)");
                ps.setInt(1, trainerId);
                ps.executeUpdate();
                log.info("connection over");
            } catch (MySqlException e) {
                log.info("saveUser exception = {}", e.getMessage());
                e.printStackTrace();
            } finally {
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("error = {}", e.getMessage());
            e.printStackTrace();
        }

    }
}
