package repository.threadmodelrep.threadservices.jpaservices;

import helperutils.closebaseconnection.JpaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Theams;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TheamServiceJpa {

    private final SessionFactory sessionFactory;

    public Map<Integer, Theams> getallTheams() {
        Map<Integer, Theams> result = new HashMap<>();
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            TypedQuery<Theams> query = em.createQuery("from Theams", Theams.class);
            query.getResultList().forEach(theams -> result.put(theams.getId(), theams));
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return result;
    }

    public Theams gettheamById(Integer id) {
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            return em.find(Theams.class, id);
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return null;

    }

    public Set<Theams> gettheamFromGroup(Integer groupId) {
        return ThreadRepositoryFactory.getRepository().allGroup().get(groupId).getTheamsSet();
    }

    public void doaddTheam(String theam) {
        if (!getallTheams().values().stream().map(Theams::getTheamName)
                .collect(Collectors.toList()).contains(theam) &&
                getallTheams().values().stream().map(Theams::getTheamName)
                        .noneMatch(theamName -> theamName.equals(theam))) {
            EntityManager em = null;
            try {
                em = sessionFactory.createEntityManager();
                EntityTransaction transaction = em.getTransaction();
                transaction.begin();
                Theams theams = new Theams().withValue(theam);
                em.persist(theams);
                transaction.commit();
            } catch (Exception e) {
                JpaUtils.rollBackQuietly(em, e);
            } finally {
                JpaUtils.closeQuietly(em);
            }
        }
    }

    public Map<Integer, Theams> getfreeTheams() {
        Map<Integer, Theams> busyTheam = getBuzyTeam();
        Map<Integer, Theams> freeTh = new HashMap<>(getallTheams());
        log.info("free Start {}", busyTheam.values());
        if (ThreadRepositoryFactory.getRepository().allGroup().isEmpty()) {
            return getallTheams();
        } else {
            log.info("in for {}", busyTheam.values());
            for (Theams allTh :
                    getallTheams().values()) {
                for (Theams bTh :
                        busyTheam.values()) {
                    if (allTh.getId() == bTh.getId()) {
                        freeTh.remove(allTh.getId());
                    }
                }
            }
            return freeTh;
        }
    }

    private Map<Integer, Theams> getBuzyTeam() {
        Map<Integer, Theams> busyTheam = new HashMap<>();
        Set<Theams> theamsSet = new HashSet<>();
        ThreadRepositoryFactory.getRepository().allGroup().values()
                .forEach(group -> theamsSet.addAll(group.getTheamsSet()));
        for (Theams theams : theamsSet) {
            busyTheam.put(theams.getId(), theams);
        }
        return busyTheam;
    }

    public void doupdateTheam(int theamId, String theamName) {
        Theams theams = getTheamById(theamId);
        theams.withValue(theamName);
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(theams);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }

    private Theams getTheamById(int id) {
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            return em.find(Theams.class, id);
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return null;
    }
}
