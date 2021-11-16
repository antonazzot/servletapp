package repository.modelrepository.modelfunction.functionpostgress;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;
import repository.RepositoryDatasourse;
import repository.modelrepository.modelfunction.RoleIDParametrCheker;
import users.Role;
import users.Student;
import users.UserImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
public class StudentFunctionPostgres {
    public static RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();

    public static HashMap<Integer, UserImpl> allStudent() {
        HashMap<Integer, UserImpl> students = new HashMap<>();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("select * from users where role_id=" + RoleIDParametrCheker.userGetRoleForDB(Role.STUDENT));
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (!rs.wasNull() || !rs.getString("login").equals("")) {
                        Student student = new Student();
                        Integer studentId = rs.getInt("id");
                        if (!rs.rowDeleted())
                            students.put(studentId,
                                    student
                                            .withTheamMark(new HashMap<>())
                                            .withId(studentId)
                                            .withLogin(rs.getString("login"))
                                            .withPassword(rs.getString("password"))
                                            .withName(rs.getString("name"))
                                            .withAge(rs.getInt("age"))
                                            .withRole(RoleIDParametrCheker.checkRole(rs.getInt("role_id")))
                            );
                    }
                }
            } catch (MySqlException e) {
                log.info("AllStudent exception = {}", e.getMessage());
                e.printStackTrace();
            } finally {
                PostgresSQLUtils.closeQuietly(ps);
                PostgresSQLUtils.closeQuietly(rs);
            }
        } catch (SQLException e) {
            log.info("AllStudent connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
        return students;
    }

    public static ArrayList<Student> studentFromGroup(Integer groupId) {
        ArrayList<Student> result = new ArrayList<>();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("select *" +
                        "from student_group where group_id = ?");
                ps.setInt(1, groupId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Student user = (Student) UsersFunctionPostgres.getUserById(rs.getInt("student_id"));
                    result.add(user);
                }
            } catch (MySqlException e) {
                log.info("studentFromGroup exception = {}", e.getMessage());
                e.printStackTrace();
            } finally {
                PostgresSQLUtils.closeQuietly(ps);
                PostgresSQLUtils.closeQuietly(rs);
            }
        } catch (SQLException e) {
            log.info("studentFromGroup connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
