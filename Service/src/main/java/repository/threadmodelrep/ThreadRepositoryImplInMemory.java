package repository.threadmodelrep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.threadmodelrep.threadfunction.functioninmemory.GroupFunction;
import repository.threadmodelrep.threadfunction.functioninmemory.MarkFunction;
import repository.threadmodelrep.threadfunction.functioninmemory.SalaryFunction;
import repository.threadmodelrep.threadfunction.functioninmemory.TheamFunction;
import threadmodel.Group;
import threadmodel.Mark;
import threadmodel.Salary;
import threadmodel.Theams;
import users.Trainer;
import users.UserImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
        return GroupFunction.getAllGroup();
    }

    @Override
    public HashMap<Integer, Theams> allTheams() {
        return TheamFunction.getallTheams();
    }

    @Override
    public HashMap<Trainer, List<Salary>> trainerSalary() {
      return SalaryFunction.gettrainerSalary();
    }

    @Override
    public HashMap<UserImpl, HashMap<Theams, List<Mark>>> studentTheamMark(int StudentId) {
      return  MarkFunction.getstudentTheamMark(StudentId);
    }

    @Override
    public List<Mark> getMarkListbyTheam(Theams theam, int studentId) {
        return MarkFunction.dogetMarkListbyTheam(theam,studentId);
    }

    @Override
    public HashMap<Integer, Mark> getMarkIDListbyTheam(Theams theam, int studentId) {
        return MarkFunction.dogetMarkIDListbyTheam(theam,studentId);
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
    public HashMap<Integer, UserImpl> studentsFromGroup(int groupId) {
        return GroupFunction.getstudentsFromGroup(groupId);
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
    public HashMap<Integer, Theams> freeTheams() {
        return TheamFunction.getfreeTheams();
    }

    @Override
    public void addSalaryToTrainer(int trainerId, int salaryValue) {
       SalaryFunction.doaddSalaryToTrainer(trainerId, salaryValue);
    }

    @Override
    public void addMarkToStudent(int studentId, int theamID, int markValue) {
    MarkFunction.doaddMarkToStudent(studentId,theamID,markValue);
    }

    @Override
    public void deleteMarksById(int[] tempMarksId, int theamId, int studentid) {
     MarkFunction.dodeleteMarksById(tempMarksId, theamId, studentid);
    }

    @Override
    public void changeMark(HashMap<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
    MarkFunction.dochangeMark(markIdMarkValue,studentId,theamId);
    }

    @Override
    public void updateGroup (int groupId, String act, int[] entytiIdforact) {
    GroupFunction.doupdateGroup(groupId, act, entytiIdforact);
    }

    @Override
    public void updateTheam (int theamId, String theamName) {
    TheamFunction.doupdateTheam(theamId, theamName);
    }
}
