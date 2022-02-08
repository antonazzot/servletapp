package controller.serviseforcontroller.viewsservises;

import helperutils.myexceptionutils.AppValidException;
import repository.RepositoryFactory;
import repository.modelrepository.modelservices.comoonservice.RoleIDParametrCheker;
import users.Role;
import users.UserImpl;

public class ChangeUser {
    public static UserImpl userForChange(int id, String name, String login, String password, String age, String role) throws AppValidException {
        int tempAge=0;
        if (age!=null && !age.equals(""))
            tempAge = Integer.parseInt(age);

        if (tempAge<0) throw new AppValidException("Age lower then null");

        UserImpl user = RepositoryFactory.getRepository().getUserById(id);
        Role tempRole = RoleIDParametrCheker.getRoleByString(role);
        login = login.equals("") ? user.getLogin() : login;
        password = password.equals("") ? user.getPassword() : password;
        name = name.equals("") ? user.getName() : name;
        int ageInt = age.equals("") ? user.getAge() : tempAge;

        return user
                .withRole(tempRole)
                .withLogin(login)
                .withName(name)
                .withPassword(password)
                .withAge(ageInt);
    }
}