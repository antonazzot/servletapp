package repository.modelrepository.modelservices.jpaservicese;

import helperutils.closebaseconnection.JpaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import users.TempStudent;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TempStudentService {
    private final SessionFactory sessionFactory;

    public List<TempStudent> getAllTempStudent() {
        List<TempStudent> result = new ArrayList<>();
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            TypedQuery<TempStudent> allst = em.createQuery("select t from TempStudent t", TempStudent.class);
            result.addAll(allst.getResultList());
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return result;
    }

    public TempStudent doGetTempStudentById(Integer id) {
        EntityManager em = null;
        TempStudent student = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            student = em.find(TempStudent.class, id);
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return student;
    }

    public void doRemoveTempstudent(Integer id) {
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            TempStudent tempStudent = em.find(TempStudent.class, id);
            em.remove(tempStudent);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
    public int doSaveTempstudent(TempStudent tempStudent) {
        EntityManager em = null;
        TempStudent student = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(tempStudent);
            transaction.commit();

        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return tempStudent.getId();
    }
}
