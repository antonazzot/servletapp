package repository.modelrepository.modelfunction.functionjpaerepositiry;

import helperutils.closebaseconnection.JpaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.threadmodelrep.ThreadRepositoryFactory;
import repository.threadmodelrep.threadfunction.functionjpa.GroupFunctionJpa;
import users.Role;
import users.Trainer;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class TrainerFunctionJpa {
//    @Autowired
//    static Configuration configuration;
//    @Autowired
//    private static final SessionFactory sessionFactory = configuration.buildSessionFactory() ;

    public static Configuration conf = new Configuration().configure();
    public static SessionFactory sessionFactory = conf.buildSessionFactory();

    public static Map<Integer, UserImpl> getallTrainer() {
        Map<Integer, UserImpl> result = new HashMap<>();
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            TypedQuery<Trainer> alltrainer = em.createQuery("select t from Trainer t where t.role = :role", Trainer.class);
            alltrainer.setParameter("role", Role.TRAINER);
            alltrainer.getResultList().forEach(trainer -> result.put(trainer.getId(), trainer));
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return result;
    }

    public static Map<Integer, UserImpl> freeTrainer() {
        if (ThreadRepositoryFactory.getRepository().allGroup().isEmpty())
            return getallTrainer();
        else {
            List<UserImpl> busyTrainer = new ArrayList<>();
            GroupFunctionJpa.getAllGroup().values()
                    .forEach(group -> busyTrainer.add(group.getTrainer()));
            return freeTrainerexecute(busyTrainer);
        }
    }

    private static Map<Integer, UserImpl> freeTrainerexecute(List<UserImpl> busyTrainer) {
        Map<Integer, UserImpl> result = new HashMap<>(getallTrainer());
        for (UserImpl alltrainer :
                getallTrainer().values()) {
            for (UserImpl busyTr : busyTrainer) {
                if (alltrainer.getId() == busyTr.getId())
                    result.remove(busyTr.getId());
            }
        }
        return result;
    }

    public static Trainer doGetTrainerById(int id) {
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            // I know, that I can do it by JPA find, but it was build this way for learning jpql too.
            TypedQuery<Trainer> query = em.createNamedQuery("trainerById", Trainer.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return null;
    }
}
