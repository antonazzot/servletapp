package repository.threadmodelrep.threadfunction.functioninpostgres;

import helperutils.closebaseconnection.PostgresSQLUtils;
import helperutils.myexceptionutils.MySqlException;
import lombok.extern.slf4j.Slf4j;
import repository.RepositoryDatasourse;
import repository.modelrepository.modelfunction.functionpostgress.StudentFunctionPostgres;
import repository.modelrepository.modelfunction.functionpostgress.TrainerFunctionPostgres;
import repository.threadmodelrep.threadfunction.updategroupstratagy.UpdateGroupStratagyPostgress;
import repository.threadmodelrep.threadfunction.updategroupstratagy.postgressupdatestratage.*;
import threadmodel.Group;
import users.Student;
import users.Trainer;
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

    public static Map<Integer, Group> getAllGroup() {
        Map<Integer, Group> result = new HashMap<>();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("select * from \"gr_oup\"");
                rs = ps.executeQuery();
                while (rs.next()) {
                    int groupId = rs.getInt("id");
                    Trainer trainer = TrainerFunctionPostgres
                            .getTrainerById(rs.getInt("trainer_id"));
                    result.put(groupId,
                            new Group()
                                    .withId(groupId)
                                    .withName(rs.getString("name"))
                                    .withTrainer(trainer)
                                    .withStudents(getstudentsFromGroup(groupId))
                                    .withTheam(TheamFunction.gettheamFromGroup(groupId)));
                }
            } catch (MySqlException e) {
                log.info("Get All group exception = {}", e.getMessage());
            } finally {
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
        Map<Integer, Student> result = new HashMap<>();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("select * from student_group where group_id = " + groupId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Student student = StudentFunctionPostgres
                            .getStudentById(rs.getInt("student_id"));
                    result.put(student.getId(), student);
                }
            } catch (MySqlException e) {
                log.info("GetstudentFromGroup exception = {}", e.getMessage());
            } finally {
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
            ResultSet rs = null;
            int groupId = 0;
            try {
                ps = connection.prepareStatement(
                        "INSERT INTO \"gr_oup\" (name, trainer_id)" +
                                "Values (?,?) returning id"
                );
                ps.setString(1, trainerId.toString() + "'s_Group");
                ps.setInt(2, trainerId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    groupId = rs.getInt("id");
                }
                insertIntoStudentGroup(groupId, studentList);
                insertIntoTheamGroup(groupId, theamsIdList);
//                insertIntoMarkTable(studentList,theamsIdList);
            } catch (MySqlException e) {
                log.info("DoaddGroup exception = {}", e.getMessage());
                e.printStackTrace();
            } finally {
                PostgresSQLUtils.closeQuietly(rs);
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
                for (Integer i :
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
            } catch (MySqlException e) {
                log.info("DoaddGroup _ InsertintoTheamGroup exception = {}", e.getMessage());
                e.printStackTrace();
            } finally {
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
                for (UserImpl student :
                        studentList) {
                    ps = connection.prepareStatement(
                            "INSERT INTO student_group (group_id, student_id) " +
                                    "Values (?,?)"
                    );
                    ps.setInt(1, groupId);
                    ps.setInt(2, student.getId());
                    ps.executeUpdate();
                }
            } catch (MySqlException e) {
                log.info("DoaddGroup _ InsertintoSStudentGroup exception = {}", e.getMessage());
                e.printStackTrace();
            } finally {
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
                for (Integer i :
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
                    }
                }
            } catch (MySqlException e) {
                log.info("DoaddGroup _ InsertintoMarktable exception = {}", e.getMessage());
                e.printStackTrace();
            } finally {
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("DoaddGroup _ InsertintoMarktable connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void doupdateGroup(int groupId, String act, int[] entytiIdforact) {
        log.info("In repository updateGroup = {}", groupId + " " + "  " + act + " " + Arrays.toString(entytiIdforact));
        UpdateGroupStratagyPostgress updateGroupStratagyPostgress;
        try (Connection connection = datasourse.getConnection()) {
            updateGroupStratagyPostgress = updateStratagyInject(act);
            assert updateGroupStratagyPostgress != null;
            updateGroupStratagyPostgress.updateGroup(groupId, entytiIdforact, connection);
        } catch (SQLException e) {
            log.info("doupdateGroup connection exception = {}", e.getMessage());
        }
    }

    private static UpdateGroupStratagyPostgress updateStratagyInject(String act) throws SQLException {
        Map<String, UpdateGroupStratagyPostgress> stratagyMap = Map.of(
                "studentdelete", new UpdateGroupStratagyStudentDelete(),
                "studentadd", new UpdateGroupStratagyPostgressStudentAdd(),
                "theamdelete", new UpdateGroupStratagyPostgressImplTheamDelete(),
                "theamadd", new UpdateGroupStratagyPostgressImplTheamAdd(),
                "trainer", new UpdateGroupStratagyPostgressImplTrainerChange());
        return stratagyMap.get(act);
    }


}
