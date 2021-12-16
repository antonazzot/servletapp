package repository.modelrepository.modelfunction.functionunmemory;

import database.DataBaseInf;
import users.UserImpl;

import java.util.Map;

public class AdminFunctionMemory {
    public static Map<Integer, UserImpl> getallAdmin() {
        return DataBaseInf.getAdminHashMap();
    }
}
