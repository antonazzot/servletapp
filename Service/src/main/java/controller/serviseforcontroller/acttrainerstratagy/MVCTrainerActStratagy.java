package controller.serviseforcontroller.acttrainerstratagy;

import org.springframework.ui.Model;
import threadmodel.Group;

public interface MVCTrainerActStratagy {
    public String doAct (Group group, String studentId, String thId, String mark, Model model);
}
