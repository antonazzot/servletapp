package repository.modelrepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import repository.modelrepository.modelfunction.deleteentitystratage.springormstratagy.*;
import repository.modelrepository.modelfunction.functionjpaerepositiry.AdminFunctionJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.StudentFunctionJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.TrainerFunctionJpa;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import users.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Repository("Orm")
public class UserSpringOrmRepository implements UserRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Map<Integer, UserImpl> allUser() {
        Map<Integer, UserImpl> result = new HashMap<>();
        result.putAll(TrainerFunctionJpa.getallTrainer());
        result.putAll(StudentFunctionJpa.getAllStudent());
        result.putAll(AdminFunctionJpa.getAllAdmin());
        return result;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Map<Integer, UserImpl> allTrainer() {
        Map<Integer, UserImpl> result = new HashMap<>();
        TypedQuery<Trainer> alltrainer = em.createQuery("select t from Trainer t where t.role = :role", Trainer.class);
        alltrainer.setParameter("role", Role.TRAINER);
        alltrainer.getResultList().stream().forEach(trainer -> result
                .put(trainer.getId(), trainer));
        return result;
    }

    @Override
    public Map<Integer, UserImpl> allStudent() {
        Map<Integer, UserImpl> result = new HashMap<>();
        TypedQuery<Student> allStudent = em.createQuery("select s from Student s where s.role = :role", Student.class);
        allStudent.setParameter("role", Role.STUDENT);
        allStudent.getResultList().forEach(student -> result.put(student.getId(), student));
        return result;
    }

    @Override
    public Map<Integer, UserImpl> allAdmin() {
        Map<Integer, UserImpl> result = new HashMap<>();
        TypedQuery<Administrator> allAdmin = em.createQuery("select a from Administrator a where a.role = :role", Administrator.class);
        allAdmin.setParameter("role", Role.ADMINISTRATOR);
        allAdmin.getResultList().forEach(administrator -> result.put(administrator.getId(), administrator));
        return result;
    }

    @Override
    public UserImpl getUserById(Integer id) {
        return em.find(UserImpl.class, id);
    }

    @Override
    public int saveUser(UserImpl user) {
        em.persist(user);
        return user.getId();
    }

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

    @Override
    public List<Student> studentFromGroup(Integer groupId) {
        List<Student> result = new ArrayList<>();
        Group group = em.find(Group.class, groupId);
        result.addAll(group.getStudentMap().values());
        return result;
    }

    @Override
    public Trainer getTrainerById(int id) {
        return em.find(Trainer.class, id);
    }

    @Override
    public Administrator getAdministratorById(int id) {
        return em.find(Administrator.class, id);
    }

    @Override
    public Student getStudentById(int id) {
        return em.find(Student.class, id);
    }
}
