package repository.threadmodelrep;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.threadmodelrep.threadfunction.jpaservices.GroupServiceJpa;
import repository.threadmodelrep.threadfunction.jpaservices.MarkServiceJpa;
import repository.threadmodelrep.threadfunction.jpaservices.SalaryServiceJpa;
import repository.threadmodelrep.threadfunction.jpaservices.TheamServiceJpa;
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
public class ThreadRepositoryImplJpa implements ThreadRepository {
    @Autowired
    private final GroupServiceJpa groupServiceJpa;
    @Autowired
    private final MarkServiceJpa markServiceJpa;
    @Autowired
    private final  SalaryServiceJpa salaryServiceJpa;
    @Autowired
    private final TheamServiceJpa theamServiceJpa;

    @Override
    public Map<Integer, Group> allGroup() {
        return groupServiceJpa.getAllGroup();
    }

    @Override
    public Map<Integer, Theams> allTheams() {
        return theamServiceJpa.getallTheams();
    }

    @Override
    public Map<Trainer, List<Salary>> trainerSalary() {
        return salaryServiceJpa.gettrainerSalary();
    }

    @Override
    public Map<UserImpl, Map<Theams, List<Mark>>> studentTheamMark(int StudentId) {
        return markServiceJpa.getstudentTheamMark(StudentId);
    }

    @Override
    public List<Mark> getMarkListbyTheam(Theams theam, int studentId) {
        return markServiceJpa.dogetMarkListbyTheam(theam, studentId);
    }

    @Override
    public Map<Integer, Mark> getMarkIDListbyTheam(Theams theam, int studentId) {
        return markServiceJpa.dogetMarkIDListbyTheam(theam, studentId);
    }

    @Override
    public Theams theamById(Integer id) {
        return theamServiceJpa.gettheamById(id);
    }

    @Override
    public Set<Theams> theamFromGroup(Integer groupId) {
        return theamServiceJpa.gettheamFromGroup(groupId);
    }

    @Override
    public Map<Integer, Student> studentsFromGroup(int groupId) {
        return groupServiceJpa.getstudentsFromGroup(groupId);
    }

    @Override
    public void addTheam(String theam) {
        theamServiceJpa.doaddTheam(theam);
    }

    @Override
    public void addGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {
    groupServiceJpa.doaddGroup(studentList, theamsIdList, trainerId);
    }

    @Override
    public Map<Integer, Theams> freeTheams() {
        return theamServiceJpa.getfreeTheams();
    }

    @Override
    public void addSalaryToTrainer(int trainerId, int salaryValue) {
        salaryServiceJpa.doaddSalaryToTrainer(trainerId, salaryValue);
    }

    @Override
    public void addMarkToStudent(int studentId, int theamID, int markValue) {
    markServiceJpa.doaddMarkToStudent(studentId, theamID, markValue);
    }

    @Override
    public void deleteMarksById(int[] tempMarksId, int theamId, int studentid) {
        markServiceJpa.dodeleteMarksById(tempMarksId, theamId, studentid);
    }

    @Override
    public void changeMark(Map<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
    markServiceJpa.dochangeMark(markIdMarkValue, studentId, theamId);
    }

    @Override
    public void updateGroup(int groupId, String act, int[] entytiIdforact) {
        groupServiceJpa.doupdateGroup(groupId, act, entytiIdforact);
    }

    @Override
    public void updateTheam(int theamId, String theamName) {
    theamServiceJpa.doupdateTheam(theamId, theamName);
    }
}
