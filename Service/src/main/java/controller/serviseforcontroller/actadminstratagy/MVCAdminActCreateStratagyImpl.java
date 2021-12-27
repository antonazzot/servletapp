package controller.serviseforcontroller.actadminstratagy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import repository.RepositoryFactory;
import repository.modelrepository.modelfunction.RoleIDParametrCheker;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import threadmodel.Theams;
import users.UserImpl;

@Slf4j
public class MVCAdminActCreateStratagyImpl implements MVCAdminActStratagy {
    @Override
    public String watchEntity(String entity, Model model, String deleteId) {
        if (!entity.equals("group") && !entity.equals("theam")) {
            log.info("Create user ={}", entity);
//            model.addAttribute("role", entity);
            model.addAttribute("userImpl", new UserImpl().withRole(RoleIDParametrCheker.userGetRoleForString(entity)));
            log.info("Model attribute ={}", model.getAttribute("role")+model.getAttribute("userImpl").toString());
            return "adminviews/adduser";
        } else if (!entity.equals("theam")) {
            log.info("Create group ={}", entity);
            model.addAttribute("mapIS", RepositoryFactory.getRepository().allStudent());
            model.addAttribute("mapITr", RepositoryFactory.getRepository().freeTrainer());
            model.addAttribute("mapITe", ThreadRepositoryFactory.getRepository().freeTheams());
//            model.addAttribute("group", new Group());
            return "adminviews/addgroup";
        } else {
            model.addAttribute("theam", new Theams());
            return "adminviews/addtheam";
        }
    }
}
