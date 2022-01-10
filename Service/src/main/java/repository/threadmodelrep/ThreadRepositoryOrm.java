package repository.threadmodelrep;

import helperutils.closebaseconnection.JpaUtils;
import helperutils.myexceptionutils.MyJpaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.RepositoryFactory;
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
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            getEm().createQuery("select g from group g", Group.class).getResultList()
                    .stream().forEach(group -> result.put(group.getId(), group));
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
            getEm().createQuery("select t from theams g", Theams.class).getResultList()
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
            TypedQuery<Mark> query = getEm().createQuery("select m from mark m where student_id=:student_id and theaam_id=:theam_id", Mark.class);
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
        ThreadRepositoryFactory.getRepository().getMarkListbyTheam(theam, studentId)
                .stream().forEach(mark -> result.put(mark.getId(), mark));
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
        try {
            begin();
            getEm().persist(
                    new Group()
                            .withName("Groups:" + trainerId)
                            .withTrainer(RepositoryFactory.getRepository().getTrainerById(trainerId))
                            .withStudents(new HashMap<>())
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
        return null;
    }

    @Override
    public void addSalaryToTrainer(int trainerId, int salaryValue) {

    }

    @Override
    public void addMarkToStudent(int studentId, int theamID, int markValue) {

    }

    @Override
    public void deleteMarksById(int[] tempMarksId, int theamId, int studentid) {

    }

    @Override
    public void changeMark(Map<Integer, Integer> markIdMarkValue, int studentId, int theamId) {

    }

    @Override
    public void updateGroup(int groupId, String act, int[] entytiIdforact) {

    }

    @Override
    public void updateTheam(int theamId, String theamName) {

    }

    public EntityManager getEm () {
        if (emThreadLocal.get()==null){
            emThreadLocal.set(emf.createEntityManager());
        }
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

}
