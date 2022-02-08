package controller.serviseforcontroller.acttrainerstratagy;

import controller.serviseforcontroller.viewsservises.ParserStringToInt;
import helperutils.myexceptionutils.AppValidException;
import org.springframework.ui.Model;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Mark;
import threadmodel.Theams;
import users.Student;

import java.util.Map;

public class MVCTrainerActChangeStratagyImpl implements MVCTrainerActStratagy {
    @Override
    public String doAct(String studentId, String thId, String mark, Model model, Integer groupId) throws AppValidException {
        if (studentId == null || studentId.equals("") || thId == null || thId.equals(""))
            throw new AppValidException("field are not valid");

        int stId = ParserStringToInt.simpleParserStringToInt(studentId);
        Student student = RepositoryFactory.getRepository().getStudentById(stId);
        Theams theams = ThreadRepositoryFactory.getRepository().theamById(Integer.parseInt(thId));
        Map<Integer, Mark> markIDListbyTheam = ThreadRepositoryFactory.getRepository().getMarkIDListbyTheam(theams, stId);
        model.addAttribute("student", student);
        model.addAttribute("th", theams);
        model.addAttribute("markIDListbyTheam", markIDListbyTheam);
        model.addAttribute("groupT", groupId);

        return "TrainerControlPage/listofmarkforchange";
    }
}
