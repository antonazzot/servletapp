package repository.modelrepository.modelfunction.memoryservices;

import database.DataBaseInf;
import users.UserImpl;

import java.util.Map;

public class AdminServiceMemory {
    public static Map<Integer, UserImpl> getallAdmin() {
        return DataBaseInf.getAdminHashMap();
    }
}
