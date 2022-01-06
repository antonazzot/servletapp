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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MVCTrainerActWatchStratagyImpl implements MVCTrainerActStratagy{
    @Override
    public String doAct(Group group, String studentId, String thId, String mark, Model model) {

        Student student =  RepositoryFactory.getRepository().getStudentById(ParserStringToInt.simpleParserStringToInt(studentId));
        Theams theams = ThreadRepositoryFactory.getRepository().theamById(ParserStringToInt.simpleParserStringToInt(thId));
        model.addAttribute("student", student);
         model.addAttribute("mapWithTheamAndMark", getTheamsListHashMap(student, theams));
        return "TrainerControlPage/watchmark";
    }
    private Map <Theams, List<Mark>> getTheamsListHashMap(Student student, Theams tempth) {
        Map<Theams, List<Mark>> result = new HashMap<>();
        result.put(tempth, ThreadRepositoryFactory.getRepository().getMarkListbyTheam(tempth, student.getId()));
        return result;
    }

}
