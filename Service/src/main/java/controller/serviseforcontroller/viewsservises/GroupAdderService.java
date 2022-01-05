package controller.serviseforcontroller.viewsservises;

import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Theams;
import users.UserImpl;

import java.util.ArrayList;
import java.util.List;

public class GroupAdderService {
    public static List<UserImpl> studentList (String [] studentMass) {
        List<UserImpl> studentList = new ArrayList<>();
        for (int i = 0; i < studentMass.length; i++) {
            studentList.add(RepositoryFactory.getRepository().getStudentById(Integer.parseInt(studentMass[i])));
        }
        return studentList;
    }

    public static  List <Theams> theamsList (Integer [] theamId) {
        List<Theams> theamsList = new ArrayList<>();
        for (int i = 0; i < theamId.length; i++) {
            theamsList.add(ThreadRepositoryFactory.getRepository().theamById(theamId[i]));
        }
        return theamsList;
    }
}