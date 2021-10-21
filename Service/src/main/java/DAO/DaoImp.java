package DAO;

import DataBase.DataBaseInf;
import ThreadModel.Mark;
import ThreadModel.Theams;
import Users.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DaoImp extends Dao {
    @Override
    public  void createUser( Role role, String name, String login, String pass, int age) {


    }

    @Override
    public  UserImpl getUser(int id) {
        if ( getUsersMapById(id)!=null)
        return getUsersMapById(id).get(id);
        return null;
    }

    @Override
    public void updateUser(int id,  User user) {
    UserImpl old = getUser(id);
    UserImpl newuser = (UserImpl)user;
    old.setRole(newuser.getRole());
    old.setAge(newuser.getAge());
    old.setLogin(newuser.getLogin());
    old.setName(newuser.getName());
    old.setPassword(newuser.getPassword());
    }

    @Override
    public void deleteUser(int id) {
    if (getUsersMapById(id)!=null)
      getUsersMapById(id).remove(id);
    else
          if ( DataBaseInf.groupHashMap.values().stream().anyMatch(g->g.getId()==id))
              DataBaseInf.groupHashMap.remove(id);

    }

    public HashMap<Integer, UserImpl> getUsersMapById (int id) {

        if (DataBaseInf.adminHashMap.containsKey(id)) {
          return (HashMap<Integer, UserImpl>)  DataBaseInf.adminHashMap;
        }
        else  if (DataBaseInf.trainerHashMap.containsKey(id)) {
            return (HashMap<Integer, UserImpl>)  DataBaseInf.trainerHashMap;
        }
        else  if (DataBaseInf.studentHashMap.containsKey(id)) {
            return (HashMap<Integer, UserImpl>)  DataBaseInf.studentHashMap;
        }
        else return null;
    }
}
