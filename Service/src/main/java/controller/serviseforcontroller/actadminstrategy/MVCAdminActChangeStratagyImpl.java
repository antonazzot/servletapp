package controller.serviseforcontroller.actadminstrategy;

import controller.serviseforcontroller.restsservice.ChangeUserToAgregateMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import repository.threadmodelrep.ThreadRepositoryFactory;

@Slf4j
public class MVCAdminActChangeStratagyImpl implements MVCAdminActStratagy {
    @Override
    public String watchEntity(String entity, Model model, String deleteId) {
        log.info("Entity for change ={}", entity);

        if (!entity.equals("group") && !entity.equals("theam")) {
            model.addAttribute("map", ChangeUserToAgregateMap.mapToChange(entity));
            return "adminControl/changeUser";
        } else if (entity.equals("theam")) {
            model.addAttribute("map", ThreadRepositoryFactory.getRepository().allTheams());
            return "adminControl/changetheam";
        } else {
            model.addAttribute("map", ThreadRepositoryFactory.getRepository().allGroup());
            return "adminControl/changeGroup";
        }
    }
}
