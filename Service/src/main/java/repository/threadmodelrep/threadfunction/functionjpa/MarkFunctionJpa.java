package repository.threadmodelrep.threadfunction.functionjpa;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.RepositoryDatasourse;
import repository.RepositoryFactory;
import repository.modelrepository.UserRepositoryImplJpa;
import threadmodel.Mark;
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
public class MarkFunctionJpa {
    private static final RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();

    public static HashMap<UserImpl, HashMap<Theams, List<Mark>>> getstudentTheamMark(int studentId) {
        HashMap<UserImpl, HashMap<Theams, List<Mark>>> studentTheamMarkMap = new HashMap<>();
        HashMap <Theams, List <Mark>> theamsListHashMap = new HashMap<>();

        Student userById = UserRepositoryImplJpa.getInstance().getStudentById(studentId);
        Set <Theams> theams = new HashSet<>();
        for (Mark mark : userById.getMarkMap().values()) {
            theams.add(mark.getTheams());
        };
        for (Theams theam : theams) {
            theamsListHashMap.put(theam, dogetMarkListbyTheam(theam, studentId));
        }
        studentTheamMarkMap.put(userById, theamsListHashMap);
        return  studentTheamMarkMap;
    }
    public static List<Mark> dogetMarkListbyTheam(Theams theam, int studentId) {
        List <Mark> marks = new ArrayList<>();
        log.info("In getMarkListMethd getTheam method = {}",theam.getTheamName()+studentId);
        Student userById = UserRepositoryImplJpa.getInstance().getStudentById(studentId);
        for (Mark mark : userById.getMarkMap().values()) {
            if (mark.getTheams().equals(theam)) {
                marks.add(mark);
            }
        }

        return marks;
    }

    public static HashMap<Integer, Mark> dogetMarkIDListbyTheam(Theams theam, int studentId) {
        HashMap<Integer, Mark> marks = new HashMap<>();
        log.info("In getMarkListMethd getTheam method = {}",theam.getTheamName()+studentId);
        dogetMarkListbyTheam(theam, studentId).forEach(mark -> marks.put(mark.getId(), mark));
        return marks;
    }
    public static void doaddMarkToStudent(int studentId, int theamID, int markValue) {
       Student student = UserRepositoryImplJpa.getInstance().getStudentById(studentId);
       Mark mark = new Mark()
               .withValue(markValue)
               .withStudent(student)
               .withTheam(TheamFunctionJpa.gettheamById(theamID));
        EntityManager em = sessionFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(mark);
        transaction.commit();
        em.close();
    }

    private static void insertMark(int studentId, int theamID, int markValue)  {
        log.info("in insert section = {} ", studentId+ " "+theamID+" " + markValue);
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(
                        "insert into mark (mark_value, student_id, theam_id) values (?, ?, ?)");
                ps.setInt(1, markValue);
                ps.setInt(2, studentId);
                ps.setInt(3, theamID);
                ps.executeUpdate();
            }
            catch (MySqlException e) {
                log.info("insertMark exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("error = {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private static void updateMark(int studentId, int theamID, int markValue) {
        log.info("in update section = {}", studentId+ " "+theamID+" " + markValue);
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(
                        "update mark set mark_value = ? where student_id = ? and theam_id = ?");

                ps.setInt(1, markValue);
                ps.setInt(2, studentId);
                ps.setInt(3, theamID);
                ps.executeUpdate();
            }
            catch (MySqlException e) {
                log.info("updateMark exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }

        } catch (SQLException e) {
            log.info("error ={}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void dodeleteMarksById(int[] tempMarksId, int theamId, int studentid) {
        for (int i : tempMarksId) {
            EntityManager em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(getMarkById(tempMarksId[i]));
            transaction.commit();
            em.close();
        }
    }

    public static void dochangeMark(HashMap<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
        for (Map.Entry <Integer, Integer> entry: markIdMarkValue.entrySet()) {
            int tempId = entry.getKey();
            int tempMarkValue = entry.getValue();
           Mark mark = getMarkById(tempId);
           mark.withValue(tempMarkValue);
            EntityManager em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(mark);
            transaction.commit();
            em.close();
        }
    }

    private static Mark getMarkById (int id) {
        EntityManager em = sessionFactory.createEntityManager();
        TypedQuery <Mark> query = em.createNamedQuery("getMarkById", Mark.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

}
