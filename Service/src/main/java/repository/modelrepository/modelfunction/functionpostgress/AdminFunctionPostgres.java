package repository.modelrepository.modelfunction.functionpostgress;

import helperutils.closebaseconnection.PostgresSQLUtils;
import helperutils.myexceptionutils.MySqlException;
import lombok.extern.slf4j.Slf4j;
import repository.RepositoryDatasourse;
import repository.modelrepository.modelfunction.RoleIDParametrCheker;
import users.Administrator;
import users.Role;
import users.UserImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AdminFunctionPostgres {
    public static RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();

    public static Map<Integer, UserImpl> allAdmin() {
        Map<Integer, UserImpl> administrators = new HashMap<>();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("select * from users where role_id=" + RoleIDParametrCheker.userGetRoleForDB(Role.ADMINISTRATOR));
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (!rs.wasNull() || !rs.getString("login").equals("")) {
                        Administrator administrator = new Administrator();
                        Integer adminId = rs.getInt("id");
                        if (!rs.rowDeleted())
                            administrators.put(adminId,
                                    administrator
                                            .withId(adminId)
                                            .withLogin(rs.getString("login"))
                                            .withPassword(rs.getString("password"))
                                            .withName(rs.getString("name"))
                                            .withAge(rs.getInt("age"))
                                            .withRole(RoleIDParametrCheker.checkRole(rs.getInt("role_id")))

                            );
                    }
                }
            } catch (MySqlException e) {
                log.info("AllAdmin exception = {}", e.getMessage());
                e.printStackTrace();
            } finally {
                PostgresSQLUtils.closeQuietly(ps);
                PostgresSQLUtils.closeQuietly(rs);
            }
        } catch (SQLException e) {
            log.info("AllAdmin connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
        return administrators;
    }

    public static Administrator getAdminById(Integer id) {
        Administrator administrator = new Administrator();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("SELECT * FROM users where id= ? and role_id = ?");
                ps.setInt(1, id);
                ps.setInt(2, RoleIDParametrCheker.userGetRoleForDB(Role.ADMINISTRATOR));
                rs = ps.executeQuery();
                while (rs.next()) {
                    administrator = (Administrator) administrator
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
        return administrator;
    }

}
