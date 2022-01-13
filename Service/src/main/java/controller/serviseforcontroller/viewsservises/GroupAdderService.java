package controller.serviseforcontroller.viewsservises;

import repository.RepositoryFactory;
import users.UserImpl;

import java.util.ArrayList;
import java.util.List;

public class GroupAdderService {
    public static List<UserImpl> studentList(String[] studentMass) {
        List<UserImpl> studentList = new ArrayList<>();
        for (String mass : studentMass) {
            studentList.add(RepositoryFactory.getRepository().getStudentById(Integer.parseInt(mass)));
        }
        return studentList;
    }
}