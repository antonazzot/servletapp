package repository.threadmodelrep.threadfunction.functionjpa;

import helperutils.closebaseconnection.JpaUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import threadmodel.Theams;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class TheamFunctionJpa {
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();

    public static HashMap<Integer, Theams> getallTheams() {
        HashMap<Integer, Theams> result = new HashMap<>();
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            TypedQuery <Theams> query = em.createQuery("from Theams", Theams.class);
            query.getResultList().forEach(theams -> result.put(theams.getId(), theams));
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return result;
    }

    public static Theams gettheamById(Integer id) {
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            return em.find(Theams.class, id);
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return null;

    }
    public static Set<Theams> gettheamFromGroup(Integer groupId)  {
       return GroupFunctionJpa.getGroupById(groupId).getTheamsSet();
    }

    public static void doaddTheam(String theam) {
        if (!getallTheams().values().stream().map(Theams::getTheamName)
                .collect(Collectors.toList()).contains(theam) &&
                getallTheams().values().stream().map(Theams::getTheamName)
                        .noneMatch(theamName -> theamName.equals(theam)))
        {
            EntityManager em =  null;
            try {
                em = sessionFactory.createEntityManager();
                EntityTransaction transaction = em.getTransaction();
                transaction.begin();
                Theams theams = new Theams().withValue(theam);
                em.persist(theams);
                transaction.commit();
            }
            catch (Exception e) {
                JpaUtils.rollBackQuietly(em, e);
            } finally {
                JpaUtils.closeQuietly(em);
            }
        }
    }

    public static HashMap<Integer, Theams> getfreeTheams() {
        HashMap <Integer,Theams> busyTheam = getBuzyTeam();
        HashMap <Integer,Theams> freeTh = new HashMap<>(getallTheams());
        log.info("free Start {}" , busyTheam.values());
        if (GroupFunctionJpa.getAllGroup().isEmpty())
        {
            return getallTheams();}
        else {
            log.info("in for {}" , busyTheam.values());
            for (Theams allTh:
                    getallTheams().values()) {
                for (Theams bTh:
                        busyTheam.values()) {
                    if (allTh.getId() == bTh.getId() ) {
                        freeTh.remove(allTh.getId());
                    }
                }
            }
            return freeTh;
        }
    }
    private static HashMap<Integer, Theams> getBuzyTeam() {
        HashMap<Integer, Theams>  busyTheam = new HashMap<>();
        Set <Theams> theamsSet = new HashSet<>();
        GroupFunctionJpa.getAllGroup().values()
                .forEach(group -> theamsSet.addAll(group.getTheamsSet()));
        for (Theams theams : theamsSet) {
            busyTheam.put(theams.getId(), theams);
        }
        return busyTheam;
    }

    public static void doupdateTheam(int theamId, String theamName) {
        Theams theams = getTheamById(theamId);
        theams.withValue(theamName);
        EntityManager em = null;
        try {
            em  = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(theams);
            transaction.commit();
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }

    private static Theams getTheamById (int id) {
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            return em.find(Theams.class, id);
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return null;
//        TypedQuery <Theams> query = em.createNamedQuery("getTheamById", Theams.class);
//        query.setParameter("id", id);
//        return query.getSingleResult();
    }
}
