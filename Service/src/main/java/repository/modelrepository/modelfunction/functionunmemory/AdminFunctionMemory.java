package repository.modelrepository.modelfunction.functionunmemory;

import database.DataBaseInf;
import users.UserImpl;

import java.util.HashMap;

public class AdminFunctionMemory {
    public static HashMap<Integer, UserImpl> getallAdmin () {
        return (HashMap<Integer, UserImpl>) DataBaseInf.getAdminHashMap();
    }
}
