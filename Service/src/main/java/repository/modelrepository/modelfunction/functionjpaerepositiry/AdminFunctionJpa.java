package repository.modelrepository.modelfunction.functionjpaerepositiry;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import users.Administrator;
import users.Role;
import users.Trainer;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.HashMap;

public class AdminFunctionJpa {
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();
    public static EntityManager em = sessionFactory.createEntityManager();

    public static HashMap<Integer, UserImpl> getAllAdmin () {

        HashMap <Integer, UserImpl> result = new HashMap<>();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        TypedQuery<Administrator> alladmin = em.createQuery("select a from Administrator a where a.role = :role", Administrator.class);
        alladmin.setParameter("role", Role.ADMINISTRATOR);
        for (Administrator admin : alladmin.getResultList()) {
            result.put(admin.getId(), admin);
        }

        return result;
    }
}
