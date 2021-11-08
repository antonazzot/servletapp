package repository.threadmodelrep.threadfunction.functioninmemory;

import action.IdFactory;
import database.DataBaseInf;
import lombok.extern.slf4j.Slf4j;
import threadmodel.Group;
import threadmodel.Theams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Slf4j
public class TheamFunction {


    public static HashMap<Integer, Theams> getallTheams() {
        return (HashMap<Integer, Theams>) DataBaseInf.getTheamsHashMap();
    }

    public static Theams gettheamById(Integer id) {
        return DataBaseInf.getTheamsHashMap().get(id);
    }
    public static Set<Theams> gettheamFromGroup(Integer groupId)  {
        Group group = DataBaseInf.getGroupHashMap().get(groupId);
        return group.getTheamsSet();
    }
    public static void doaddTheam(String theam) {
        Theams theams = new Theams()
                .withId(IdFactory.idBuilder())
                .withValue(theam);
        DataBaseInf.getTheamsHashMap().put(theams.getId(), theams);
    }

    public static HashMap<Integer, Theams> getfreeTheams() {
        HashMap <Integer, Theams> result;
        if (DataBaseInf.getGroupHashMap().values().isEmpty()) {
            return (HashMap<Integer, Theams>) DataBaseInf.getTheamsHashMap();
        }
        ArrayList<Theams> busyTheams = new ArrayList<>();
        for (Group group:
                DataBaseInf.getGroupHashMap().values()) {
            busyTheams.addAll(group.getTheamsSet());
        }
        log.info("after first for ");
        result = extractTheam(busyTheams);
        return result;
    }

    private static HashMap<Integer, Theams> extractTheam(ArrayList<Theams> busyTheams) {
        HashMap <Integer, Theams> result =  new HashMap<>(DataBaseInf.getTheamsHashMap());
        log.info("In extract");
        for (Theams theams:
                DataBaseInf.getTheamsHashMap().values()) {
            log.info("Busy theamLog1 = {}", theams.getTheamName());
            for (Theams busyTheam:
                    busyTheams) {
                log.info("Busy theamLog2 = {}", busyTheam.getTheamName());
                if (theams.getId() == busyTheam.getId())
                    result.remove(theams.getId());
            }
        }
        return result;
    }

    public static void doupdateTheam (int theamId, String theamName) {
        DataBaseInf.getTheamsHashMap().get(theamId).withValue(theamName);
    }
}
