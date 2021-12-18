package controller.serviseforcontroller.actadminstratagy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import users.UserImpl;

@Slf4j
public class MVCAdminActCreateStratagyImpl implements MVCAdminActStratagy {
    @Override
    public String watchEntity(String entity, Model model) {
        if (!entity.equals("group") && !entity.equals("theam")) {
            log.info("Create user ={}", entity);
            model.addAttribute("role", entity);
            model.addAttribute("userImpl", new UserImpl());
            log.info("Model attribute ={}", model.getAttribute("role")+model.getAttribute("userImpl").toString());
            return "adminviews/adduser";
        } else if (!entity.equals("theam")) {
            log.info("Create group ={}", entity);
//            model.setAttribute("mapIS", RepositoryFactory.getRepository().allStudent());
//            model.setAttribute("mapITr", RepositoryFactory.getRepository().freeTrainer());
//            model.setAttribute("mapITe", ThreadRepositoryFactory.getRepository().freeTheams());
            return "adminControl/theamscreatelist.jsp";
        } else {
            return "adminControl/theamadd.jsp";
        }
    }
}
