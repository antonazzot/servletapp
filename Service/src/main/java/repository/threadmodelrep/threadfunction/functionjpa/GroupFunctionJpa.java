package repository.threadmodelrep.threadfunction.functionjpa;

import helperutils.MyExceptionUtils.MyJpaException;
import helperutils.closebaseconnection.JpaUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.modelrepository.modelfunction.functionjpaerepositiry.TrainerFunctionJpa;
import repository.threadmodelrep.threadfunction.updategroupstratagy.*;
import repository.threadmodelrep.threadfunction.updategroupstratagy.jpaupdatestratage.*;
import repository.threadmodelrep.threadfunction.updategroupstratagy.postgressupdatestratage.*;
import threadmodel.Group;
import threadmodel.Theams;
import users.Student;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class GroupFunctionJpa {
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();


    public static HashMap<Integer, Group> getAllGroup () {
        HashMap<Integer, Group> result = new HashMap<>();
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

    public static Map<Integer, Student> getstudentsFromGroup(int groupId) {
        return getGroupById(groupId).getStudentMap();
    }

    public static void doaddGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {
        Set <Theams> theams = new HashSet<>();
        theamsIdList.forEach(id -> theams.add(TheamFunctionJpa.gettheamById(id)));
        HashMap <Integer, Student> studentHashMap = new HashMap<>();
        studentList.stream().map(user -> (Student)user)
                .forEach(student -> studentHashMap.put(student.getId(), student));
        Group group = new Group()
                .withTrainer(TrainerFunctionJpa.doGetTrainerById(trainerId))
                .withName("Groups_" +trainerId)
                .withTheam(theams)
                .withStudents(studentHashMap);
        log.info("Group add = {}", "Group " + group.getId() + "Theam: " + theams.toString() + "Student: " + studentHashMap.values().toString() );
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

      public static void doupdateGroup (int groupId, String act, int[] entytiIdforact) {
        log.info("In repository updateGroup = {}", groupId + " " + "  " + act + " " + Arrays.toString(entytiIdforact));
        UpdateStratageJpa updateGroupStratagyJpa;
        EntityManager em = null;
        Group group = GroupFunctionJpa.getGroupById(groupId);
          try  {
              em = sessionFactory.createEntityManager();
              updateGroupStratagyJpa = updateStratagyInject(act);
              assert updateGroupStratagyJpa != null;
              updateGroupStratagyJpa.updateGroup(group, entytiIdforact, em);
          }
          catch (MyJpaException e) {
              log.info("doupdateGroup  exception = {}", e.getMessage());
          }
    }

    private static UpdateStratageJpa updateStratagyInject(String act) throws MyJpaException {
        Map <String, UpdateStratageJpa> stratagyMap = new HashMap<>(Map.of(
                "studentdelete", new UpdateGroupStratagyJpaStudentDelete(),
                "studentadd", new UpdateGroupStratagyJpaStudentAdd(),
                "theamdelete", new UpdateGroupStratagyImplJpaTheamDelete(),
                "theamadd",new UpdateGroupStratagyImplJpaTheamAdd(),
                "trainer", new UpdateGroupStratagyImplJpaTrainerChange()));
        return stratagyMap.get(act);
    }

    public static Group getGroupById (int id) {
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
