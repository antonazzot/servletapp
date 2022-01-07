package repository.modelrepository.modelfunction.functionjpaerepositiry;

import aspect.JpaTransaction;
import helperutils.closebaseconnection.JpaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import repository.modelrepository.modelfunction.deleteentitystratage.jpastratagy.*;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserFunctionJpa {
//    @Autowired
//    public  static Configuration configuration;
//    @Autowired
//    public static SessionFactory sessionFactory;

    public static  Configuration conf = new Configuration().configure();
    public static SessionFactory sessionFactory = conf.buildSessionFactory();

    public static UserImpl getUserById(Integer id) {
        return getAllUser().get(id);
    }

    public static Map<Integer, UserImpl> getAllUser() {
        Map<Integer, UserImpl> result = new HashMap<>();
        result.putAll(TrainerFunctionJpa.getallTrainer());
        result.putAll(StudentFunctionJpa.getAllStudent());
        result.putAll(AdminFunctionJpa.getAllAdmin());
        return result;
    }
//    @JpaTransaction
    public static int doSaveUser(UserImpl user) {
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

    public static Optional<UserImpl> doRemoveUser(Integer id, String entity) {
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
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return userForChange;
    }
}
