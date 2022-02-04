package controller.serviseforcontroller.acttrainerstratagy;

import controller.serviseforcontroller.viewsservises.ParserStringToInt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import repository.threadmodelrep.ThreadRepositoryFactory;

@Slf4j
public class MVCTrainerActCreateStratagyImpl implements MVCTrainerActStratagy {
    @Override
    public String doAct(String studentId, String thId, String mark, Model model, Integer groupId) {
        if (mark == null || mark.equals("") || studentId == null || studentId.equals("") || thId == null || thId.equals("")) {
            String massage = "field are not valid";
            model.addAttribute("massage", massage);
            return "exception";
        }
        int markInt;
        try {
            markInt = Integer.parseInt(mark);
            if (markInt < 0) throw new IllegalArgumentException("Mark < 0");
        } catch (IllegalArgumentException e) {
            String massage = "mark must be valid";
            model.addAttribute("massage", massage);
            return "exception";
        }
        ThreadRepositoryFactory.getRepository().addMarkToStudent(
                ParserStringToInt.simpleParserStringToInt(studentId),
                ParserStringToInt.simpleParserStringToInt(thId),
                markInt);

        model.addAttribute("groupT", ThreadRepositoryFactory.getRepository().allGroup().get(groupId));

        return "TrainerControlPage/trainerstartpage";
    }
}
