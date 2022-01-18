package controller.serviseforcontroller.acttrainerstratagy;

import controller.serviseforcontroller.viewsservises.ParserStringToInt;
import org.springframework.ui.Model;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;

public class MVCTrainerActCreateStratagyImpl implements MVCTrainerActStratagy {
    @Override
    public String doAct(Group group, String studentId, String thId, String mark, Model model) {
        if (mark == null || mark.equals("") || studentId == null || studentId.equals("") || thId == null || thId.equals("")) {
            return "exception";
        }
        int markInt;
        try {
             markInt = Integer.parseInt(mark);
        }
        catch (IllegalArgumentException e) {
            return "exception";
        }
                ThreadRepositoryFactory.getRepository().addMarkToStudent(
                ParserStringToInt.simpleParserStringToInt(studentId),
                ParserStringToInt.simpleParserStringToInt(thId),
                markInt
        );
        return "redirect: /web/mvc/hello";
    }
}
