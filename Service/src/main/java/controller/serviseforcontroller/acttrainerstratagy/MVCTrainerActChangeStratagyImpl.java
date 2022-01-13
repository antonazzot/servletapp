package controller.serviseforcontroller.acttrainerstratagy;

import controller.serviseforcontroller.viewsservises.ParserStringToInt;
import org.springframework.ui.Model;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import threadmodel.Mark;
import threadmodel.Theams;
import users.Student;
import users.UserImpl;

import java.util.List;
import java.util.Map;

public class MVCTrainerActChangeStratagyImpl implements MVCTrainerActStratagy{
    @Override
    public String doAct(Group group, String studentId, String thId, String mark, Model model) {
        if (studentId==null || studentId.equals("") || thId==null || thId.equals("")) {
            return "exception";
        }
        int stId = ParserStringToInt.simpleParserStringToInt(studentId);
        Student student = RepositoryFactory.getRepository().getStudentById(stId);
        Theams theams = ThreadRepositoryFactory.getRepository().theamById( Integer.parseInt(thId));
        Map<Integer, Mark> markIDListbyTheam = ThreadRepositoryFactory.getRepository().getMarkIDListbyTheam(theams, stId);
        model.addAttribute("student", student);
        model.addAttribute("th", theams);
        model.addAttribute("markIDListbyTheam", markIDListbyTheam);

        return "TrainerControlPage/listofmarkforchange";
    }
}
