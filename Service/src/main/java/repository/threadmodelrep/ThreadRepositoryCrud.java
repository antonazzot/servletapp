package repository.threadmodelrep;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import repository.RepositoryFactory;
import repository.modelrepository.modelservices.crudrepositorislikeservice.StudentCrudRepository;
import repository.modelrepository.modelservices.crudrepositorislikeservice.TrainerCrudRepository;
import repository.threadmodelrep.threadservices.crudthreadservices.GroupCrudRepository;
import repository.threadmodelrep.threadservices.crudthreadservices.MarkCrudRepository;
import repository.threadmodelrep.threadservices.crudthreadservices.SalaryCrudRepository;
import repository.threadmodelrep.threadservices.crudthreadservices.TheamCrudRepository;
import repository.threadmodelrep.threadservices.updategroupstratagy.UpdateStratageCrud;
import repository.threadmodelrep.threadservices.updategroupstratagy.crudstrategygroupupdate.*;
import threadmodel.Group;
import threadmodel.Mark;
import threadmodel.Salary;
import threadmodel.Theams;
import users.Student;
import users.Trainer;
import users.UserImpl;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ThreadRepositoryCrud implements ThreadRepository {
    private final GroupCrudRepository groupCrudRepository;
    private final TheamCrudRepository theamCrudRepository;
    private final MarkCrudRepository markCrudRepository;
    private final SalaryCrudRepository salaryCrudRepository;
    private final StudentCrudRepository studentCrudRepository;
    private final TrainerCrudRepository trainerCrudRepository;

    @Override
    public Map<Integer, Group> allGroup() {
        Map<Integer, Group> result = new HashMap<>();
        groupCrudRepository.findAll().forEach(group -> result.put(group.getId(), group));
        return result;
    }

    @Override
    public Map<Integer, Theams> allTheams() {
        Map<Integer, Theams> result = new HashMap<>();
        theamCrudRepository.findAll().forEach(theams -> result.put(theams.getId(), theams));
        return result;
    }

    @Override
    public Map<Trainer, List<Salary>> trainerSalary() {
        Map<Trainer, List<Salary>> result = new HashMap<>();
        RepositoryFactory.getRepository().allTrainer().values()
                .stream().map(user -> (Trainer) user)
                .forEach(trainer -> result.put(trainer, trainer.getSalarylist()));
        return result;
    }

    @Override
    public Map<UserImpl, Map<Theams, List<Mark>>> studentTheamMark(int StudentId) {
        Map<UserImpl, Map<Theams, List<Mark>>> result = new HashMap<>();
        Student student = (Student) RepositoryFactory.getRepository().getUserById(StudentId);
        Set<Theams> teams = student.getMarkMap().values().stream().map(Mark::getTheams).collect(Collectors.toSet());
        Map<Theams, List<Mark>> tempResultMap = new HashMap<>();
        for (Theams team : teams) {
            tempResultMap.put(team, student.getMarkMap().values().stream().filter(mark -> mark.getTheams().getId()==team.getId()).collect(Collectors.toList()));
        }
        result.put(student, tempResultMap);
        return result;

    }

    @Override
    public List<Mark> getMarkListbyTheam(Theams theam, int studentId) {
        return markCrudRepository.findByTheamsAndStudent(theam, RepositoryFactory.getRepository().getStudentById(studentId));
    }

    @Override
    public Map<Integer, Mark> getMarkIDListbyTheam(Theams theam, int studentId) {
        Map<Integer, Mark> result = new HashMap<>();
        markCrudRepository.findAll().forEach(mark -> result.put(mark.getId(), mark));
        return result;
    }

    @Override
    public Theams theamById(Integer id) {
        return theamCrudRepository.findById(id).get();
    }

    @Override
    public Set<Theams> theamFromGroup(Integer groupId) {
        return theamCrudRepository.findByGroupId(groupId);
    }

    @Override
    public Map<Integer, Student> studentsFromGroup(int groupId) {
        return groupCrudRepository.findById(groupId).get().getStudentMap();
    }

    @Override
    public void addTheam(String theam) {
        theamCrudRepository.save(new Theams().withValue(theam));
    }

    @Override
    public void addGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {
        Map<Integer, Student> studentMap = new HashMap<>();
        studentList.stream().map(user -> (Student) user).forEach(student -> studentMap.put(student.getId(), student));
        Set<Theams> theamsSet = new HashSet<>();
        theamsIdList.stream().map(this::theamById).forEach(theamsSet::add);
        Group group = new Group()
                .withStudents(studentMap)
                .withTheam(theamsSet)
                .withName("Group_" + trainerId)
                .withTrainer(RepositoryFactory.getRepository().getTrainerById(trainerId));
        groupCrudRepository.save(group);
    }

    @Override
    public Map<Integer, Theams> freeTheams() {
        Map<Integer, Theams> result = new HashMap<>(allTheams());
        List<Theams> busyTheams = new ArrayList<>();
        groupCrudRepository.findAll().forEach(group -> busyTheams.addAll(group.getTheamsSet()));
        for (Theams busyTheam : busyTheams) {
            result.remove(busyTheam.getId());
        }
        return result;
    }

    @Override
    public void addSalaryToTrainer(int trainerId, int salaryValue) {
        Salary salary = new Salary()
                .withTrainer(RepositoryFactory.getRepository().getTrainerById(trainerId))
                .withValue(salaryValue);
        salaryCrudRepository.save(salary);
    }

    @Override
    public void addMarkToStudent(int studentId, int theamID, int markValue) {
        Mark mark = new Mark()
                .withStudent(RepositoryFactory.getRepository().getStudentById(studentId))
                .withTheam(theamById(theamID))
                .withValue(markValue);
        markCrudRepository.save(mark);
    }

    @Override
    public void deleteMarksById(int[] tempMarksId, int theamId, int studentid) {
        List<Integer> integerList = new ArrayList<>();
        for (int i : tempMarksId) {
            integerList.add(i);
        }
        markCrudRepository.deleteAllById(integerList);
    }

    @Override
    public void changeMark(Map<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
        for (Map.Entry<Integer, Integer> entry : markIdMarkValue.entrySet()) {
            Mark markforchange = markCrudRepository.findById(entry.getKey()).get();
            markforchange.withValue(entry.getValue());
            markCrudRepository.save(markforchange);
        }

    }

    @Override
    public void updateGroup(int groupId, String act, int[] entytiIdforact) {
        Group group = groupCrudRepository.findById(groupId).get();
        Group changeGroup = chngeStrategyForUpdateGroup(act).updateGroup(group, entytiIdforact);
        groupCrudRepository.save(changeGroup);
    }


    private UpdateStratageCrud chngeStrategyForUpdateGroup(String act) {
        Map<String, UpdateStratageCrud> stratagyMap = Map.of(
                "studentdelete", new UpdateGroupStrategyCrudStudentDelete(),
                "studentadd", new UpdateGroupStrategyCrudStudentAdd(studentCrudRepository),
                "theamdelete", new UpdateGroupStrategyCrudTheamDelete(theamCrudRepository),
                "theamadd", new UpdateGroupStrategyCrudTheamAdd(theamCrudRepository),
                "trainer", new UpdateGroupStrategyCrudTrainerChange(trainerCrudRepository));
        return stratagyMap.get(act);
    }

    @Override
    public void updateTheam(int theamId, String theamName) {
        Theams theams = theamCrudRepository.findById(theamId).get();
        theams.withValue(theamName);
        theamCrudRepository.save(theams);
    }
}
