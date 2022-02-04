package repository.threadmodelrep;

import org.springframework.stereotype.Repository;
import repository.threadmodelrep.threadservices.memoryservices.GroupService;
import repository.threadmodelrep.threadservices.memoryservices.MarkService;
import repository.threadmodelrep.threadservices.memoryservices.SalaryService;
import repository.threadmodelrep.threadservices.memoryservices.TheamService;
import threadmodel.Group;
import threadmodel.Mark;
import threadmodel.Salary;
import threadmodel.Theams;
import users.Student;
import users.Trainer;
import users.UserImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class ThreadRepositoryImplInMemory implements ThreadRepository {

    private ThreadRepositoryImplInMemory() {
    }

    @Override
    public Map<Integer, Group> allGroup() {
        return GroupService.getAllGroup();
    }

    @Override
    public Map<Integer, Theams> allTheams() {
        return TheamService.getallTheams();
    }

    @Override
    public Map<Trainer, List<Salary>> trainerSalary() {
        return SalaryService.gettrainerSalary();
    }

    @Override
    public Map<UserImpl, Map<Theams, List<Mark>>> studentTheamMark(int StudentId) {
        return MarkService.getstudentTheamMark(StudentId);
    }

    @Override
    public List<Mark> getMarkListbyTheam(Theams theam, int studentId) {
        return MarkService.dogetMarkListbyTheam(theam, studentId);
    }

    @Override
    public Map<Integer, Mark> getMarkIDListbyTheam(Theams theam, int studentId) {
        return MarkService.dogetMarkIDListbyTheam(theam, studentId);
    }

    @Override
    public Theams theamById(Integer id) {
        return TheamService.gettheamById(id);
    }

    @Override
    public Set<Theams> theamFromGroup(Integer groupId) {
        return TheamService.gettheamFromGroup(groupId);
    }

    @Override
    public Map<Integer, Student> studentsFromGroup(int groupId) {
        return GroupService.getstudentsFromGroup(groupId);
    }

    @Override
    public void addTheam(String theam) {
        TheamService.doaddTheam(theam);
    }

    @Override
    public void addGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {
        GroupService.doaddGroup(studentList, theamsIdList, trainerId);
    }

    @Override
    public Map<Integer, Theams> freeTheams() {
        return TheamService.getfreeTheams();
    }

    @Override
    public void addSalaryToTrainer(int trainerId, int salaryValue) {
        SalaryService.doaddSalaryToTrainer(trainerId, salaryValue);
    }

    @Override
    public void addMarkToStudent(int studentId, int theamID, int markValue) {
        MarkService.doaddMarkToStudent(studentId, theamID, markValue);
    }

    @Override
    public void deleteMarksById(int[] tempMarksId, int theamId, int studentid) {
        MarkService.dodeleteMarksById(tempMarksId, theamId, studentid);
    }

    @Override
    public void changeMark(Map<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
        MarkService.dochangeMark(markIdMarkValue, studentId, theamId);
    }

    @Override
    public void updateGroup(int groupId, String act, int[] entytiIdforact) {
        GroupService.doupdateGroup(groupId, act, entytiIdforact);
    }

    @Override
    public void updateTheam(int theamId, String theamName) {
        TheamService.doupdateTheam(theamId, theamName);
    }
}
