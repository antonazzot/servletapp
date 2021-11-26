package repository.threadmodelrep;

import repository.threadmodelrep.threadfunction.functionjpa.GroupFunctionJpa;
import repository.threadmodelrep.threadfunction.functionjpa.MarkFunctionJpa;
import repository.threadmodelrep.threadfunction.functionjpa.SalaryFunctionJpa;
import repository.threadmodelrep.threadfunction.functionjpa.TheamFunctionJpa;
import threadmodel.Group;
import threadmodel.Mark;
import threadmodel.Salary;
import threadmodel.Theams;
import users.Student;
import users.Trainer;
import users.UserImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ThreadRepositoryImplJpa implements ThreadRepository {

    private static volatile ThreadRepositoryImplJpa instance;

    private ThreadRepositoryImplJpa () {
        //singlton
    }

    public static ThreadRepositoryImplJpa getInstance() {
        if (instance == null) {
            synchronized (ThreadRepositoryImplJpa.class) {
                if (instance == null) {
                    instance = new ThreadRepositoryImplJpa();
                }
            }
        }
        return instance;
    }
    @Override
    public Map<Integer, Group> allGroup() {
        return GroupFunctionJpa.getAllGroup();
    }

    @Override
    public Map<Integer, Theams> allTheams() {
        return TheamFunctionJpa.getallTheams();
    }

    @Override
    public Map<Trainer, List<Salary>> trainerSalary() {
        return SalaryFunctionJpa.gettrainerSalary();
    }

    @Override
    public Map<UserImpl, Map<Theams, List<Mark>>> studentTheamMark(int StudentId) {
        return MarkFunctionJpa.getstudentTheamMark(StudentId);
    }

    @Override
    public List<Mark> getMarkListbyTheam(Theams theam, int studentId) {
        return MarkFunctionJpa.dogetMarkListbyTheam(theam, studentId);
    }

    @Override
    public Map<Integer, Mark> getMarkIDListbyTheam(Theams theam, int studentId) {
        return MarkFunctionJpa.dogetMarkIDListbyTheam(theam, studentId);
    }

    @Override
    public Theams theamById(Integer id) {
        return TheamFunctionJpa.gettheamById(id);
    }

    @Override
    public Set<Theams> theamFromGroup(Integer groupId) {
        return TheamFunctionJpa.gettheamFromGroup(groupId);
    }

    @Override
    public Map<Integer, Student> studentsFromGroup(int groupId) {
        return GroupFunctionJpa.getstudentsFromGroup(groupId);
    }

    @Override
    public void addTheam(String theam) {
        TheamFunctionJpa.doaddTheam(theam);
    }

    @Override
    public void addGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {
    GroupFunctionJpa.doaddGroup(studentList, theamsIdList, trainerId);
    }

    @Override
    public Map<Integer, Theams> freeTheams() {
        return TheamFunctionJpa.getfreeTheams();
    }

    @Override
    public void addSalaryToTrainer(int trainerId, int salaryValue) {
        SalaryFunctionJpa.doaddSalaryToTrainer(trainerId, salaryValue);
    }

    @Override
    public void addMarkToStudent(int studentId, int theamID, int markValue) {
    MarkFunctionJpa.doaddMarkToStudent(studentId, theamID, markValue);
    }

    @Override
    public void deleteMarksById(int[] tempMarksId, int theamId, int studentid) {
        MarkFunctionJpa.dodeleteMarksById(tempMarksId, theamId, studentid);
    }

    @Override
    public void changeMark(HashMap<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
    MarkFunctionJpa.dochangeMark(markIdMarkValue, studentId, theamId);
    }

    @Override
    public void updateGroup(int groupId, String act, int[] entytiIdforact) {
        GroupFunctionJpa.doupdateGroup(groupId, act, entytiIdforact);
    }

    @Override
    public void updateTheam(int theamId, String theamName) {
    TheamFunctionJpa.doupdateTheam(theamId, theamName);
    }
}
