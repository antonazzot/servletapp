package controller.serviseforcontroller.acttrainerstratagy;

import controller.serviseforcontroller.senderservice.SenderService;
import controller.serviseforcontroller.viewsservises.ParserStringToInt;
import helperutils.myexceptionutils.AppValidException;
import org.springframework.ui.Model;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Theams;
import users.Student;

public class MVCTrainerActDeleteStratagyImpl implements MVCTrainerActStratagy {
    @Override
    public String doAct(String studentId, String thId, String mark, Model model, Integer groupId, SenderService senderService) throws AppValidException {
        if (studentId == null || studentId.equals("") || thId == null || thId.equals(""))
            throw  new AppValidException("field are not valid");

        Theams theams = ThreadRepositoryFactory.getRepository().theamById(ParserStringToInt.simpleParserStringToInt(thId));
        Student student = RepositoryFactory.getRepository().getStudentById(ParserStringToInt.simpleParserStringToInt(studentId));
        model.addAttribute("th", theams);
        model.addAttribute("student", student);
        model.addAttribute("mapOfMark", ThreadRepositoryFactory.getRepository().getMarkIDListbyTheam(theams, student.getId()));
        model.addAttribute("groupId", groupId);
        return "TrainerControlPage/deletemark";
    }
}
