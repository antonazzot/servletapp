package repository.threadmodelrep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.threadmodelrep.threadfunction.functioninpostgres.GroupFunction;
import repository.threadmodelrep.threadfunction.functioninpostgres.MarkFunction;
import repository.threadmodelrep.threadfunction.functioninpostgres.SalaryFunction;
import repository.threadmodelrep.threadfunction.functioninpostgres.TheamFunction;
import threadmodel.Group;
import threadmodel.Mark;
import threadmodel.Salary;
import threadmodel.Theams;
import users.Student;
import users.Trainer;
import users.UserImpl;

import java.util.*;

public class ThreadRepositoryImplPostgres implements ThreadRepository {

    private static final Logger log = LoggerFactory.getLogger(ThreadRepositoryImplPostgres.class);
    private static volatile ThreadRepositoryImplPostgres instance;

    private ThreadRepositoryImplPostgres() {
        //singlton
    }

    public static ThreadRepositoryImplPostgres getInstance() {
        if (instance == null) {
            synchronized (ThreadRepositoryImplPostgres.class) {
                if (instance == null) {
                    instance = new ThreadRepositoryImplPostgres();
                }
            }
        }
        return instance;
    }

    @Override
    public Map<Integer, Group> allGroup() {
        return GroupFunction.getAllGroup();
    }

    @Override
    public Map<Integer, Theams> allTheams() {
        return TheamFunction.getallTheams();
    }

    @Override
    public Map<Trainer, List<Salary>> trainerSalary() {
        return SalaryFunction.gettrainerSalary();
    }

    @Override
    public Map<UserImpl, Map<Theams, List<Mark>>> studentTheamMark(int studentId) {
        return MarkFunction.getstudentTheamMark(studentId);
    }

    @Override
    public List<Mark> getMarkListbyTheam(Theams theam, int studentId) {
        return MarkFunction.dogetMarkListbyTheam(theam, studentId);
    }

    @Override
    public Map<Integer, Mark> getMarkIDListbyTheam(Theams theam, int studentId) {
        return MarkFunction.dogetMarkIDListbyTheam(theam, studentId);
    }

    @Override
    public Theams theamById(Integer id) {
        return TheamFunction.gettheamById(id);
    }

    @Override
    public Set<Theams> theamFromGroup(Integer groupId) {
        return TheamFunction.gettheamFromGroup(groupId);
    }

    @Override
    public Map<Integer, Student> studentsFromGroup(int groupId) {
        return  GroupFunction.getstudentsFromGroup(groupId);
    }

    @Override
    public void addTheam(String theam) {
        TheamFunction.doaddTheam(theam);
    }

    @Override
    public void addGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {
        GroupFunction.doaddGroup(studentList, theamsIdList, trainerId);
    }

    @Override
    public Map<Integer, Theams> freeTheams() {
        return TheamFunction.getfreeTheams();
    }

    @Override
    public void addSalaryToTrainer(int trainerId, int salaryValue) {
        SalaryFunction.doaddSalaryToTrainer(trainerId, salaryValue);
    }

    @Override
    public void addMarkToStudent(int studentId, int theamID, int markValue) {
        MarkFunction.doaddMarkToStudent(studentId, theamID, markValue);
    }

    @Override
    public void deleteMarksById(int[] tempMarksId, int theamId, int studentid) {
        MarkFunction.dodeleteMarksById(tempMarksId, theamId, studentid);
    }

    @Override
    public void changeMark(HashMap<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
        MarkFunction.dochangeMark(markIdMarkValue, studentId, theamId);
    }

    @Override
    public void updateGroup(int groupId, String act, int[] entytiIdforact) {
        log.info("In repository updateGroup = {}", groupId + " " + "  " + act + " " + Arrays.toString(entytiIdforact));
        GroupFunction.doupdateGroup(groupId, act, entytiIdforact);
    }

    @Override
    public void updateTheam(int theamId, String theamName) {
        TheamFunction.doupdateTheam(theamId, theamName);
    }
}

