package controller.serviseforcontroller.actadminstrategy;


import org.springframework.ui.Model;
import repository.RepositoryFactory;

public class MVCAdminActDeleteStratagyImpl implements MVCAdminActStratagy {
    @Override
    public String watchEntity(String entity, Model model, String deleteId) {
        int entityId;
        try {
            entityId = Integer.parseInt(deleteId);
        } catch (IllegalArgumentException var5) {
            return "exception";
        }
        RepositoryFactory.getRepository().removeUser(entityId, entity);
        return "redirect:/mvc/hello";
    }
}