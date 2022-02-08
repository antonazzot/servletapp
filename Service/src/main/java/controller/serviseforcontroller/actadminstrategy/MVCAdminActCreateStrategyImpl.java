package controller.serviseforcontroller.actadminstrategy;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Theams;

@Slf4j
public class MVCAdminActCreateStrategyImpl implements MVCAdminActStrategy {
    @Override
    public String prepareEntity(String entity, Model model) {
        if (!entity.equals("group") && !entity.equals("theam")) {
            model.addAttribute("role", entity);
            return "adminControl/adduserpage";
        } else if (!entity.equals("theam")) {
            model.addAttribute("mapIS", RepositoryFactory.getRepository().allStudent());
            model.addAttribute("mapITr", RepositoryFactory.getRepository().freeTrainer());
            model.addAttribute("mapITe", ThreadRepositoryFactory.getRepository().freeTheams());
            return "adminControl/addgroup";
        } else {
            model.addAttribute("theam", new Theams());
            return "adminControl/theamadd";
        }
    }
}
