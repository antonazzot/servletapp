package controller.serviseforcontroller.actadminstratagy;

import org.springframework.ui.Model;
import repository.RepositoryFactory;

public class MVCAdminActDeleteStratagyImpl implements MVCAdminActStratagy {
    @Override
    public String watchEntity(String entity, Model model) {
        int entityId;
        try {
//            entityId = Integer.parseInt(model.getParameter("id"));
        } catch (IllegalArgumentException var5) {
            return "exeception.jsp";
        }

//        RepositoryFactory.getRepository().removeUser(entityId, entity);
        return "adminControl/adminActList.jsp";
    }
}

