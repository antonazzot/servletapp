package repository.modelrepository.modelfunction.functionjpaerepositiry;

import helperutils.closebaseconnection.JpaUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.modelrepository.modelfunction.deleteentitystratage.*;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Slf4j
public class UserFunctionJpa {
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();


    public static UserImpl getUserById (Integer id) {
       return getAllUser().get(id);
    }

    public static HashMap<Integer, UserImpl> getAllUser() {
            HashMap <Integer, UserImpl> result = new HashMap<>();
            result.putAll(TrainerFunctionJpa.getallTrainer());
            result.putAll(StudentFunctionJpa.getAllStudent());
            result.putAll(AdminFunctionJpa.getAllAdmin());
            return result;
    }

    public static int doSaveUser(UserImpl user) {
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(user);
            transaction.commit();
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return user.getId();
    }

    public static Optional<UserImpl> doRemoveUser(Integer id, String entity) {
            EntityManager em = sessionFactory.createEntityManager();
            DeleteStratage deleteStratage = changeStratageForDelete(entity);
            deleteStratage.doDeleteEntity(id, em);
            return Optional.empty();
    }

    private static DeleteStratage changeStratageForDelete(String entity) {
        Map<String, DeleteStratage> stratageMap = Collections.unmodifiableMap(Map.of(
                "student", new DeleteStudentImp(),
                "trainer", new DeleteTrainerImp(),
                "administrator", new DeleteAdminImp(),
                "group",new DeleteGroupImp(),
                "theam", new DeleteTheamImp()
        ));
        return stratageMap.get(entity);
    }

    public static UserImpl doUpdateUser(UserImpl user) {
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
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return userForChange;
    }
}
