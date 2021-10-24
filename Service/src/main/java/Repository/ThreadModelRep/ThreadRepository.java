package Repository.ThreadModelRep;



import ThreadModel.Group;
import ThreadModel.Mark;
import ThreadModel.Salary;
import ThreadModel.Theams;
import Users.Student;
import Users.Trainer;
import Users.UserImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

public interface ThreadRepository {
    HashMap <Integer, Group> allGroup ();
    HashMap <Integer, Theams> allTheams();
    HashMap <Trainer, ArrayList<Salary>> trainerSalary ();
    HashMap <Student,HashMap<Theams, ArrayList<Mark>>> studentTheamMark ();
    Theams theamById (Integer id);
    Set <Theams> theamFromGroup (Integer groupId);
}
