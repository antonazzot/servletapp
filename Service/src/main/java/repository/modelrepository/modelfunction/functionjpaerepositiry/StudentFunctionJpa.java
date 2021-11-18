package repository.modelrepository.modelfunction.functionjpaerepositiry;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import threadmodel.Group;
import users.Administrator;
import users.Role;
import users.Student;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class StudentFunctionJpa {
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();


    public static HashMap<Integer, UserImpl> getAllStudent() {

        HashMap <Integer, UserImpl> result = new HashMap<>();
        EntityManager em = sessionFactory.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        TypedQuery<Student> allst = em.createQuery("select s from Student s where s.role = :role", Student.class);
        allst.setParameter("role", Role.STUDENT);
        allst.getResultList().forEach(student -> result.put(student.getId(), student));
        em.close();
        return result;
    }

    public static ArrayList<Student> getStudentFromGroup(Integer groupId) {
        EntityManager em = sessionFactory.createEntityManager();
        Group group = em.find(Group.class, groupId);
        em.close();
        ArrayList <Student> students =new ArrayList<>();
        group.getStudentMap().values().forEach(student -> students.add(student));
        return students;
    }

    public static Student doGetStudentById(int id) {
        EntityManager em = sessionFactory.createEntityManager();
//        TypedQuery <Student> query = em.createNamedQuery("studenById", Student.class);
//        query.setParameter("id", id);
//        return query.getSingleResult();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Student student = em.find(Student.class, id);
        em.close();
        return student;

    }
}
