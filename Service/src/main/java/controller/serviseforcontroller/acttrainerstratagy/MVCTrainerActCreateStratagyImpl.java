package controller.serviseforcontroller.acttrainerstratagy;

import controller.serviseforcontroller.viewsservises.ParserStringToInt;
import helperutils.myexceptionutils.AppValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import repository.threadmodelrep.ThreadRepositoryFactory;

@Slf4j
public class MVCTrainerActCreateStratagyImpl implements MVCTrainerActStratagy {
    @Override
    public String doAct(String studentId, String thId, String mark, Model model, Integer groupId) throws AppValidException {
        if (mark == null || mark.equals("") || studentId == null || studentId.equals("") || thId == null || thId.equals(""))
           throw new AppValidException("field are not valid");

        int markInt = Integer.parseInt(mark);

        if (markInt < 0)
            throw new AppValidException("mark must be valid, mark<0");

        ThreadRepositoryFactory.getRepository().addMarkToStudent(
                ParserStringToInt.simpleParserStringToInt(studentId),
                ParserStringToInt.simpleParserStringToInt(thId),
                markInt);

        model.addAttribute("groupT", ThreadRepositoryFactory.getRepository().allGroup().get(groupId));

        return "TrainerControlPage/trainerstartpage";
    }
}
