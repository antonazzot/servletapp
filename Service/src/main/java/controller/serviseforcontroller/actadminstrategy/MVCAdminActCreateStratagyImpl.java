package controller.serviseforcontroller.actadminstrategy;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Theams;

@Slf4j
public class MVCAdminActCreateStratagyImpl implements MVCAdminActStratagy {
    @Override
    public String watchEntity(String entity, Model model, String deleteId) {
        if (!entity.equals("group") && !entity.equals("theam")) {
            model.addAttribute("role", entity);
//            model.addAttribute("userImpl", new UserImpl().withRole(RoleIDParametrCheker.getRoleByString(entity)));
            return "adminControl/adduserpage";
        } else if (!entity.equals("theam")) {
            log.info("Create group ={}", entity);
            model.addAttribute("mapIS", RepositoryFactory.getRepository().allStudent());
            model.addAttribute("mapITr", RepositoryFactory.getRepository().freeTrainer());
            model.addAttribute("mapITe", ThreadRepositoryFactory.getRepository().freeTheams());
//            model.addAttribute("group", new Group());
            return "adminControl/addgroup";
        } else {
            model.addAttribute("theam", new Theams());
            return "adminControl/theamadd";
        }
    }
}
