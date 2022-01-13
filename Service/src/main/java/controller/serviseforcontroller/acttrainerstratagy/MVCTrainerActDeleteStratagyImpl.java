package controller.serviseforcontroller.acttrainerstratagy;

import controller.serviseforcontroller.viewsservises.ParserStringToInt;
import org.springframework.ui.Model;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import threadmodel.Theams;
import users.Student;

public class MVCTrainerActDeleteStratagyImpl implements MVCTrainerActStratagy {
    @Override
    public String doAct(Group group, String studentId, String thId, String mark, Model model) {
        if (studentId == null || studentId.equals("") || thId == null || thId.equals("")) {
            return "exception";
        }
        Theams theams = ThreadRepositoryFactory.getRepository().theamById(ParserStringToInt.simpleParserStringToInt(thId));
        Student student = RepositoryFactory.getRepository().getStudentById(ParserStringToInt.simpleParserStringToInt(studentId));
        model.addAttribute("th", theams);
        model.addAttribute("student", student);
        model.addAttribute("mapOfMark", ThreadRepositoryFactory.getRepository().getMarkIDListbyTheam(theams, student.getId()));
        return "TrainerControlPage/deletemark";
    }
}
