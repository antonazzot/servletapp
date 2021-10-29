package Repository.ThreadModelRep;

import DataBase.DataBaseInf;
import Repository.RepositoryDatasourse;
import ThreadModel.Group;
import ThreadModel.Mark;
import ThreadModel.Salary;
import ThreadModel.Theams;
import Users.Student;
import Users.Trainer;
import Users.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ThreadRepositoryImplInMemory implements ThreadRepository {

    RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();
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
}
