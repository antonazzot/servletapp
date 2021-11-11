package repository.threadmodelrep;


import threadmodel.Group;
import threadmodel.Mark;
import threadmodel.Salary;
import threadmodel.Theams;
import users.Student;
import users.Trainer;
import users.UserImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface ThreadRepository {
    HashMap<Integer, Group> allGroup();

    HashMap<Integer, Theams> allTheams();

    HashMap<Trainer, List<Salary>> trainerSalary();

    HashMap<UserImpl, HashMap<Theams, List<Mark>>> studentTheamMark(int StudentId);

    List<Mark> getMarkListbyTheam(Theams theam, int studentId);

    HashMap<Integer, Mark> getMarkIDListbyTheam(Theams theam, int studentId);

    Theams theamById(Integer id);

    Set<Theams> theamFromGroup(Integer groupId);

    HashMap<Integer, Student> studentsFromGroup(int groupId);

    void addTheam(String theam);

    void addGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId);

    HashMap<Integer, Theams> freeTheams();

    void addSalaryToTrainer(int trainerId, int salaryValue);

    void addMarkToStudent(int studentId, int theamID, int markValue);

    void deleteMarksById(int[] tempMarksId, int theamId, int studentid);

    void changeMark(HashMap<Integer, Integer> markIdMarkValue, int studentId, int theamId);

    void updateGroup(int groupId, String act, int[] entytiIdforact);

    void updateTheam(int theamId, String theamName);


}
