package controller.serviseforcontroller.actadminstrategy;

import org.springframework.ui.Model;

public interface MVCAdminActStratagy {
    String watchEntity(String entity, Model model, String deleteId);
}
