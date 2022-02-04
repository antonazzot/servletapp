package controller.serviseforcontroller.acttrainerstratagy;

import controller.serviseforcontroller.viewsservises.ParserStringToInt;
import org.springframework.ui.Model;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Theams;
import users.Student;

public class MVCTrainerActDeleteStratagyImpl implements MVCTrainerActStratagy {
    @Override
    public String doAct(String studentId, String thId, String mark, Model model, Integer groupId) {
        if (studentId == null || studentId.equals("") || thId == null || thId.equals("")) {
            String massage = "field are not valid";
            model.addAttribute("massage", massage);
            return "exception";
        }
        Theams theams = ThreadRepositoryFactory.getRepository().theamById(ParserStringToInt.simpleParserStringToInt(thId));
        Student student = RepositoryFactory.getRepository().getStudentById(ParserStringToInt.simpleParserStringToInt(studentId));
        model.addAttribute("th", theams);
        model.addAttribute("student", student);
        model.addAttribute("mapOfMark", ThreadRepositoryFactory.getRepository().getMarkIDListbyTheam(theams, student.getId()));
        model.addAttribute("groupId", groupId);
        return "TrainerControlPage/deletemark";
    }
}
