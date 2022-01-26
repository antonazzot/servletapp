package repository.threadmodelrep.threadservices.memoryservices;

import database.DataBaseInf;
import lombok.extern.slf4j.Slf4j;
import threadmodel.Salary;
import users.Trainer;
import users.UserImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SalaryFunction {

    public static Map<Trainer, List<Salary>> gettrainerSalary() {
        Map<Trainer, List<Salary>> result = new HashMap<>();
        for (UserImpl trainer :
                DataBaseInf.getTrainerHashMap().values()) {
            result.put((Trainer) trainer, ((Trainer) trainer).getSalarylist());
        }
        return result;
    }

    public static void doaddSalaryToTrainer(int trainerId, int salaryValue) {
        Trainer trainer = (Trainer) DataBaseInf.getTrainerHashMap().get(trainerId);
        trainer.addSalary(new Salary().withValue(salaryValue));
    }
}
