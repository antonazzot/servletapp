package controller.serviseforcontroller;

import repository.RepositoryFactory;
import users.Student;
import users.UserImpl;

import java.util.ArrayList;
import java.util.List;

public class FreeStudentExtract {
    public static List<UserImpl> freeStudent (List <Student> studentList) {
        List <UserImpl> result = new ArrayList<>(RepositoryFactory.getRepository().allStudent().values());
        result.removeAll(studentList);
        return result;
    }
 }
