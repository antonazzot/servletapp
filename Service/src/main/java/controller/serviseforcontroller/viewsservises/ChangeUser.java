package controller.serviseforcontroller.viewsservises;

import helperutils.myexceptionutils.AppValidException;
import repository.RepositoryFactory;
import users.UserImpl;

public class ChangeUser {
    public static UserImpl userForChange(int id, String name, String login, String password, String age) throws AppValidException {
        int tempAge = Integer.parseInt(age);

        if (tempAge<0) throw new AppValidException("Age lowwer then null");

        UserImpl user = RepositoryFactory.getRepository().getUserById(id);
        login = login.equals("") ? user.getLogin() : login;
        password = password.equals("") ? user.getPassword() : password;
        name = name.equals("") ? user.getName() : name;
        int ageInt = age.equals("") ? user.getAge() : tempAge;

        return user
                .withLogin(login)
                .withName(name)
                .withPassword(password)
                .withAge(ageInt);
    }
}