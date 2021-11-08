package repository.modelrepository.modelfunction.functionunmemory;

import database.DataBaseInf;
import threadmodel.Group;
import users.UserImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class TrainerFunctionMemory {

    public static HashMap<Integer, UserImpl> getallTrainer() {
        return (HashMap<Integer, UserImpl>) DataBaseInf.getTrainerHashMap();
    }

    public static HashMap<Integer, UserImpl> getfreeTrainer() {
        HashMap <Integer, UserImpl> result =  new HashMap<>(DataBaseInf.getTrainerHashMap());
        if (DataBaseInf.getGroupHashMap().values().isEmpty()) {
            return (HashMap<Integer, UserImpl>) DataBaseInf.getTrainerHashMap();
        }
        else  {

            ArrayList<UserImpl> busyTrainers = (ArrayList<UserImpl>) DataBaseInf.getGroupHashMap().values().stream()
                    .map(Group::getTrainer).collect(Collectors.toList());
            for (UserImpl trainer:
                    DataBaseInf.getTrainerHashMap().values()) {
                for (UserImpl busyTrainer:
                        busyTrainers ) {
                    if (trainer.getId() == busyTrainer.getId())
                        result.remove(trainer.getId());
                }
            }
        }
        return  result;
    }
}
