package repository.modelrepository.modelservices.jpaservicese;

import helperutils.closebaseconnection.JpaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.modelrepository.modelservices.deleteentitystratege.jpastrategy.*;
import users.Student;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceJpa {

    @Autowired
    private final SessionFactory sessionFactory;

    public UserImpl getUserById(Integer id) {
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            return em.find(Student.class, id);
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return null;
    }

    public Map<Integer, UserImpl> getAllUser() {
        Map<Integer, UserImpl> result = new HashMap<>();
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            List<UserImpl> resultList = em.createQuery("select s from Student s").getResultList();
            resultList.forEach(user -> result.put(user.getId(), user));
            return result;
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return result;
    }

    //    @JpaTransaction
    public int doSaveUser(UserImpl user) {
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(user);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return user.getId();
    }

    public Optional<UserImpl> doRemoveUser(Integer id, String entity) {
        EntityManager em = sessionFactory.createEntityManager();
        DeleteStratageJPA deleteStratageJPA = changeStratageForDelete(entity);
        deleteStratageJPA.doDeleteEntity(id, em);
        return Optional.empty();
    }

    private static DeleteStratageJPA changeStratageForDelete(String entity) {
        Map<String, DeleteStratageJPA> stratageMap = Map.of(
                "student", new DeleteStudentImpJPA(),
                "trainer", new DeleteTrainerImpJPA(),
                "administrator", new DeleteAdminImpJPA(),
                "group", new DeleteGroupImpJPA(),
                "theam", new DeleteTheamImpJPA()
        );
        return stratageMap.get(entity);
    }

    public UserImpl doUpdateUser(UserImpl user) {
        UserImpl userForChange = getUserById(user.getId());
        userForChange
                .withLogin(user.getLogin())
                .withPassword(user.getPassword())
                .withName(user.getName())
                .withAge(user.getAge())
                .withRole(user.getRole());
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(userForChange);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return userForChange;
    }

    public UserImpl getUserByLogin(String login) {
        return getAllUser().values().stream().filter(user -> user.getLogin().equalsIgnoreCase(login)).findFirst().orElse(null);
    }
}
