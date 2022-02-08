package controller.serviseforcontroller.viewsservises;

import repository.RepositoryFactory;
import users.Role;
import users.Student;
import users.TempStudent;

public class ActWithTempUser {
    public static void doAct(String act, Integer[] id) {

        if(act.equals("add")) {
            for (Integer integer : id) {
                TempStudent tempUserById = RepositoryFactory.getRepository().getTempUserById(integer);
                Student student = (Student) new Student()
                        .withRole(Role.STUDENT)
                        .withName(tempUserById.getName())
                        .withLogin(tempUserById.getLogin())
                        .withPassword(tempUserById.getPassword())
                        .withAge(tempUserById.getAge());
                RepositoryFactory.getRepository().saveUser(student);
                RepositoryFactory.getRepository().removeTempStudent(integer);
            }

        }
        else
        {
            for (Integer integer : id) {
                RepositoryFactory.getRepository().removeTempStudent(integer);
            }

        }
    }
}
