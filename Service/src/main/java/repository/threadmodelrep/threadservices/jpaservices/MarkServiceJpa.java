package repository.threadmodelrep.threadservices.jpaservices;

import helperutils.closebaseconnection.JpaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Mark;
import threadmodel.Theams;
import users.Student;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarkServiceJpa {
    @Autowired
    private final SessionFactory sessionFactory;

    public Map<UserImpl, Map<Theams, List<Mark>>> getstudentTheamMark(int studentId) {
        Map<UserImpl, Map<Theams, List<Mark>>> studentTheamMarkMap = new HashMap<>();
        Student student = RepositoryFactory.getRepository().getStudentById(studentId);
        Set<Theams> theams = getTheamsSet(student);
        Map<Theams, List<Mark>> theamsListHashMap = getTheamsListHashMap(studentId, theams);
        studentTheamMarkMap.put(student, theamsListHashMap);
        return studentTheamMarkMap;
    }

    private Map<Theams, List<Mark>> getTheamsListHashMap(int studentId, Set<Theams> theams) {
        Map<Theams, List<Mark>> theamsListHashMap = new HashMap<>();
        for (Theams theam : theams) {
            theamsListHashMap.put(theam, dogetMarkListbyTheam(theam, studentId));
        }
        return theamsListHashMap;
    }

    private Set<Theams> getTheamsSet(Student student) {
        Set<Theams> theams = new HashSet<>();
        for (Mark mark : student.getMarkMap().values()) {
            theams.add(mark.getTheams());
        }
        return theams;
    }

    public List<Mark> dogetMarkListbyTheam(Theams theam, int studentId) {
        List<Mark> marks = new ArrayList<>();
        log.info("In getMarkListMethd getTheam method = {}", theam.getTheamName() + studentId);
        Student student = RepositoryFactory.getRepository().getStudentById(studentId);
        for (Mark mark : student.getMarkMap().values()) {
            if (mark.getTheams().equals(theam)) {
                marks.add(mark);
            }
        }
        return marks;
    }

    public Map<Integer, Mark> dogetMarkIDListbyTheam(Theams theam, int studentId) {
        Map<Integer, Mark> marks = new HashMap<>();
        log.info("In getMarkListMethd getTheam method = {}", theam.getTheamName() + studentId);
        dogetMarkListbyTheam(theam, studentId).forEach(mark -> marks.put(mark.getId(), mark));
        return marks;
    }

    public void doaddMarkToStudent(int studentId, int theamID, int markValue) {
        Student student = RepositoryFactory.getRepository().getStudentById(studentId);
        Mark mark = new Mark()
                .withValue(markValue)
                .withStudent(student)
                .withTheam(ThreadRepositoryFactory.getRepository().theamById(theamID));
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(mark);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }

    public void dodeleteMarksById(int[] tempMarksId, int theamId, int studentid) {
        EntityManager em = null;
        for (int i : tempMarksId) {

            try {
                em = sessionFactory.createEntityManager();
                EntityTransaction transaction = em.getTransaction();
                transaction.begin();
//                log.info("MARK By ID ={}", "  " + markById.getId()+" " + markById.getValuesOfMark());
                em.remove(getMarkById(i));
                transaction.commit();
            } catch (Exception e) {
                JpaUtils.rollBackQuietly(em, e);
            } finally {
                JpaUtils.closeQuietly(em);
            }
        }
    }

    public void dochangeMark(Map<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
        for (Map.Entry<Integer, Integer> entry : markIdMarkValue.entrySet()) {
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
            } catch (Exception e) {
                JpaUtils.rollBackQuietly(em, e);
            } finally {
                JpaUtils.closeQuietly(em);
            }
        }
    }

    private Mark getMarkById(int id) {
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            TypedQuery<Mark> query = em.createNamedQuery("getMarkById", Mark.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return null;
    }

}
