package controller.serviseforcontroller.actadminstrategy;


import controller.serviseforcontroller.restsservice.ChangeUserToAgregateMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import repository.threadmodelrep.ThreadRepositoryFactory;

@Slf4j
public class MVCAdminActWatchStratagyImpl implements MVCAdminActStratagy {
    @Override
    public String watchEntity(String entity, Model model, String deleteId) {
        log.info("Watch group ={}", entity);
        if (!entity.equals("group") && !entity.equals("theam")) {
            model.addAttribute("map", ChangeUserToAgregateMap.mapToChange(entity));
            return "demonstrate";
        } else if (!entity.equals("theam")) {
            model.addAttribute("map", ThreadRepositoryFactory.getRepository().allGroup());
            return "adminControl/demonstrategroup";
        } else {
            model.addAttribute("map", ThreadRepositoryFactory.getRepository().allTheams());
            return "adminControl/theamdemonstrate";
        }
    }
}