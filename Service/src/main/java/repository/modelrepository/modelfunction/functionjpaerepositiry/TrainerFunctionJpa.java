package repository.modelrepository.modelfunction.functionjpaerepositiry;

import helperutils.closebaseconnection.JpaUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.threadmodelrep.ThreadRepositoryImplPostgres;
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

@Slf4j
public class TrainerFunctionJpa {
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();

    public static HashMap<Integer, UserImpl> getallTrainer() {
        HashMap <Integer, UserImpl> result = new HashMap<>();
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            TypedQuery<Trainer> alltrainer = em.createQuery("select t from Trainer t where t.role = :role", Trainer.class);
            alltrainer.setParameter("role", Role.TRAINER);
            alltrainer.getResultList().forEach(trainer -> result.put(trainer.getId(), trainer));
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return result;
    }

    public static HashMap<Integer, UserImpl> freeTrainer() {
        if (ThreadRepositoryImplPostgres.getInstance().allGroup().isEmpty())
            return getallTrainer();
        else {
                List<UserImpl> busyTrainer = new ArrayList<>();
                GroupFunctionJpa.getAllGroup().values()
                        .forEach(group -> busyTrainer.add(group.getTrainer()));
                return freeTrainerexecute((ArrayList<UserImpl>) busyTrainer);
        }
    }

    private static HashMap<Integer, UserImpl> freeTrainerexecute(ArrayList<UserImpl> busyTrainer) {
        HashMap<Integer, UserImpl> result = new HashMap<>(getallTrainer());
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
            TypedQuery <Trainer> query = em.createNamedQuery("trainerById", Trainer.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return null;
    }
}
