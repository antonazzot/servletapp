package controller.serviseforcontroller.actadminstrategy;


import controller.serviseforcontroller.restsservice.ChangeUserToAgregateMap;
import org.springframework.ui.Model;
import repository.threadmodelrep.ThreadRepositoryFactory;

public class MVCAdminActDeleteStrategyImpl implements MVCAdminActStrategy {
    @Override
    public String prepareEntity(String entity, Model model) {

        if (!entity.equals("group") && !entity.equals("theam")) {
            model.addAttribute("map", ChangeUserToAgregateMap.mapToChange(entity));
            model.addAttribute("entity", entity);
            return "adminControl/userdeleteP";
        } else if (!entity.equals("theam")) {
            model.addAttribute("map", ThreadRepositoryFactory.getRepository().allGroup());
            model.addAttribute("entity", entity);
            return "adminControl/deletegrouppage";
        } else {
            model.addAttribute("map", ThreadRepositoryFactory.getRepository().allTheams());
            model.addAttribute("entity", entity);
            return "adminControl/theamdeletepage";
        }

    }
}