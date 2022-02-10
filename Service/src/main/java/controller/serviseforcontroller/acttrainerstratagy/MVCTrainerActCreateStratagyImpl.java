package controller.serviseforcontroller.acttrainerstratagy;

import controller.serviseforcontroller.senderservice.SenderService;
import controller.serviseforcontroller.viewsservises.ParserStringToInt;
import helperutils.myexceptionutils.AppValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
@RequiredArgsConstructor
@Slf4j
public class MVCTrainerActCreateStratagyImpl implements MVCTrainerActStratagy {
    @Override
    public String doAct(String studentId, String thId, String mark, Model model, Integer groupId, SenderService senderService) throws AppValidException {
        if (mark == null || mark.equals("") || studentId == null || studentId.equals("") || thId == null || thId.equals(""))
           throw new AppValidException("field are not valid");

        int markInt = ParserStringToInt.simpleParserStringToInt(mark);


        if (markInt < 0 || markInt>100)
            throw new AppValidException("mark must be upper then 0 and lower 100");
        int studentIntId = ParserStringToInt.simpleParserStringToInt(studentId);
        int thIntId = ParserStringToInt.simpleParserStringToInt(thId);
        String theamName = ThreadRepositoryFactory.getRepository().theamById(thIntId).getTheamName();

        ThreadRepositoryFactory.getRepository().addMarkToStudent(studentIntId, thIntId, markInt);

        String MESSAGE_MARK_ADD = "You have new mark.";
        senderService.sendMail(RepositoryFactory.getRepository().getStudentById(studentIntId).getEmail(), MESSAGE_MARK_ADD, "Theam: " + theamName + " Mark value: " + markInt + "." );

        model.addAttribute("groupT", ThreadRepositoryFactory.getRepository().allGroup().get(groupId));

        return "TrainerControlPage/trainerstartpage";
    }
}
