package repository.modelrepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import repository.modelrepository.modelservices.deleteentitystratege.springormstrategy.*;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import users.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

//@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Repository("Orm")
public class UserSpringOrmRepository implements UserRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Map<Integer, UserImpl> allUser() {
        Map<Integer, UserImpl> result = new HashMap<>();
        result.putAll(allTrainer());
        result.putAll(allStudent());
        result.putAll(allAdmin());
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Map<Integer, UserImpl> allTrainer() {
        Map<Integer, UserImpl> result = new HashMap<>();
        TypedQuery<Trainer> alltrainer = em.createQuery("select t from Trainer t where t.role = :role", Trainer.class);
        alltrainer.setParameter("role", Role.TRAINER);
        alltrainer.getResultList().stream().forEach(trainer -> result
                .put(trainer.getId(), trainer));
        return result;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Map<Integer, UserImpl> allStudent() {
        Map<Integer, UserImpl> result = new HashMap<>();
        TypedQuery<Student> allStudent = em.createQuery("select s from Student s where s.role = :role", Student.class);
        allStudent.setParameter("role", Role.STUDENT);
        allStudent.getResultList().forEach(student -> result.put(student.getId(), student));
        return result;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Map<Integer, UserImpl> allAdmin() {
        Map<Integer, UserImpl> result = new HashMap<>();
        TypedQuery<Administrator> allAdmin = em.createQuery("select a from Administrator a where a.role = :role", Administrator.class);
        allAdmin.setParameter("role", Role.ADMINISTRATOR);
        allAdmin.getResultList().forEach(administrator -> result.put(administrator.getId(), administrator));
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public UserImpl getUserById(Integer id) {
        return allUser().get(id);
    }

    @Override
    public UserImpl getUserByLogin(String login) {
        return allUser().values().stream().filter(user -> user.getLogin().equalsIgnoreCase(login)).findFirst().orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int saveUser(UserImpl user) {
        em.persist(user);
        return user.getId();
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        DeleteStratageOrm deleteStratageORM = changeStratageForDelete(entity);
        deleteStratageORM.doDeleteEntity(id);
        return Optional.empty();
    }

    private  DeleteStratageOrm changeStratageForDelete(String entity) {

        Map<String, DeleteStratageOrm> stratageMap = Map.of(
                "student", new DeleteStudentImpOrm(em),
                "trainer", new DeleteTrainerImpOrm(em),
                "administrator", new DeleteAdminImpOrm(em),
                "group", new DeleteGroupImpOrm(em),
                "theam", new DeleteTheamImpOrm(em)
        );
        return stratageMap.get(entity);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserImpl updateUser(UserImpl user) {
        return em.merge(user);
    }

    @Override
    public Map<Integer, UserImpl> freeTrainer() {
        Collection<Group> values = ThreadRepositoryFactory.getRepository().allGroup().values();
        if (values.isEmpty())
            return allTrainer();
        else {
            List<UserImpl> busyTrainer = new ArrayList<>();
            values.forEach(group -> busyTrainer.add(group.getTrainer()));
            return freeTrainerexecute(busyTrainer);
        }
    }

    private Map<Integer, UserImpl> freeTrainerexecute(List<UserImpl> busyTrainer) {
        Map<Integer, UserImpl> result = new HashMap<>(allTrainer());
        for (UserImpl alltrainer :
                allTrainer().values()) {
            for (UserImpl busyTr : busyTrainer) {
                if (alltrainer.getId() == busyTr.getId())
                    result.remove(busyTr.getId());
            }
        }
        return result;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<Student> studentFromGroup(Integer groupId) {
        List<Student> result = new ArrayList<>();
        Group group = em.find(Group.class, groupId);
        result.addAll(group.getStudentMap().values());
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Trainer getTrainerById(int id) {
        return em.find(Trainer.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Administrator getAdministratorById(int id) {
        return em.find(Administrator.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Student getStudentById(int id) {
        return em.find(Student.class, id);
    }

    @Transactional
    @Override
    public int saveTempStudent(TempStudent tempStudent) {
        em.persist(tempStudent);
        return tempStudent.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<TempStudent> findAllTempSstudent() {
        TypedQuery<TempStudent> allTempStudent =  em.createQuery("select t from TempStudent t", TempStudent.class);
        return  allTempStudent.getResultList();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public TempStudent getTempUserById(Integer id) {
        return em.find(TempStudent.class, id);
    }

    @Transactional
    @Override
    public void removeTempStudent(Integer id) {
        em.remove(getTempUserById(id));
    }
}
