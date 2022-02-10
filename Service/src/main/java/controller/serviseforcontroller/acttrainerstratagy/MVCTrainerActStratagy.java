package controller.serviseforcontroller.acttrainerstratagy;

import controller.serviseforcontroller.senderservice.SenderService;
import org.springframework.ui.Model;

public interface MVCTrainerActStratagy {
    public String doAct(String studentId, String thId, String mark, Model model, Integer groupId, SenderService senderService);
}
