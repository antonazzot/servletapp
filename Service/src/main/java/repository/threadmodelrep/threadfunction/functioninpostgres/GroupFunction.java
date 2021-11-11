package repository.threadmodelrep.threadfunction.functioninpostgres;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;
import repository.RepositoryDatasourse;
import repository.RepositoryFactory;
import repository.threadmodelrep.threadfunction.updategroupstratagy.*;
import threadmodel.Group;
import users.Student;
import users.UserImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class GroupFunction {

  private static final RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();

    public static HashMap<Integer, Group> getAllGroup () {
        HashMap<Integer, Group> result = new HashMap<>();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("select * from \"gr_oup\"");
                rs = ps.executeQuery();
                while (rs.next()) {
                    int groupId = rs.getInt("id");
                    UserImpl user =   RepositoryFactory.getRepository()
                            .getUserById(rs.getInt("trainer_id"));
                    result.put(groupId,
                            new Group()
                                    .withId(groupId)
                                    .withName(rs.getString("name"))
                                    .withTrainer(user)
                                    .withStudents(
                                            getstudentsFromGroup(groupId)
                                    )
                                    .withTheam(TheamFunction.gettheamFromGroup(groupId))
                    );
                }
            }
             catch (MySqlException e ) {
               log.info("Get All group exception = {}", e.getMessage());
             }
            finally {
                PostgresSQLUtils.closeQuietly(rs);
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("Get All group connection SQL exception ={}", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static Map<Integer, Student> getstudentsFromGroup(int groupId) {
        HashMap<Integer, Student> result = new HashMap<>();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
               ps = connection.prepareStatement("select * from student_group where group_id = " + groupId );
               rs = ps.executeQuery();
                while (rs.next()) {
                    UserImpl student = RepositoryFactory.getRepository()
                            .getUserById(rs.getInt("student_id"));
                    result.put(student.getId(), (Student) student);
                }
            }
            catch (MySqlException e) {
               log.info("GetstudentFromGroup exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(rs);
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("GetstudentFromGroup connection SQL exception ={}", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static void doaddGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(
                        "INSERT INTO \"gr_oup\" (name, trainer_id)" +
                                "Values (?,?)"
                );
                ps.setString(1, trainerId.toString()+"'s_Group");
                ps.setInt(2, trainerId);
                ps.executeUpdate();
                int groupId = getGroupId(trainerId);
                insertIntoStudentGroup(groupId, studentList);
                insertIntoTheamGroup(groupId, theamsIdList);
                insertIntoMarkTable(studentList,theamsIdList);
            }
            catch (MySqlException e) {
                log.info("DoaddGroup exception = {}", e.getMessage());
                e.printStackTrace();
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("DoaddGroup connection SQL exception ={}", e.getMessage());
            e.printStackTrace();
        }

    }

    private static void insertIntoTheamGroup(int groupId, List<Integer> theamsIdList) {
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            try {
                for (Integer i:
                        theamsIdList) {
                     ps = connection.prepareStatement(
                            "INSERT INTO theam_group (group_id, theam_id) " +
                                    "Values (?,?)"
                    );
                    ps.setInt(1, groupId);
                    ps.setInt(2, i);
                    ps.executeUpdate();
                    ps.close();
                }
            }
            catch (MySqlException e) {
                log.info("DoaddGroup _ InsertintoTheamGroup exception = {}", e.getMessage());
                e.printStackTrace();
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("insertIntoTheamGroup connection SQL exception ={}", e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertIntoStudentGroup(int groupId, List<UserImpl> studentList) {
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            try {
                for (UserImpl student:
                        studentList) {
                     ps = connection.prepareStatement(
                            "INSERT INTO student_group (group_id, student_id) " +
                                    "Values (?,?)"
                    );
                    ps.setInt(1, groupId);
                    ps.setInt(2, student.getId());
                    ps.executeUpdate();
                }
            }
            catch (MySqlException e) {
                log.info("DoaddGroup _ InsertintoSStudentGroup exception = {}", e.getMessage());
                e.printStackTrace();
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("DoaddGroup _ InsertintoSStudentGroup connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertIntoMarkTable(List<UserImpl> studentList, List<Integer> theamsIdList) {
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            try {
                for (Integer i:
                        theamsIdList) {
                    for (UserImpl student :
                            studentList) {
                        ps = connection.prepareStatement(
                                "INSERT INTO mark (student_id, theam_id) " +
                                        "Values (?,?)"
                        );
                        ps.setInt(1, student.getId());
                        ps.setInt(2, i);
                        ps.executeUpdate();
                        ps.close();
                    }
                }
            }
            catch (MySqlException e) {
                log.info("DoaddGroup _ InsertintoMarktable exception = {}", e.getMessage());
                e.printStackTrace();
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("DoaddGroup _ InsertintoMarktable connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private static int getGroupId(int trainerId) {
        int  groupId = 0;
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                 ps = connection.prepareStatement(
                        "select id from \"gr_oup\" where trainer_id = "+trainerId
                );
                 rs = ps.executeQuery();
                while (rs.next()) {
                    groupId = rs.getInt("id");
                }
                return groupId;
            }
            catch (MySqlException e) {
                log.info("GetGroupID exception = {}", e.getMessage());
                e.printStackTrace();
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
                PostgresSQLUtils.closeQuietly(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  groupId;
    }

      public static void doupdateGroup (int groupId, String act, int[] entytiIdforact) {
        log.info("In repository updateGroup = {}", groupId + " " + "  " + act + " " + Arrays.toString(entytiIdforact));
        UpdateGroupStratagy updateGroupStratagy;
        try (Connection connection = datasourse.getConnection()) {
          updateGroupStratagy = updateStratagyInject(act);
            assert updateGroupStratagy != null;
            updateGroupStratagy.updateGroup(groupId, entytiIdforact, connection);
        }
        catch (SQLException e) {
            log.info("doupdateGroup connection exception = {}", e.getMessage());
        }
    }

    private static UpdateGroupStratagy updateStratagyInject(String act) throws SQLException {
        switch (act) {
            case "studentdelete":
               return new UpdateGroupStratagyStudentDelete();
            case "studentadd":
                return new UpdateGroupStratagyStudentAdd();
            case "theamdelete":
                return new UpdateGroupStratagyImplTheamDelete();
            case "theamadd":
                return new UpdateGroupStratagyImplTheamAdd();
            case "trainer":
                return new  UpdateGroupStratagyImplTrainerChange();
        }
        return null;
    }


}
