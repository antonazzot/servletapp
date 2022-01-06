package repository.threadmodelrep;


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

public interface ThreadRepository {
    Map<Integer, Group> allGroup();

    Map<Integer, Theams> allTheams();

    Map<Trainer, List<Salary>> trainerSalary();

    Map<UserImpl, Map<Theams, List<Mark>>> studentTheamMark(int StudentId);

    List<Mark> getMarkListbyTheam(Theams theam, int studentId);

    Map<Integer, Mark> getMarkIDListbyTheam(Theams theam, int studentId);

    Theams theamById(Integer id);

    Set<Theams> theamFromGroup(Integer groupId);

    Map<Integer, Student> studentsFromGroup(int groupId);

    void addTheam(String theam);

    void addGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId);

    Map<Integer, Theams> freeTheams();

    void addSalaryToTrainer(int trainerId, int salaryValue);

    void addMarkToStudent(int studentId, int theamID, int markValue);

    void deleteMarksById(int[] tempMarksId, int theamId, int studentid);

    void changeMark(Map<Integer, Integer> markIdMarkValue, int studentId, int theamId);

    void updateGroup(int groupId, String act, int[] entytiIdforact);

    void updateTheam(int theamId, String theamName);


}
