package controller.serviseforcontroller.acttrainerstrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import users.UserImpl;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MVCAdminActChangeStratagyImpl implements MVCAdminActStratagy {
    @Override
    public String watchEntity(String entity, Model model, String deleteId) {

        if (!entity.equals("group") && !entity.equals("theam")) {
            log.info("Change entity ={}", entity);
            model.addAttribute("map", this.mapToChange(entity));
            return "adminControl/changeUser";
        } else if (entity.equals("theam")) {
            model.addAttribute("map", ThreadRepositoryFactory.getRepository().allTheams());
            return "adminControl/changetheam";
        } else if (entity.equals("group")) {
            model.addAttribute("map", ThreadRepositoryFactory.getRepository().allGroup());
            return "adminControl/changeGroup";
        } else {
            return "exeception.jsp";
        }

    }
    /**
     * This method given data for future users change
     **/
    private Map<Integer, UserImpl> mapToChange(String user) {
        Map<Integer, UserImpl> result = new HashMap<>();
        switch (user) {
            case "student":
                result = RepositoryFactory.getRepository().allStudent();
                break;
            case "trainer":
                log.info("Watch student ={}", user);
                result = RepositoryFactory.getRepository().allTrainer();
                break;
            case "administrator":
                result = RepositoryFactory.getRepository().allAdmin();
                break;
        }
        return result;
    }
}
