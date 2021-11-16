package repository.modelrepository.modelfunction.functionunmemory;

import database.DataBaseInf;
import threadmodel.Group;
import users.Student;
import users.UserImpl;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentFunctionMemory {
    public static HashMap<Integer, UserImpl> getallStudent() {
        return (HashMap<Integer, UserImpl>) DataBaseInf.getStudentHashMap();
    }

    public static ArrayList<Student> studentFromGroup(Integer groupId) {
        Group group = DataBaseInf.getGroupHashMap().get(groupId);
        return new ArrayList<>(group.getStudentMap().values());
    }

}
