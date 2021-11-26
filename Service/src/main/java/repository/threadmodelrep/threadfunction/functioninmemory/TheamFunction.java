package repository.threadmodelrep.threadfunction.functioninmemory;

import action.IdFactory;
import database.DataBaseInf;
import lombok.extern.slf4j.Slf4j;
import threadmodel.Group;
import threadmodel.Theams;

import java.util.*;

@Slf4j
public class TheamFunction {


    public static Map<Integer, Theams> getallTheams() {
        return  DataBaseInf.getTheamsHashMap();
    }

    public static Theams gettheamById(Integer id) {
        return DataBaseInf.getTheamsHashMap().get(id);
    }

    public static Set<Theams> gettheamFromGroup(Integer groupId) {
        Group group = DataBaseInf.getGroupHashMap().get(groupId);
        return group.getTheamsSet();
    }

    public static void doaddTheam(String theam) {
        Theams theams = new Theams()
                .withId(IdFactory.idBuilder())
                .withValue(theam);
        DataBaseInf.getTheamsHashMap().put(theams.getId(), theams);
    }

    public static Map<Integer, Theams> getfreeTheams() {
        Map<Integer, Theams> result;
        if (DataBaseInf.getGroupHashMap().values().isEmpty()) {
            return DataBaseInf.getTheamsHashMap();
        }
        List<Theams> busyTheams = new ArrayList<>();
        for (Group group :
                DataBaseInf.getGroupHashMap().values()) {
            busyTheams.addAll(group.getTheamsSet());
        }
        log.info("after first for ");
        result = extractTheam(busyTheams);
        return result;
    }

    private static Map<Integer, Theams> extractTheam(List<Theams> busyTheams) {
        Map<Integer, Theams> result = new HashMap<>(DataBaseInf.getTheamsHashMap());
        log.info("In extract");
        for (Theams theams :
                DataBaseInf.getTheamsHashMap().values()) {
            log.info("Busy theamLog1 = {}", theams.getTheamName());
            for (Theams busyTheam :
                    busyTheams) {
                log.info("Busy theamLog2 = {}", busyTheam.getTheamName());
                if (theams.getId() == busyTheam.getId())
                    result.remove(theams.getId());
            }
        }
        return result;
    }

    public static void doupdateTheam(int theamId, String theamName) {
        DataBaseInf.getTheamsHashMap().get(theamId).withValue(theamName);
    }
}
