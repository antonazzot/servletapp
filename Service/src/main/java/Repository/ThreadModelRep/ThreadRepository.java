package Repository.ThreadModelRep;



import ThreadModel.Group;
import ThreadModel.Mark;
import ThreadModel.Salary;
import ThreadModel.Theams;
import Users.Student;
import Users.Trainer;
import Users.UserImpl;

import java.util.*;

public interface ThreadRepository {
    HashMap <Integer, Group> allGroup ();
    HashMap <Integer, Theams> allTheams();
    HashMap <Trainer, List<Salary>> trainerSalary ();
    HashMap <UserImpl,HashMap<Theams, List<Mark>>> studentTheamMark (int StudentId);
    Theams theamById (Integer id);
    Set <Theams> theamFromGroup (Integer groupId);
    HashMap <Integer, UserImpl> studentsFromGroup (int groupId);

}
