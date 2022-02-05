package controller.serviseforcontroller.viewsservises;

import helperutils.myexceptionutils.AppValidException;
import repository.RepositoryFactory;
import users.UserImpl;

import java.util.ArrayList;
import java.util.List;

public class GroupAdderService {
    public static List<UserImpl> studentList(String[] studentMass) throws AppValidException {
        List<UserImpl> studentList = new ArrayList<>();
        try {
            for (String mass : studentMass) {
                studentList.add(RepositoryFactory.getRepository().getStudentById(Integer.parseInt(mass)));
            }
        }
        catch (IllegalArgumentException e) {
            throw new AppValidException("not parse string");
        }


        return studentList;
    }
}