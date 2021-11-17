package repository.threadmodelrep.threadfunction.functionjpa;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.RepositoryDatasourse;
import repository.RepositoryFactory;
import repository.modelrepository.modelfunction.functionjpaerepositiry.TrainerFunctionJpa;
import repository.threadmodelrep.threadfunction.updategroupstratagy.*;
import threadmodel.Group;
import threadmodel.Theams;
import users.Student;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class GroupFunctionJpa {
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();
    public static EntityManager em = sessionFactory.createEntityManager();

  private static final RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();

    public static HashMap<Integer, Group> getAllGroup () {
        HashMap<Integer, Group> result = new HashMap<>();
        TypedQuery <Group> groupTypedQuery = em.createQuery("from Group", Group.class);
        for (Group group : groupTypedQuery.getResultList()) {
            result.put(group.getId(), group);
        }
        return result;
    }

    public static Map<Integer, Student> getstudentsFromGroup(int groupId) {
        return getAllGroup().get(groupId).getStudentMap();
    }

    public static void doaddGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {
        Set <Theams> theams = null;
        theamsIdList.forEach(id -> theams.add(TheamFunctionJpa.gettheamById(id)));
        HashMap <Integer, Student> studentHashMap = null;
        studentList.stream().map(user -> (Student)user)
                .forEach(student -> studentHashMap.put(student.getId(), student));
        Group group = new Group()
                .withTrainer(TrainerFunctionJpa.getallTrainer().get(trainerId))
                .withName("Groups_" +trainerId)
                .withTheam(theams)
                .withStudents(studentHashMap);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(group);
        em.close();
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
