package controller.serviseforcontroller.restsservice;

import lombok.extern.slf4j.Slf4j;
import repository.RepositoryFactory;
import users.UserImpl;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ChangeUserToAgregateMap {

    private ChangeUserToAgregateMap() {
        //utils class
    }
    /**
     * This method given data for future users change
     **/
    public static Map<Integer, UserImpl> mapToChange(String user) {
        Map<Integer, UserImpl> result = new HashMap<>();
        switch (user) {
            case "student":
                result = RepositoryFactory.getRepository().allStudent();
                break;
            case "trainer":
                result = RepositoryFactory.getRepository().allTrainer();
                break;
            case "administrator":
                result = RepositoryFactory.getRepository().allAdmin();
                break;
        }
        return result;
    }
}
