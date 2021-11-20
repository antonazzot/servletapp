package repository.modelrepository.modelfunction.functionjpaerepositiry;

import helperutils.closebaseconnection.JpaUtils;
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

public class StudentFunctionJpa {
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();

    public static HashMap<Integer, UserImpl> getAllStudent() {
        HashMap <Integer, UserImpl> result = new HashMap<>();
        EntityManager em = null;
        try {
            em =sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            TypedQuery<Student> allst = em.createQuery("select s from Student s where s.role = :role", Student.class);
            allst.setParameter("role", Role.STUDENT);
            allst.getResultList().forEach(student -> result.put(student.getId(), student));
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return result;
    }

    public static ArrayList<Student> getStudentFromGroup(Integer groupId) {
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            Group group = em.find(Group.class, groupId);
            ArrayList <Student> students =new ArrayList<>();
            group.getStudentMap().values().forEach(student -> students.add(student));
            return students;
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return student;
    }
}