package repository.threadmodelrep;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import repository.RepositoryFactory;
import repository.threadmodelrep.threadfunction.functionorm.Markormservice;
import threadmodel.Group;
import threadmodel.Mark;
import threadmodel.Salary;
import threadmodel.Theams;
import users.Student;
import users.Trainer;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Transactional(propagation = Propagation.REQUIRED)
@Slf4j
@RequiredArgsConstructor
@Repository
public class ThreadRepositoryOrm implements ThreadRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Map<Integer, Group> allGroup() {
        Map<Integer, Group> result = new HashMap<>();
        em.createQuery("from Group", Group.class).getResultList().forEach(group -> result.put(group.getId(), group));
        return result;
    }

    @Override
    public Map<Integer, Theams> allTheams() {
        Map<Integer, Theams> result = new HashMap<>();
        em.createQuery("from Theams", Theams.class).getResultList().stream().forEach(theams -> result.put(theams.getId(), theams));
        return result;
    }

    @Override
    public Map<Trainer, List<Salary>> trainerSalary() {
        Map<Trainer, List<Salary>> result = new HashMap<>();
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
        TypedQuery<Mark> query = em.createQuery("select m from Mark m where m.student.id=:student_id and m.theams.id=:theam_id", Mark.class);
        query.setParameter("student_id", studentId);
        query.setParameter("theam_id", theam.getId());
        return query.getResultList();
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
        return em.find(Theams.class, id);
    }

    @Override
    public Set<Theams> theamFromGroup(Integer groupId) {
        return getGroupById(groupId).getTheamsSet();
    }

    @Override
    public Map<Integer, Student> studentsFromGroup(int groupId) {
        return getGroupById(groupId).getStudentMap();
    }

    @Override
    public void addTheam(String theam) {
        em.persist(new Theams().withValue(theam));
    }

    @Override
    public void addGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {
        Map<Integer, Student> studentMap = new HashMap<>();
        studentList.stream().map(user -> (Student) user).forEach(student -> studentMap.put(student.getId(), student));
        Set<Theams> theamsSet = new HashSet<>();
        theamsIdList.stream().forEach(id -> theamsSet.add(theamById(id)));
        Trainer trainer = RepositoryFactory.getRepository().getTrainerById(trainerId);
        em.persist(
                new Group()
                        .withName("Groups:" + trainerId)
                        .withTrainer(trainer)
                        .withStudents(studentMap)
                        .withTheam(theamsSet));
    }

    @Override
    public Map<Integer, Theams> freeTheams() {
        Map<Integer, Theams> result = new HashMap<>();
        em.createQuery("select t from Theams t where t.group=null", Theams.class).getResultList().stream().forEach(theams -> result.put(theams.getId(), theams));
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addSalaryToTrainer(int trainerId, int salaryValue) {
        em.persist(new Salary()
                .withTrainer(RepositoryFactory.getRepository().getTrainerById(trainerId))
                .withValue(salaryValue));
    }

    @Override
    public void addMarkToStudent(int studentId, int theamID, int markValue) {
        em.persist(new Mark()
                .withStudent(RepositoryFactory.getRepository().getStudentById(studentId))
                .withTheam(theamById(theamID))
                .withValue(markValue));
    }

    @Override
    public void deleteMarksById(int[] tempMarksId, int theamId, int studentid) {
        for (int i : tempMarksId) {
            Mark mark = getMarkById(i);
            em.remove(mark);
        }
    }

    @Override
    public void changeMark(Map<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
        for (Map.Entry<Integer, Integer> map : markIdMarkValue.entrySet()) {
            Mark mark = getMarkById(map.getKey());
            if (map.getValue() != null) {
                mark = mark.withValue(map.getValue());
                em.merge(mark);
            }
        }
    }

    @Override
    public void updateGroup(int groupId, String act, int[] entytiIdforact) {
        ThreadRepositoryFactory.getRepository().updateGroup(groupId, act, entytiIdforact);
    }

    @Override
    public void updateTheam(int theamId, String theamName) {
        Theams theams = theamById(theamId);
        if (!theamName.equals(""))
            theams = theams.withValue(theamName);
        em.merge(theams);
    }

    private Group getGroupById(Integer groupId) {
        return em.find(Group.class, groupId);
    }

    private Mark getMarkById(int id) {
        TypedQuery<Mark> query = em.createNamedQuery("getMarkById", Mark.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

}
