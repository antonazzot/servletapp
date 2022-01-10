package repository.threadmodelrep;

import helperutils.closebaseconnection.JpaUtils;
import helperutils.myexceptionutils.MyJpaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.RepositoryFactory;
import repository.threadmodelrep.threadfunction.functionjpa.GroupFunctionJpa;
import repository.threadmodelrep.threadfunction.functionjpa.MarkFunctionJpa;
import repository.threadmodelrep.threadfunction.functionorm.Markormservice;
import threadmodel.Group;
import threadmodel.Mark;
import threadmodel.Salary;
import threadmodel.Theams;
import users.Student;
import users.Trainer;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Slf4j
@Repository
public class ThreadRepositoryOrm implements ThreadRepository {

    private EntityManagerFactory emf;
    private final ThreadLocal<EntityManager> emThreadLocal = new ThreadLocal<>();
    @Autowired
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Map<Integer, Group> allGroup() {
        Map <Integer, Group> result = new HashMap<>();
        try {
            begin();
            getEm().createQuery("from Group", Group.class)
                    .getResultList().forEach(group -> result.put(group.getId(), group));
        }
        catch (IllegalArgumentException e) {
            JpaUtils.rollBackQuietly(getEm(), e);
        }
        finally {
            JpaUtils.closeQuietly(getEm());
        }
        return  result;
    }

    @Override
    public Map<Integer, Theams> allTheams() {
        Map <Integer, Theams> result = new HashMap<>();
        try {
            begin();
            getEm().createQuery("from Theams", Theams.class).getResultList()
                    .stream().forEach(theams -> result.put(theams.getId(), theams));
        }
        catch (IllegalArgumentException e) {
            JpaUtils.rollBackQuietly(getEm(), e);
        }
        finally {
            JpaUtils.closeQuietly(getEm());
        }
        return  result;
    }

    @Override
    public Map<Trainer, List<Salary>> trainerSalary() {
        Map <Trainer, List<Salary>> result = new HashMap<>();
        RepositoryFactory.getRepository().allTrainer().values()
                .stream()
                .map(user -> (Trainer) user)
                .forEach(trainer -> result.put(trainer, trainer.getSalarylist()));
        return result;
    }

    @Override
    public Map<UserImpl, Map<Theams, List<Mark>>> studentTheamMark(int StudentId) {
        Map<UserImpl, Map<Theams, List<Mark>>> result = new HashMap<>();
        Student student = RepositoryFactory.getRepository().getStudentById(StudentId);
        Set<Theams> theams = Markormservice.getTheamsSet(student);
        Map<Theams, List<Mark>> theamsListHashMap = Markormservice.getTheamsListHashMap(StudentId, theams);
        result.put(student, theamsListHashMap);
        return result;
    }

    @Override
    public List<Mark> getMarkListbyTheam(Theams theam, int studentId) {
        try {
            begin();
            TypedQuery<Mark> query = getEm().createQuery("select m from Mark m where m.student=:student_id and m.theams=:theam_id", Mark.class);
            query.setParameter("student_id", studentId);
            query.setParameter("theam_id", theam.getId());
            return query.getResultList();
        }
        catch (IllegalArgumentException e) {
            JpaUtils.rollBackQuietly(getEm(), e);
        }
        finally {
            JpaUtils.closeQuietly(getEm());
        }
        return  null;
    }

    @Override
    public Map<Integer, Mark> getMarkIDListbyTheam(Theams theam, int studentId) {
        Map<Integer, Mark> result = new HashMap<>();
        getMarkListbyTheam(theam, studentId)
                .forEach(mark -> result.put(mark.getId(), mark));
        return result;
    }

    @Override
    public Theams theamById(Integer id) {
        try {
            begin();
            return getEm().find(Theams.class, id);
        }
        catch (IllegalArgumentException e) {
        JpaUtils.rollBackQuietly(getEm(), e);
    }
        finally {
        JpaUtils.closeQuietly(getEm());
    }
        return  null;
    }

    @Override
    public Set<Theams> theamFromGroup(Integer groupId) {
        return  getGroupById(groupId).getTheamsSet();
    }

    @Override
    public Map<Integer, Student> studentsFromGroup(int groupId) {
        return getGroupById(groupId).getStudentMap();
    }

    @Override
    public void addTheam(String theam) {
    try {
        begin();
        getEm().persist(new Theams().withValue(theam));
        commit();
    }
    catch (IllegalArgumentException e) {
        JpaUtils.rollBackQuietly(getEm(), e);
    }
    finally {
        JpaUtils.closeQuietly(getEm());
    }
    }

    @Override
    public void addGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {
        Map <Integer, Student> studentMap = new HashMap<>();
        studentList.stream().map(user -> (Student)user)
                .forEach(student -> studentMap.put(student.getId(),student));
        Set<Theams> theamsSet = new HashSet<>();
        theamsIdList.stream()
                .forEach(id -> theamsSet.add(theamById(id)));
        Trainer trainer = RepositoryFactory.getRepository().getTrainerById(trainerId);
        try {
            begin();
            getEm().persist(
                    new Group()
                            .withName("Groups:" + trainerId)
                            .withTrainer(trainer)
                            .withStudents(studentMap)
                            .withTheam(theamsSet)
            );
            commit();
        }
        catch (IllegalArgumentException e) {
            JpaUtils.rollBackQuietly(getEm(), e);
        }
        finally {
            JpaUtils.closeQuietly(getEm());
        }
    }

