package Repository.ThreadModelRep;

import Action.IdFactory;
import DataBase.DataBaseInf;
import Repository.RepositoryFactory;
import ThreadModel.Group;
import ThreadModel.Mark;
import ThreadModel.Salary;
import ThreadModel.Theams;
import Users.Student;
import Users.Trainer;
import Users.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ThreadRepositoryImplInMemory implements ThreadRepository {
    private static final Logger log = LoggerFactory.getLogger(ThreadRepositoryImpl.class);
    private static ThreadRepositoryImplInMemory instance;

    private ThreadRepositoryImplInMemory () {
    }

    public static ThreadRepositoryImplInMemory getInstance() {
        if (instance == null) {
            synchronized (ThreadRepositoryImpl.class) {
                if (instance == null) {
                    instance = new ThreadRepositoryImplInMemory();
                }
            }
        }
        return instance;
    }
    @Override
    public HashMap<Integer, Group> allGroup() {
        return (HashMap<Integer, Group>) DataBaseInf.groupHashMap;
    }

    @Override
    public HashMap<Integer, Theams> allTheams() {
        return (HashMap<Integer, Theams>) DataBaseInf.theamsHashMap;
    }

    @Override
    public HashMap<Trainer, List<Salary>> trainerSalary() {
        HashMap <Trainer, List <Salary>> result = new HashMap<>();
        for (UserImpl trainer:
             DataBaseInf.trainerHashMap.values()) {
            result.put((Trainer) trainer, ((Trainer) trainer).getSalarylist());
        }
        return result;
    }

    @Override
    public HashMap<UserImpl, HashMap<Theams, List<Mark>>> studentTheamMark(int StudentId) {
        HashMap<UserImpl, HashMap<Theams, List<Mark>>> result = new HashMap<>();
        for (UserImpl student:
             DataBaseInf.studentHashMap.values()) {
            result.put((Student)student, ((Student) student).getListOfMark());
        }
        return result;
    }

    @Override
    public List<Mark> getMarkListbyTheam(Theams theam, int studentId) {
        Student student = (Student) DataBaseInf.studentHashMap.get(studentId);
        return student.getListOfMark().get(theam);
    }

    @Override
    public HashMap<Integer, Mark> getMarkIDListbyTheam(Theams theam, int studentId) {
        HashMap<Integer, Mark> result = new HashMap<>();
        for (Mark mark:
             getMarkListbyTheam(theam, studentId)) {
            result.put(mark.getId(), mark);
        }
        return  result;
    }

    @Override
    public Theams theamById(Integer id) {
        return DataBaseInf.theamsHashMap.get(id);
    }

    @Override
    public Set<Theams> theamFromGroup(Integer groupId) {
        Group group = DataBaseInf.groupHashMap.get(groupId);
        return group.getTheamsSet();
    }

    @Override
    public HashMap<Integer, UserImpl> studentsFromGroup(int groupId) {
        Group group = DataBaseInf.groupHashMap.get(groupId);
        return (HashMap<Integer, UserImpl>) group.getStudentMap();
    }

    @Override
    public void addTheam(String theam) {
    Theams theams = new Theams()
            .withId(IdFactory.idBuilder())
            .withValue(theam);
    DataBaseInf.theamsHashMap.put(theams.getId(), theams);
    }

    @Override
    public void addGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {
    Group group = new Group();
    Set <Theams> theams =  new HashSet<>();
    HashMap <Integer, UserImpl> students = new HashMap<>();
        for (UserImpl user:
             studentList) {
            Student student = (Student)user;
            for (Integer theamId:
                 theamsIdList) {
                Theams tempTheam = theamById(theamId);
                student.addTheam(tempTheam);
            }
            students.put(student.getId(), student);
        }
        for (Integer theamId:
             theamsIdList) {
            theams.add(theamById(theamId));
        }
    group.withId(IdFactory.idBuilder())
            .withName("Groups_" + group.getId() )
            .withStudents(students)
            .withTrainer(RepositoryFactory.getRepository().getUserById(trainerId))
            .withTheam(theams);
        log.info("Group was create = {}", "Group name:" + group.getName() +
                " group student: " +group.getStudentMap().values().toString()  +" " + group.getTheamsSet().toString());
        DataBaseInf.groupHashMap.put(group.getId(), group);
    }

    @Override
    public HashMap<Integer, Theams> freeTheams() {
        HashMap <Integer, Theams> result;

        if (DataBaseInf.groupHashMap.values().isEmpty()) {
            return (HashMap<Integer, Theams>) DataBaseInf.theamsHashMap;
        }
            ArrayList<Theams> busyTheams = new ArrayList<>();
            for (Group group:
                 DataBaseInf.groupHashMap.values()) {
                busyTheams.addAll(group.getTheamsSet());
                          }
            log.info("after first for ");
            result = extractTheam(busyTheams);

        return result;
    }

    private HashMap<Integer, Theams> extractTheam(ArrayList<Theams> busyTheams) {
        HashMap <Integer, Theams> result =  new HashMap<>(DataBaseInf.theamsHashMap);
        log.info("In extract");
        for (Theams theams:
                DataBaseInf.theamsHashMap.values()) {
            log.info("Busy theamLog1 = {}", theams.getTheamName());
            for (Theams busyTheam:
                    busyTheams) {
                log.info("Busy theamLog2 = {}", busyTheam.getTheamName());
                if (theams.getId() == busyTheam.getId())
                    result.remove(theams.getId());
            }
        }
        return result;
    }

    @Override
    public void addSalaryToTrainer(int trainerId, int salaryValue) {
        Trainer trainer = (Trainer) DataBaseInf.trainerHashMap.get(trainerId);
        trainer.addSalary(new Salary().withValue(salaryValue));
    }

    @Override
    public void addMarkToStudent(int studentId, int theamID, int markValue) {
    Student student = (Student) DataBaseInf.studentHashMap.get(studentId);

    student.getListOfMark()
            .get(theamById(theamID))
            .add(new Mark()
            .withId(IdFactory.idBuilder())
            .withValue(markValue));
    }

    @Override
    public void deleteMarksById(int [] tempMarksId, int theamId) {
        for (UserImpl user:
             DataBaseInf.studentHashMap.values()) {
            Student student = (Student) user;
            for (int j : tempMarksId) {
              List <Mark> tempMarks = student.getListOfMark().get(theamById(theamId));
             Mark marksFordelete = tempMarks.stream().filter(mark -> mark.getId() == j).findAny().get();
            student.getListOfMark().values().remove(marksFordelete);
            }
        }
    }

    @Override
    public void changeMark(HashMap<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
    Student student = (Student) RepositoryFactory.getRepository().allStudent().get(studentId);
    for (Map.Entry <Integer, Integer> entry : markIdMarkValue.entrySet()) {
        Mark tempMark =   student.getListOfMark()
                .get(theamById(theamId))
                .stream().filter(mark -> mark.getId() == entry.getKey()).findAny().get();
        log.info("Old mark id and value = {}" , tempMark.getId() + " value: " + tempMark.getValuesOfMark());
        tempMark.setValuesOfMark(entry.getValue());
        log.info("New mark id and value = {}" , tempMark.getId() + " value: " + tempMark.getValuesOfMark());

    }
    }

    @Override
    public void updateGroup(int groupId, String act, int[] entytiIdforact) {
    Group group = DataBaseInf.groupHashMap.get(groupId);
        switch (act) {
            case "studentdelete": {
                for (int item : entytiIdforact) {
                    group.getStudentMap().remove(item);
                }
            }

                break;
            case "studentadd": {
                for (int value : entytiIdforact) {
                    Student student = (Student) RepositoryFactory.getRepository().allStudent().get(value);
                    group.addStudent(student);
                }
            }
                break;
            case "theamdelete": {
                for (int k : entytiIdforact) {
                    Theams theams = DataBaseInf.theamsHashMap.get(k);
                    group.getTheamsSet().remove(theams);
                }
            }
                break;
            case "theamadd":
                {
                for (int j : entytiIdforact) {
                    Theams theams = DataBaseInf.theamsHashMap.get(j);
                    group.getTheamsSet().add(theams);
                }
                }
                break;
            case "trainer": {
                group.withTrainer(DataBaseInf.trainerHashMap.get(entytiIdforact[0]));
            }

        }
    }

    @Override
    public void updateTheam(int theamId, String theamName) {
    DataBaseInf.theamsHashMap.get(theamId).withValue(theamName);
    }
}
