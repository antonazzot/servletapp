package Action;

import DataBase.DataBaseInf;
import ThreadModel.Group;
import ThreadModel.Theams;

import java.util.*;

public class IndividSetMap {
    public static HashMap<Integer, Theams> theamsHashSet () {
        HashSet<Theams> result = new HashSet<>(Set.of(Theams.values()));
        HashMap <Integer, Theams> theamsHashMap = new HashMap<>();
        if (DataBaseInf.groupHashMap.isEmpty()) {
            for (Theams th: Theams.values()) {
                theamsHashMap.put(IdFactory.idBuilder(), th);
            }
            return theamsHashMap;
        } else {

            for (Group group:
                    DataBaseInf.groupHashMap.values()) {
                for (Theams theams :
                        group.getTheamsSet()) {
                    result.remove(theams);
                }

            }
            for (Theams th: result) {
                theamsHashMap.put(IdFactory.idBuilder(), th);
            }
            return theamsHashMap;
        }

    }
}
