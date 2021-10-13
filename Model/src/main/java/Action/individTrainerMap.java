package Action;

import DataBase.DataBaseInf;
import ThreadModel.Group;
import Users.UserImpl;

import java.util.HashMap;

public class individTrainerMap {
    public static HashMap<UserImpl, String> theamsHashSet(HashMap<UserImpl, String> map) {

        HashMap<UserImpl, String> result = new HashMap<>();
        if (DataBaseInf.groupHashMap.isEmpty()) {
            return map;
        } else {

            for (Group group :
                    DataBaseInf.groupHashMap.values()) {
                map.remove(group.getTrainer());
            }


            return map;
        }

    }
}
