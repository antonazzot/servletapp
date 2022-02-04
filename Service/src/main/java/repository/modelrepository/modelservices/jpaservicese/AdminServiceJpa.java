package repository.modelrepository.modelservices.jpaservicese;

import helperutils.closebaseconnection.JpaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import users.Administrator;
import users.Role;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceJpa {

    @Autowired
    private final SessionFactory sessionFactory;

    public Map<Integer, UserImpl> getAllAdmin() {
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

    public Administrator doGetAdministratorById(int id) {
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
