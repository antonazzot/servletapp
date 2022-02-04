package repository.threadmodelrep;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import repository.threadmodelrep.threadservices.postgresservices.GroupServicesPostgres;
import repository.threadmodelrep.threadservices.postgresservices.MarkServicesPostgres;
import repository.threadmodelrep.threadservices.postgresservices.SalaryServicesPostgres;
import repository.threadmodelrep.threadservices.postgresservices.TheamServicesPostgres;
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
@RequiredArgsConstructor
public class ThreadRepositoryImplPostgres implements ThreadRepository {
    private final GroupServicesPostgres groupServicesPostgres;
    private final MarkServicesPostgres markServicesPostgres;
    private final TheamServicesPostgres theamServicesPostgres;
    private final SalaryServicesPostgres salaryServicesPostgres;

    @Override
    public Map<Integer, Group> allGroup() {
        return groupServicesPostgres.getAllGroup();
    }

    @Override
    public Map<Integer, Theams> allTheams() {
        return theamServicesPostgres.getallTheams();
    }

    @Override
    public Map<Trainer, List<Salary>> trainerSalary() {
        return salaryServicesPostgres.gettrainerSalary();
    }

    @Override
    public Map<UserImpl, Map<Theams, List<Mark>>> studentTheamMark(int studentId) {
        return markServicesPostgres.getstudentTheamMark(studentId);
    }

    @Override
    public List<Mark> getMarkListbyTheam(Theams theam, int studentId) {
        return markServicesPostgres.dogetMarkListbyTheam(theam, studentId);
    }

    @Override
    public Map<Integer, Mark> getMarkIDListbyTheam(Theams theam, int studentId) {
        return markServicesPostgres.dogetMarkIDListbyTheam(theam, studentId);
    }

    @Override
    public Theams theamById(Integer id) {
        return theamServicesPostgres.gettheamById(id);
    }

    @Override
    public Set<Theams> theamFromGroup(Integer groupId) {
        return theamServicesPostgres.gettheamFromGroup(groupId);
    }

    @Override
    public Map<Integer, Student> studentsFromGroup(int groupId) {
        return groupServicesPostgres.getstudentsFromGroup(groupId);
    }

    @Override
    public void addTheam(String theam) {
        theamServicesPostgres.doaddTheam(theam);
    }

    @Override
    public void addGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {
        groupServicesPostgres.doaddGroup(studentList, theamsIdList, trainerId);
    }

    @Override
    public Map<Integer, Theams> freeTheams() {
        return theamServicesPostgres.getfreeTheams();
    }

    @Override
    public void addSalaryToTrainer(int trainerId, int salaryValue) {
        salaryServicesPostgres.doaddSalaryToTrainer(trainerId, salaryValue);
    }

    @Override
    public void addMarkToStudent(int studentId, int theamID, int markValue) {
        markServicesPostgres.doaddMarkToStudent(studentId, theamID, markValue);
    }

    @Override
    public void deleteMarksById(int[] tempMarksId, int theamId, int studentid) {
        markServicesPostgres.dodeleteMarksById(tempMarksId, theamId, studentid);
    }

    @Override
    public void changeMark(Map<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
        markServicesPostgres.dochangeMark(markIdMarkValue, studentId, theamId);
    }

    @Override
    public void updateGroup(int groupId, String act, int[] entytiIdforact) {
        groupServicesPostgres.doupdateGroup(groupId, act, entytiIdforact);
    }

    @Override
    public void updateTheam(int theamId, String theamName) {
        theamServicesPostgres.doupdateTheam(theamId, theamName);
    }
}

