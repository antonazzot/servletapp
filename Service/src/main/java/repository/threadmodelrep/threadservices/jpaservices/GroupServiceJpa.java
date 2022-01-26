package repository.threadmodelrep.threadservices.jpaservices;

import helperutils.myexceptionutils.MyJpaException;
import helperutils.closebaseconnection.JpaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import repository.threadmodelrep.threadservices.updategroupstratagy.*;
import repository.threadmodelrep.threadservices.updategroupstratagy.jpaupdatestratage.*;
import threadmodel.Group;
import threadmodel.Theams;
import users.Student;
import users.Trainer;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.*;
@Service
@Slf4j
@RequiredArgsConstructor
public class GroupServiceJpa {
    @Autowired
    private final SessionFactory sessionFactory;

    public  Map<Integer, Group> getAllGroup () {
        Map<Integer, Group> result = new HashMap<>();
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            TypedQuery <Group> groupTypedQuery = em.createQuery("from Group", Group.class);
            groupTypedQuery.getResultList().forEach(group -> result.put(group.getId(), group));
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return result;
    }

    public  Map<Integer, Student> getstudentsFromGroup(int groupId) {
        return getGroupById(groupId).getStudentMap();
    }

    public  void doaddGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {
        Set <Theams> theams = new HashSet<>();
        theamsIdList.forEach(id -> theams.add(ThreadRepositoryFactory.getRepository().theamById(id)));
        Map <Integer, Student> studentHashMap = new HashMap<>();
        studentList.stream().map(user -> (Student)user)
                .forEach(student -> studentHashMap.put(student.getId(), student));
        Trainer trainer = RepositoryFactory.getRepository().getTrainerById(trainerId);
        Group group = new Group()
                .withTrainer(trainer)
                .withName("Groups_" +trainerId)
                .withTheam(theams)
                .withStudents(studentHashMap);
        log.info("Group add = {}", "Group " + group.getId() + "Theam: " + theams.toString() + "Student: " + studentHashMap.values().toString() + "trainer:" + trainer );
        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(group);
            transaction.commit();
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }

      public  void doupdateGroup (int groupId, String act, int[] entytiIdforact) {
        log.info("In repository updateGroup = {}", groupId + " " + "  " + act + " " + Arrays.toString(entytiIdforact));
        EntityManager em = null;
        Group group = getGroupById(groupId);
          try  {
              em = sessionFactory.createEntityManager();
              UpdateStratageJpa updateGroupStratagyJpa = updateStratagyInject(act);
              updateGroupStratagyJpa.updateGroup(group, entytiIdforact, em);
          }
          catch (MyJpaException e) {
              log.info("doupdateGroup  exception = {}", e.getMessage());
          }
    }

    private  UpdateStratageJpa updateStratagyInject(String act) throws MyJpaException {
        Map <String, UpdateStratageJpa> stratagyMap = Map.of(
                "studentdelete", new UpdateGroupStratagyJpaStudentDelete(),
                "studentadd", new UpdateGroupStratagyJpaStudentAdd(),
                "theamdelete", new UpdateGroupStratagyImplJpaTheamDelete(),
                "theamadd",new UpdateGroupStratagyImplJpaTheamAdd(),
                "trainer", new UpdateGroupStratagyImplJpaTrainerChange());
        return stratagyMap.get(act);
    }

    public  Group getGroupById (int id) {
        EntityManager em = null;
        Group group = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            group = em.find(Group.class, id);
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return group;
    }


}
