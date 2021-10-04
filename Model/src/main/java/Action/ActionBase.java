package Action;

import DataBase.DataBaseInf;
import Users.Role;
import Users.UserImpl;

import java.util.ArrayList;

public class ActionBase {
    public static ArrayList <UserImpl> getUserList (Role role) {
        ArrayList <UserImpl> users = null;
        if (Role.ADMINISTRATOR.equals(role)) {
         users = (ArrayList<UserImpl>) DataBaseInf.adminHashMap.values();
        }
        else
            if (Role.STUDENT.equals(role)){
                users = (ArrayList<UserImpl>) DataBaseInf.studentHashMap.values();
            }
            else
            if (Role.TRAINER.equals(role)) {
                users = (ArrayList<UserImpl>) DataBaseInf.trainerHashMap.values();
            }
           return users;
    }

}
