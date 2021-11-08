package repository.modelrepository;

import users.UserImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public interface UserRepository {
    HashMap<Integer, UserImpl> allUser ();
    HashMap<Integer, UserImpl> allTrainer ();
    HashMap<Integer, UserImpl> allStudent ();
    HashMap<Integer, UserImpl> allAdmin ();
    UserImpl getUserById (Integer id);
    int  saveUser (UserImpl user);
    Optional <UserImpl> removeUser (Integer id, String entity);
    UserImpl updateUser (UserImpl user);
    HashMap<Integer, UserImpl> freeTrainer();
    ArrayList <UserImpl> studentFromGroup (Integer groupId);

 }
