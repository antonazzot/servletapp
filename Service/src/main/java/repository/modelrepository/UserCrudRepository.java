package repository.modelrepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import repository.RepositoryFactory;
import repository.modelrepository.modelservices.crudrepositorislikeservice.AdministratorCrudRepository;
import repository.modelrepository.modelservices.crudrepositorislikeservice.StudentCrudRepository;
import repository.modelrepository.modelservices.crudrepositorislikeservice.TempStudentCrudRepoitory;
import repository.modelrepository.modelservices.crudrepositorislikeservice.TrainerCrudRepository;
import repository.threadmodelrep.threadservices.crudthreadservices.GroupCrudRepository;
import repository.threadmodelrep.threadservices.crudthreadservices.TheamCrudRepository;
import users.*;

import java.util.*;

@RequiredArgsConstructor
@Repository("Crud")
public class UserCrudRepository implements UserRepository {

    private final StudentCrudRepository studentCrudRepository;
    private final AdministratorCrudRepository administratorCrudRepository;
    private final TrainerCrudRepository trainerCrudRepository;
    private final TheamCrudRepository theamCrudRepository;
    private final GroupCrudRepository groupCrudRepository;
    private final TempStudentCrudRepoitory tempStudentCrudRepoitory;

    @Transactional
    @Override
    public Map<Integer, UserImpl> allUser() {
        Map<Integer, UserImpl> result = new HashMap<>();
        result.putAll(allTrainer());
        result.putAll(allAdmin());
        result.putAll(allStudent());
        return result;
    }

    @Override
    public Map<Integer, UserImpl> allTrainer() {
        Map<Integer, UserImpl> result = new HashMap<>();
        trainerCrudRepository.findByrole(Role.TRAINER).forEach(user -> result.put(user.getId(), user));
        return result;
    }

    @Override
    public Map<Integer, UserImpl> allStudent() {
        Map<Integer, UserImpl> result = new HashMap<>();
        studentCrudRepository.findByrole(Role.STUDENT).forEach(user -> result.put(user.getId(), user));
        return result;
    }

    @Override
    public Map<Integer, UserImpl> allAdmin() {
        Map<Integer, UserImpl> result = new HashMap<>();
        administratorCrudRepository.findByrole(Role.ADMINISTRATOR).forEach(user -> result.put(user.getId(), user));
        return result;
    }

    @Override
    public Map<Integer, TempStudent> allTemp() {
        Map<Integer, TempStudent> result = new HashMap<>();
        tempStudentCrudRepoitory.findAll().forEach(user -> result.put(user.getId(), user));
        return result;
    }

    @Override
    public UserImpl getUserById(Integer id) {
        return allUser().get(id);
    }

    @Override
    public UserImpl getUserByLogin(String login) {
        Optional<UserImpl> tempUser = RepositoryFactory.getRepository().allUser().values().
                stream().filter(user -> user.getLogin().equalsIgnoreCase(login)).findFirst();
        return tempUser.orElse(null);
    }

    @Override
    public int saveUser(UserImpl user) {
        if (user.getRole().equals(Role.ADMINISTRATOR))
            administratorCrudRepository.save((Administrator) user);
        else if (user.getRole().equals(Role.TRAINER))
            trainerCrudRepository.save((Trainer) user);
        else studentCrudRepository.save((Student) user);

        return user.getId();
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        CrudRepository<?, Integer> repositoryForDelete = changeStrategyForDelete(entity);
        repositoryForDelete.deleteById(id);
        return Optional.empty();
    }

    private CrudRepository<?, Integer> changeStrategyForDelete(String entity) {
        Map<String, CrudRepository> map = Map.of(
                "student", studentCrudRepository,
                "trainer", trainerCrudRepository,
                "administrator", administratorCrudRepository,
                "theam", theamCrudRepository,
                "group", groupCrudRepository
        );
        return map.get(entity);
    }

    @Override
    public UserImpl updateUser(UserImpl user) {
        if (user.getRole().equals(Role.ADMINISTRATOR))
            administratorCrudRepository.save((Administrator) user);
        else if (user.getRole().equals(Role.TRAINER))
            trainerCrudRepository.save((Trainer) user);
        else studentCrudRepository.save((Student) user);

//        UserImpl oldUser = getUserById(user.getId());
//        changeStrategyByRole(user.getRole()).save(user);

        return user;
    }

    private CrudRepository<?, Integer> changeStrategyByRole(Role role) {
        Map<Role, CrudRepository> map = Map.of(
                Role.STUDENT, studentCrudRepository,
                Role.TRAINER, trainerCrudRepository,
                Role.ADMINISTRATOR, administratorCrudRepository
        );
        return map.get(role);
    }

    @Override
    public Map<Integer, UserImpl> freeTrainer() {
        Map<Integer, UserImpl> result = new HashMap<>();
        Collection<UserImpl> all = allTrainer().values();
        List<Trainer> busy = new ArrayList<>();
        groupCrudRepository.findAll().forEach(group -> busy.add(group.getTrainer()));
        all.removeAll(busy);
        all.forEach(trainer -> result.put(trainer.getId(), trainer));
        return result;
    }

    @Override
    public List<Student> studentFromGroup(Integer groupId) {
        return (List<Student>) groupCrudRepository.findById(groupId).get().getStudentMap().values();
    }

    @Override
    public Trainer getTrainerById(int id) {
        return trainerCrudRepository.findById(id).get();
    }

    @Override
    public Administrator getAdministratorById(int id) {
        return administratorCrudRepository.findById(id).get();
    }

    @Override
    public Student getStudentById(int id) {
        return studentCrudRepository.findById(id).get();
    }

    @Override
    public int saveTempStudent(TempStudent tempStudent) {
        TempStudent save = tempStudentCrudRepoitory.save(tempStudent);
       return save.getId();
    }

    @Override
    public List<TempStudent> findAllTempSstudent() {
        return (List<TempStudent>) tempStudentCrudRepoitory.findAll();
    }

    @Override
    public TempStudent getTempUserById(Integer id) {
        Optional<TempStudent> byId = tempStudentCrudRepoitory.findById(id);
        return byId.orElse(null);
    }

    @Override
    public void removeTempStudent(Integer id) {
       tempStudentCrudRepoitory.deleteById(id);
    }
}
