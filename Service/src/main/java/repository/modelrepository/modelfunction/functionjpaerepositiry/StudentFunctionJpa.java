package repository.modelrepository.modelfunction.functionjpaerepositiry;

import helperutils.closebaseconnection.JpaUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import threadmodel.Group;
import users.Role;
import users.Student;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class StudentFunctionJpa {
    //    @Autowired
//    public static Configuration configuration;
//    @Autowired
//    public   SessionFactory sessionFactory;
    public static Configuration conf = new Configuration().configure();
    public static SessionFactory sessionFactory = conf.buildSessionFactory();

    public static Map<Integer, UserImpl> getAllStudent() {
        Map<Integer, UserImpl> result = new HashMap<>();
        EntityManager em = null;
        try {

            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            TypedQuery<Student> allst = em.createQuery("select s from Student s where s.role = :role", Student.class);
            allst.setParameter("role", Role.STUDENT);
            allst.getResultList().forEach(student -> result.put(student.getId(), student));
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return result;
    }

    public static List<Student> getStudentFromGroup(Integer groupId) {
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            Group group = em.find(Group.class, groupId);
            List<Student> students = new ArrayList<>();
            group.getStudentMap().values().forEach(student -> students.add(student));
            return students;
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return null;
    }

    public static Student doGetStudentById(int id) {
        EntityManager em = null;
        Student student = null;
        try {
            em = sessionFactory.createEntityManager();
//        TypedQuery <Student> query = em.createNamedQuery("studenById", Student.class);
//        query.setParameter("id", id);
//        return query.getSingleResult();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            student = em.find(Student.class, id);
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return student;
    }
}
