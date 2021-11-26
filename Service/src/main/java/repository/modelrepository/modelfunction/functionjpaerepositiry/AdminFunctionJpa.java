package repository.modelrepository.modelfunction.functionjpaerepositiry;

import helperutils.closebaseconnection.JpaUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import users.Administrator;
import users.Role;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AdminFunctionJpa {
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();

    public static Map<Integer, UserImpl> getAllAdmin() {
        Map<Integer, UserImpl> result = new HashMap<>();
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            TypedQuery<Administrator> alladmin = em.createQuery("select a from Administrator a where a.role = :role", Administrator.class);
            alladmin.setParameter("role", Role.ADMINISTRATOR);
            alladmin.getResultList().forEach(administrator -> result.put(administrator.getId(), administrator));
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return result;
    }

    public static Administrator doGetAdministratorById(int id) {
        EntityManager em = null;
        Administrator administrator = null;
        try {
            em = sessionFactory.createEntityManager();
            // I know, that I can do it by JPA find, but it was build this way for learning jpql too.
            TypedQuery<Administrator> query = em.createNamedQuery("adminById", Administrator.class);
            query.setParameter("id", id);
            administrator = query.getSingleResult();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return administrator;
    }
}
