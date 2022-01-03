package controller.serviseforcontroller.acttrainerstrategy;


import org.springframework.ui.Model;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;

public class MVCAdminActDeleteStratagyImpl implements MVCAdminActStratagy {
    @Override
    public String watchEntity(String entity, Model model, String deleteId) {
        int entityId;
        try {
            entityId = Integer.parseInt(deleteId);
        } catch (IllegalArgumentException var5) {
            return "exeception";
        }
        RepositoryFactory.getRepository().removeUser(entityId, entity);
        return "redirect:/check";
    }
}