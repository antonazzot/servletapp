package controller.serviseforcontroller.actadminstratagy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import repository.RepositoryFactory;
import users.UserImpl;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MVCAdminActChangeStratagyImpl implements MVCAdminActStratagy {
    @Override
    public String watchEntity(String entity, Model model, String deleteId) {
//
//        if (!entity.equals("group") && !entity.equals("theam")) {
//            log.info("Change entity ={}", entity);
//            model.setAttribute("map", this.mapToChange(entity));
//            return "adminControl/changeUser.jsp";
//        } else if (entity.equals("theam")) {
//            model.setAttribute("map", ThreadRepositoryFactory.getRepository().allTheams());
//            return "adminControl/changeTheam.jsp";
//        } else if (entity.equals("group")) {
//            model.setAttribute("map", ThreadRepositoryFactory.getRepository().allGroup());
//            return "adminControl/changeGroup.jsp";
//        } else {
//            return "exeception.jsp";
//        }
        return null;
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
