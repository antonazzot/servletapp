package repository.threadmodelrep.threadfunction.functionjpa;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.JpaUtils;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.RepositoryDatasourse;
import repository.RepositoryFactory;
import repository.modelrepository.UserRepositoryImplJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.StudentFunctionJpa;
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
        Student student = StudentFunctionJpa.doGetStudentById(studentId);
        Set<Theams> theams = getTheamsSet(student);
        HashMap<Theams, List<Mark>> theamsListHashMap = getTheamsListHashMap(studentId, theams);
        studentTheamMarkMap.put(student, theamsListHashMap);
        return  studentTheamMarkMap;
    }

    private static HashMap<Theams, List<Mark>> getTheamsListHashMap(int studentId, Set<Theams> theams) {
        HashMap <Theams, List <Mark>> theamsListHashMap = new HashMap<>();
        for (Theams theam : theams) {
            theamsListHashMap.put(theam, dogetMarkListbyTheam(theam, studentId));
        }
        return theamsListHashMap;
    }

    private static Set<Theams> getTheamsSet(Student student) {
        Set <Theams> theams = new HashSet<>();
        for (Mark mark : student.getMarkMap().values()) {
            theams.add(mark.getTheams());
        }
        return theams;
    }

    public static List<Mark> dogetMarkListbyTheam(Theams theam, int studentId) {
        List <Mark> marks = new ArrayList<>();
        log.info("In getMarkListMethd getTheam method = {}",theam.getTheamName()+studentId);
        Student student = UserRepositoryImplJpa.getInstance().getStudentById(studentId);
        for (Mark mark : student.getMarkMap().values()) {
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
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(mark);
            transaction.commit();
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }

    public static void dodeleteMarksById(int[] tempMarksId, int theamId, int studentid) {
        EntityManager em = null;
        for (int i : tempMarksId) {
            try {
                em = sessionFactory.createEntityManager();
                EntityTransaction transaction = em.getTransaction();
                transaction.begin();
                em.remove(getMarkById(i));
                transaction.commit();
            }
            catch (Exception e) {
                JpaUtils.rollBackQuietly(em, e);
            } finally {
                JpaUtils.closeQuietly(em);
            }
        }
    }

    public static void dochangeMark(HashMap<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
        for (Map.Entry <Integer, Integer> entry: markIdMarkValue.entrySet()) {
            EntityManager em = null;
            int tempId = entry.getKey();
            int tempMarkValue = entry.getValue();
            Mark mark = getMarkById(tempId);
            mark.withValue(tempMarkValue);
            try {
                em = sessionFactory.createEntityManager();
                EntityTransaction transaction = em.getTransaction();
                transaction.begin();
                em.merge(mark);
                transaction.commit();
            }
            catch (Exception e) {
                JpaUtils.rollBackQuietly(em, e);
            } finally {
                JpaUtils.closeQuietly(em);
            }
        }
    }

    private static Mark getMarkById (int id) {
        EntityManager em = sessionFactory.createEntityManager();
        TypedQuery <Mark> query = em.createNamedQuery("getMarkById", Mark.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

}