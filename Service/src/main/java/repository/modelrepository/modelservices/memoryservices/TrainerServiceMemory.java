package repository.modelrepository.modelservices.memoryservices;

import database.DataBaseInf;
import threadmodel.Group;
import users.Trainer;
import users.UserImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TrainerServiceMemory {

    public static Map<Integer, UserImpl> getallTrainer() {
        return  DataBaseInf.getTrainerHashMap();
    }

    public static Map<Integer, UserImpl> getfreeTrainer() {
        Map<Integer, UserImpl> result = new HashMap<>(DataBaseInf.getTrainerHashMap());
        if (DataBaseInf.getGroupHashMap().values().isEmpty()) {
            return DataBaseInf.getTrainerHashMap();
        } else {

            List<Trainer> busyTrainers = DataBaseInf.getGroupHashMap().values().stream()
                    .map(Group::getTrainer).collect(Collectors.toList());
            for (UserImpl trainer :
                    DataBaseInf.getTrainerHashMap().values()) {
                for (UserImpl busyTrainer :
                        busyTrainers) {
                    if (trainer.getId() == busyTrainer.getId())
                        result.remove(trainer.getId());
                }
            }
        }
        return result;
    }
}
