package repository.modelrepository.modelfunction.functionunmemory;

import database.DataBaseInf;
import threadmodel.Group;
import users.Student;
import users.UserImpl;

import java.util.List;
import java.util.Map;

public class StudentFunctionMemory {
    public static Map<Integer, UserImpl> getallStudent() {
        return  DataBaseInf.getStudentHashMap();
    }

    public static List<Student> studentFromGroup(Integer groupId) {
        Group group = DataBaseInf.getGroupHashMap().get(groupId);
        return (List<Student>) group.getStudentMap().values();
    }

}