    @Override
    public Map<Integer, Theams> freeTheams() {
        Map<Integer, Theams> result = new HashMap<>();
        try {
            EntityManager entityManager = emf.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createQuery("select t from Theams t where t.group=null", Theams.class).getResultList()
                  .stream().forEach(theams -> result.put(theams.getId(), theams));
          return  result;
        }
        catch (IllegalArgumentException e) {
            JpaUtils.rollBackQuietly(getEm(), e);
        }
        finally {
            JpaUtils.closeQuietly(getEm());
        }
//        Map<Integer, Theams> busyTheam = getBuzyTeam();
//        Map<Integer, Theams> freeTh = new HashMap<>(ThreadRepositoryFactory.getRepository().allTheams());
//        Map<Integer, Theams> allTheam = Map.copyOf(freeTh);
//        log.info("free Start {}", busyTheam.values());
//        if (GroupFunctionJpa.getAllGroup().isEmpty()) {
//            return freeTh;
//        } else {
//            log.info("in for {}", busyTheam.values());
//            for (Theams allTh :
//                    allTheam.values()) {
//                for (Theams bTh :
//                        busyTheam.values()) {
//                    if (allTh.getId() == bTh.getId()) {
//                        freeTh.remove(allTh.getId());
//                    }
//                }
//            }
//            return freeTh;
        return result;
    }

    @Override
    public void addSalaryToTrainer(int trainerId, int salaryValue) {
        Trainer trainer = RepositoryFactory.getRepository().getTrainerById(trainerId);
    try {
        begin();
        getEm().persist(new Salary()
                .withTrainer(trainer)
                .withValue(salaryValue));
        commit();
    }
        catch (IllegalArgumentException e) {
        JpaUtils.rollBackQuietly(getEm(), e);
    }
        finally {
        JpaUtils.closeQuietly(getEm());
    }
    }

    @Override
    public void addMarkToStudent(int studentId, int theamID, int markValue) {
        Student student = RepositoryFactory.getRepository().getStudentById(studentId);
        Theams theams = theamById(theamID);
    try {
        begin();
        getEm().persist(new Mark()
                .withStudent(student)
                .withTheam(theams)
                .withValue(markValue));
    }
    catch (IllegalArgumentException e) {
        JpaUtils.rollBackQuietly(getEm(), e);
    }
    finally {
        JpaUtils.closeQuietly(getEm());
    }
    }

    @Override
    public void deleteMarksById(int[] tempMarksId, int theamId, int studentid) {
    try {
        for (int i : tempMarksId) {
            Mark mark = getMarkById(i);
            begin();
            getEm().remove(mark);
            commit();
        }
    }
    catch (IllegalArgumentException e) {
        JpaUtils.rollBackQuietly(getEm(), e);
    }
    finally {
        JpaUtils.closeQuietly(getEm());
    }
    }

    @Override
    public void changeMark(Map<Integer, Integer> markIdMarkValue, int studentId, int theamId) {

        for (Map.Entry<Integer, Integer> map : markIdMarkValue.entrySet()) {
            Mark mark = getMarkById(map.getKey());
            if (map.getValue()!=null)
                mark = mark.withValue(map.getValue());
            try {
                begin();
                if (map.getValue()!=null)
                getEm().merge(mark);
            }
            catch (IllegalArgumentException e) {
                JpaUtils.rollBackQuietly(getEm(), e);
            }
            finally {
                JpaUtils.closeQuietly(getEm());
            }
        }
    }

    @Override
    public void updateGroup(int groupId, String act, int[] entytiIdforact) {
    GroupFunctionJpa.doupdateGroup(groupId, act, entytiIdforact);
    }

    @Override
    public void updateTheam(int theamId, String theamName) {
        Theams theams = theamById(theamId);
        if (!theamName.equals(""))
         theams = theams.withValue(theamName);
        try {
    begin();
    getEm().merge(theams);
}
catch (IllegalArgumentException e) {
    JpaUtils.rollBackQuietly(getEm(), e);
}
finally {
    JpaUtils.closeQuietly(getEm());
}
    }

    public EntityManager getEm () {
//        if (emThreadLocal.get()==null){
            emThreadLocal.set(emf.createEntityManager());
//        }
        return emThreadLocal.get();
    }
    public void begin () {
        getEm().getTransaction().begin();
    }

    public void commit () {
        getEm().getTransaction().commit();
    }

    public void rollback () {
        getEm().getTransaction().rollback();
    }

    public void close () {
        getEm().close();
    }

    private Group getGroupById (Integer groupId) {
        Group group = null;
        try {
          begin();
          group = getEm().find(Group.class, groupId);
        } catch (IllegalArgumentException e) {
            JpaUtils.rollBackQuietly(getEm(), e);
        }
        finally {
            JpaUtils.closeQuietly(getEm());
        }
        return  group;

    }

//    private  Map<Integer, Theams> getBuzyTeam() {
//        Map<Integer, Theams> busyTheam = new HashMap<>();
//        Set<Theams> theamsSet = new HashSet<>();
//        GroupFunctionJpa.getAllGroup().values()
//                .forEach(group -> theamsSet.addAll(group.getTheamsSet()));
//        for (Theams theams : theamsSet) {
//            busyTheam.put(theams.getId(), theams);
//        }
//        return busyTheam;
//    }
    private  Mark getMarkById(int id) {
        EntityManager em = null;
        try {
            TypedQuery<Mark> query = getEm().createNamedQuery("getMarkById", Mark.class);
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
