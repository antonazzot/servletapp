package repository.modelrepository;

import helperutils.closebaseconnection.JpaUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import repository.modelrepository.modelfunction.deleteentitystratage.jpastratagy.*;
import repository.threadmodelrep.ThreadRepositoryFactory;
import repository.threadmodelrep.threadfunction.functionjpa.GroupFunctionJpa;
import threadmodel.Group;
import users.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.*;

@RequiredArgsConstructor
@Repository("Orm")
public class UserSpringOrmRepository implements UserRepository{
    private final EntityManagerFactory emf;
    private final ThreadLocal<EntityManager> emThreadLocal = new ThreadLocal<>();

    @Override
    public Map<Integer, UserImpl> allUser() {
        Map <Integer, UserImpl> result = new HashMap<>();
        try {
            begin();
            getEm().createQuery("select u from users u", UserImpl.class).getResultList()
                    .forEach(user -> result.put(user.getId(),user));

        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(getEm(),e);
        }
        finally {
            JpaUtils.closeQuietly(getEm());
        }


        return result;
    }

    @Override
    public Map<Integer, UserImpl> allTrainer() {
        Map <Integer, UserImpl> result = new HashMap<>();
       try {
        begin();
        TypedQuery<Trainer> alltrainer = getEm().createQuery("select t from Trainer t where t.role = :role", Trainer.class);
        alltrainer.setParameter("role", Role.TRAINER);
        alltrainer.getResultList().forEach(trainer -> result.put(trainer.getId(), trainer));
    }
        catch (Exception e) {
        JpaUtils.rollBackQuietly(getEm(),e);
    }
        finally {
        JpaUtils.closeQuietly(getEm());
    }
        return result;
    }

    @Override
    public Map<Integer, UserImpl> allStudent() {
        Map <Integer, UserImpl> result = new HashMap<>();
       try {
        begin();
        TypedQuery<Student> allStudent = getEm().createQuery("select s from Student s where s.role = :role", Student.class);
        allStudent.setParameter("role", Role.STUDENT);
        allStudent.getResultList().forEach(student -> result.put(student.getId(), student));
    }
        catch (Exception e) {
        JpaUtils.rollBackQuietly(getEm(),e);
    }
        finally {
        JpaUtils.closeQuietly(getEm());
    }
        return result;
    }

    @Override
    public Map<Integer, UserImpl> allAdmin() {
        Map <Integer, UserImpl> result = new HashMap<>();
      try {
        begin();
        TypedQuery<Administrator> allAdmin = getEm().createQuery("select a from Administrator a where a.role = :role", Administrator.class);
        allAdmin.setParameter("role", Role.ADMINISTRATOR);
        allAdmin.getResultList().forEach(administrator -> result.put(administrator.getId(), administrator));
    }
        catch (Exception e) {
        JpaUtils.rollBackQuietly(getEm(),e);
    }
        finally {
        JpaUtils.closeQuietly(getEm());
    }
        return result;
    }

    @Override
    public UserImpl getUserById(Integer id) {
        UserImpl user = null;
        try {
        begin();
        user = getEm().find(UserImpl.class, id);    }
        catch (Exception e) {
        JpaUtils.rollBackQuietly(getEm(),e);
    }
        finally {
        JpaUtils.closeQuietly(getEm());
    }
        return user;
    }

    @Override
    public int saveUser(UserImpl user) {
     try {
        begin();
        getEm().persist(user);
        commit();
    }
        catch (Exception e) {
        JpaUtils.rollBackQuietly(getEm(),e);
    }
        finally {
        JpaUtils.closeQuietly(getEm());
    }
        return user.getId();
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        DeleteStratageJPA deleteStratageJPA = changeStratageForDelete(entity);
        deleteStratageJPA.doDeleteEntity(id, getEm());
        return Optional.ofNullable(getStudentById(id));
    }

    private static DeleteStratageJPA changeStratageForDelete(String entity) {
        Map<String, DeleteStratageJPA> stratageMap = Map.of(
                "student", new DeleteStudentImpJPA(),
                "trainer", new DeleteTrainerImpJPA(),
                "administrator", new DeleteAdminImpJPA(),
                "group", new DeleteGroupImpJPA(),
                "theam", new DeleteTheamImpJPA()
        );
        return stratageMap.get(entity);
    }

    @Override
    public UserImpl updateUser(UserImpl user) {
        begin();
        UserImpl merge = getEm().merge(user);
        commit();
        return merge;
    }

    @Override
    public Map<Integer, UserImpl> freeTrainer() {
        if (ThreadRepositoryFactory.getRepository().allGroup().isEmpty())
            return allTrainer();
        else {
            List<UserImpl> busyTrainer = new ArrayList<>();
            GroupFunctionJpa.getAllGroup().values()
                    .forEach(group -> busyTrainer.add(group.getTrainer()));
            return freeTrainerexecute(busyTrainer);
        }
    }

    private  Map<Integer, UserImpl> freeTrainerexecute(List<UserImpl> busyTrainer) {
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
        List <Student> result = new ArrayList<>();
        Group group = getEm().find(Group.class, groupId);
        group.getStudentMap().values().forEach(student -> result.add(student));
        return  result;
    }

    @Override
    public Trainer getTrainerById(int id) {
     try {
       begin();
       return getEm().find(Trainer.class, id);
    }
        catch (Exception e) {
        JpaUtils.rollBackQuietly(getEm(),e);
    }
        finally {
        JpaUtils.closeQuietly(getEm());
    }
     return null;
    }
    @Override
    public Administrator getAdministratorById(int id) {
       try {
        begin();
        return getEm().find(Administrator.class, id);
    }
        catch (Exception e) {
        JpaUtils.rollBackQuietly(getEm(),e);
    }
        finally {
        JpaUtils.closeQuietly(getEm());
    }
       return null;
    }

    @Override
    public Student getStudentById(int id) {
     try {
        begin();
        return getEm().find(Student.class, id);
    }
        catch (Exception e) {
        JpaUtils.rollBackQuietly(getEm(),e);
    }
        finally {
        JpaUtils.closeQuietly(getEm());
    }
     return null;
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


}
