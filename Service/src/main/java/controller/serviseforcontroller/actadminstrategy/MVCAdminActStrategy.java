package controller.serviseforcontroller.actadminstrategy;

import org.springframework.ui.Model;

public interface MVCAdminActStrategy {
    String prepareEntity(String entity, Model model);
}
